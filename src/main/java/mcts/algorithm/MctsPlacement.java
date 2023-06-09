package mcts.algorithm;

import game.logic.GameBoard;
import game.players.Player;
import mcts.node.NodePlacement;

public class MctsPlacement {
  private static final int WIN_SCORE = 1;
  private static final int TIE_SCORE = 0;
  private static final int LOSE_SCORE = -1;
  private final int computationalBudget;
  private final double c;
  private final HeuristicType placementType;
  private final HeuristicType movementType;
  private int totalNumberOfSimulations;
  private Player currentPlayer;
  private int callCount;

  public MctsPlacement() {
    this.computationalBudget = 100;
    this.c = 0.5;
    this.placementType = HeuristicType.NONE;
    this.movementType = HeuristicType.NONE;
  }

  public MctsPlacement(
      double c, int computationalBudget, HeuristicType placementType, HeuristicType movementType) {
    this.c = c;
    this.computationalBudget = computationalBudget;
    this.placementType = placementType;
    this.movementType = movementType;
  }

  public int[] getNextPlacementPosition(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

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
      switch (placementType) {
        case MORTFT -> tmp.placeMaxOwnReachableThreeFishTiles();
        case MERTFT -> tmp.placeMinEnemyReachableThreeFishTiles();
        case MORFC -> tmp.placeMaxOwnReachableFishCount();
        case MERFC -> tmp.placeMinEnemyReachableFishCount();
        case MORT -> tmp.placeMaxOwnReachableTiles();
        case MERT -> tmp.placeMinEnemyReachableTiles();
        default -> tmp.placeRandom();
      }
    }

    while (!tmp.getBoard().isMovementPhaseOver()) {
      switch (movementType) {
        case MNTFC -> tmp.moveMaxNewTileFishCount();
        case MORTFT -> tmp.moveMaxOwnReachableThreeFishTiles();
        case MERTFT -> tmp.moveMinEnemyReachableThreeFishTiles();
        case MORFC -> tmp.moveMaxOwnReachableFishCount();
        case MERFC -> tmp.moveMinEnemyReachableFishCount();
        case MORT -> tmp.moveMaxOwnReachableTiles();
        case MERT -> tmp.moveMinEnemyReachableTiles();
        default -> tmp.moveRandom();
      }
    }

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

  public void resetStats() {
    callCount = 0;
    totalNumberOfSimulations = 0;
  }
}
