package mcts.algorithm;

import game.logic.GameBoard;
import game.players.Player;
import mcts.heavyplayout.PlacementHeuristicType;
import mcts.node.Node;
import mcts.node.NodePlacement;

public class MctsPlacement {
  private static final int WIN_SCORE = 1;
  private static final int TIE_SCORE = 0;
  private static final int LOSE_SCORE = -1;
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
    this.c = Math.sqrt(2);
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
    // init starting tree
    root.expandChildrenPlacement();

    long start = System.currentTimeMillis();
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

    while (!tmp.getBoard().isPlacementPhaseOver()) {
      switch (type) {
        case MAX_FISH_FOR_PENGUIN -> tmp.playMaxFishForPenguin();
        case MAX_TILES_FOR_PENGUIN -> tmp.playMaxTilesForPenguin();
        case MAX_FISH_PER_TILE_FOR_PENGUIN -> tmp.playMaxFishPerTileForPenguin();
        case MAX_FISH_FOR_ALL_PENGUINS -> tmp.playMaxFishForAllPenguins();
        case MAX_TILES_FOR_ALL_PENGUINS -> tmp.playMaxTilesForAllPenguins();
        case MAX_FISH_PER_TILE_FOR_ALL_PENGUINS -> tmp.playMaxFishPerTileForAllPenguins();
        case MIN_FISH_FOR_OPPONENT_PENGUINS -> tmp.playMinFishForOpponentPenguins();
        case MIN_TILES_FOR_OPPONENT_PENGUINS -> tmp.playMinTilesForOpponentPenguins();
        case MIN_FISH_PER_TILE_FOR_OPPONENT_PENGUINS -> tmp
            .playMinFishPerTileForOpponentPenguins();
        default -> tmp.playRandomPlacement();
      }
    }

    while (!tmp.getBoard().isMovementPhaseOver()) {
      tmp.playRandomMove();
    }

    numberOfSimulations++;
    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodePlacement expandedNode, int winnerIndex) {
    NodePlacement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (winnerIndex == -1) {
        tmp.updateScore(TIE_SCORE);
      } else if (currentPlayer.equals(tmp.getBoard().getPlayers()[winnerIndex])) {
        tmp.updateScore(WIN_SCORE);
      } else if (!currentPlayer.equals(tmp.getBoard().getPlayers()[winnerIndex])) {
        tmp.updateScore(LOSE_SCORE);
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

  public void resetStats() {
    callCount = 0;
    totalNumberOfSimulations = 0;
    numberOfSimulations = 0;
  }

  public PlacementHeuristicType getType() {
    return type;
  }
}
