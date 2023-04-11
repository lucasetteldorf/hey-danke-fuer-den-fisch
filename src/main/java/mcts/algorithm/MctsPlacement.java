package mcts.algorithm;

import game.logic.GameBoard;
import game.players.Player;
import mcts.heavyplayout.PlacementHeuristicType;
import mcts.node.Node;
import mcts.node.NodePlacement;

public class MctsPlacement {
  private static final int WIN_SCORE = 1;
  private final int computationalBudget;
  private final double c;
  private final PlacementHeuristicType type;
  private int totalNumberOfSimulations;
  private int numberOfSimulations;
  private boolean printSimulations;
  private Player currentPlayer;
  private int callCount;

  public MctsPlacement() {
    this.computationalBudget = 100;
    this.c = 1 / Math.sqrt(2);
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

    if (printSimulations) {
      numberOfSimulations = 0;
    }

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
    if (printSimulations) {
      System.out.println(
          callCount
              + ": "
              + numberOfSimulations
              + " placement simulations ("
              + currentPlayer.getName()
              + ")");
    }
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
        node = (NodePlacement) Ucb1.findBestNode(node, c);
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

    while (!tmp.getBoard().isPlacementPhaseOver()) {
      switch (type) {

        default -> tmp.playRandomPlacement();
      }
    }

    while (!tmp.getBoard().isMovementPhaseOver()) {
      tmp.playRandomMove();
    }

    if (printSimulations) {
      numberOfSimulations++;
    }
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

  public void enableSimulationPrint() {
    printSimulations = true;
  }

  public PlacementHeuristicType getType() {
    return type;
  }
}
