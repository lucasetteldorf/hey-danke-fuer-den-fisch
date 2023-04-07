package mcts.algorithm;

import game.logic.GameBoard;
import game.players.Player;
import mcts.heavyplayout.PlacementHeuristicType;
import mcts.heavyplayout.PlacementHeuristics;
import mcts.node.Node;
import mcts.node.NodePlacement;

public class MctsPlacement {
  private static final int WIN_SCORE = 1;
  private final int computationalBudget;
  private final double c;
  private final PlacementHeuristicType type;
  protected int totalNumberOfSimulations;
  protected int numberOfSimulations;
  private Player currentPlayer;
  private int callCount;

  public MctsPlacement() {
    this.computationalBudget = 100;
    this.c = 0.35;
    this.type = PlacementHeuristicType.NONE;
  }

  public MctsPlacement(double c, int computationalBudget) {
    this.c = c;
    this.computationalBudget = computationalBudget;
    this.type = PlacementHeuristicType.NONE;
  }

  public MctsPlacement(double c, int computationalBudget, PlacementHeuristicType type) {
    this.c = c;
    this.computationalBudget = computationalBudget;
    this.type = type;
  }

  public int[] getNextPlacementPosition(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

    numberOfSimulations = 0;

    NodePlacement root = new NodePlacement();
    root.setBoard(board);
    root.initUntriedPlacements();
    long start = System.currentTimeMillis();
    initTree(root);

    while ((System.currentTimeMillis() - start) < computationalBudget) {
      // 1: Selection
      NodePlacement selectedNode = selectNode(root);

      // 2: Expansion
      NodePlacement expandedNode = selectedNode;
      if (!selectedNode.getBoard().isPlacementPhaseOver()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulatePlayout(expandedNode);

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    callCount++;
    //    System.out.println(
    //        callCount
    //            + ": "
    //            + numberOfSimulations
    //            + " placement simulations ("
    //            + currentPlayer.getName()
    //            + ")");
    NodePlacement bestNode = (NodePlacement) root.getChildWithMaxVisits();
    return bestNode.getPreviousPlacementPosition();
  }

  private void initTree(NodePlacement root) {
    root.expandChildrenPlacement();
    for (Node child : root.getChildren()) {
      NodePlacement childPlacement = (NodePlacement) child;
      int playoutResult = simulatePlayout(childPlacement);
      backpropagate(childPlacement, playoutResult);
    }
  }

  private NodePlacement selectNode(NodePlacement root) {
    NodePlacement node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedPlacements()) {
        break;
      } else {
        node = (NodePlacement) Uct.findBestNode(node, c);
      }
    }
    return node;
  }

  private NodePlacement expandNode(NodePlacement node) {
    int[] randomUntriedPlacementPosition = node.getRandomUntriedPlacement();
    node.getUntriedPlacementPositions().remove(randomUntriedPlacementPosition);
    NodePlacement expandedNode = new NodePlacement(node);
    expandedNode.setPreviousPlacementPosition(randomUntriedPlacementPosition);
    expandedNode
        .getBoard()
        .placePenguin(randomUntriedPlacementPosition[0], randomUntriedPlacementPosition[1]);
    expandedNode.setParent(node);
    expandedNode.initUntriedPlacements();
    node.addChild(expandedNode);
    return expandedNode;
  }

  // light playout
  protected int simulatePlayout(NodePlacement node) {
    NodePlacement tmp = new NodePlacement(node);

    GameBoard board = tmp.getBoard();
    while (!board.isPlacementPhaseOver()) {
      switch (type) {
        case NONE -> tmp.playRandomPlacement();
        case MAX_FISH_PER_TILE -> PlacementHeuristics.playMaxFishPerTile(board);
        case MAX_FISH_PER_NEIGHBOR_TILE -> PlacementHeuristics.playMaxFishPerNeighborTile(board);
        case MAX_TOTAL_FISH -> PlacementHeuristics.playMaxTotalFish(board);
        case MAX_TOTAL_NEIGHBOR_FISH -> PlacementHeuristics.playMayTotalNeighborFish(board);
        case MAX_OWN_POSSIBILITIES -> PlacementHeuristics.playMaxOwnPossibilities(board);
        case MIN_OPPONENT_POSSIBILITIES -> PlacementHeuristics.playMinOpponentPossibilities(board);
      }
    }

    while (!tmp.getBoard().isMovementPhaseOver()) {
      tmp.playRandomMove();
    }

    numberOfSimulations++;
    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodePlacement expandedNode, int playerIndex) {
    NodePlacement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (playerIndex != -1 && currentPlayer.equals(tmp.getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      tmp = (NodePlacement) tmp.getParent();
    }
  }

  public int getTotalNumberOfSimulations() {
    return totalNumberOfSimulations;
  }

  public double getAverageSimulations() {
    return (double) totalNumberOfSimulations / callCount;
  }

  public int getCallCount() {
    return callCount;
  }

  public int getComputationalBudget() {
    return computationalBudget;
  }

  public double getC() {
    return c;
  }

  public PlacementHeuristicType getType() {
    return type;
  }
}
