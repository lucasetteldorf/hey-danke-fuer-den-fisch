package mcts.node;

import game.logic.GameBoard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utility.RandomUtility;

public class NodePlacement extends Node {
  private final List<int[]> untriedPlacementPositions;
  private int[] previousPlacementPosition;

  public NodePlacement() {
    super();
    this.untriedPlacementPositions = new ArrayList<>();
  }

  public NodePlacement(GameBoard board) {
    super(board);
    this.untriedPlacementPositions = new ArrayList<>();
  }

  // copy constructor
  public NodePlacement(NodePlacement node) {
    super(node);
    this.untriedPlacementPositions = new ArrayList<>();
    for (int[] placement : node.getUntriedPlacementPositions()) {
      this.untriedPlacementPositions.add(new int[] {placement[0], placement[1]});
    }

    if (node.previousPlacementPosition != null) {
      this.previousPlacementPosition =
          Arrays.copyOf(node.previousPlacementPosition, node.previousPlacementPosition.length);
    }
  }

  public int[] getPreviousPlacementPosition() {
    return previousPlacementPosition;
  }

  public void setPreviousPlacementPosition(int[] previousPlacementPosition) {
    this.previousPlacementPosition = previousPlacementPosition;
  }

  public List<int[]> getUntriedPlacementPositions() {
    return untriedPlacementPositions;
  }

  public void initUntriedPlacements() {
    untriedPlacementPositions.addAll(board.getAllLegalPlacementPositions());
  }

  public boolean hasUntriedPlacements() {
    return untriedPlacementPositions.size() > 0;
  }

  public int[] getRandomUntriedPlacement() {
    return untriedPlacementPositions.get(
        RandomUtility.getRandomIndex(untriedPlacementPositions.size()));
  }

  public void expandChildrenPlacement() {
    for (int[] placementPosition : untriedPlacementPositions) {
      NodePlacement node = new NodePlacement(this.board);
      node.getBoard().placePenguin(placementPosition[0], placementPosition[1]);
      node.setParent(this);
      node.initUntriedPlacements();
      node.setPreviousPlacementPosition(placementPosition);
      addChild(node);
    }
    untriedPlacementPositions.clear();
  }

  public void playMaxFishForPenguin() {
    int maxReachableFish = Integer.MIN_VALUE;
    int[] bestPosition = null;

    for (int[] placementPosition : board.getAllLegalPlacementPositions()) {
      int reachableFish = board.getReachableFishForPenguin(placementPosition);
      if (reachableFish > maxReachableFish) {
        maxReachableFish = reachableFish;
        bestPosition = placementPosition;
      }
    }

    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  public void playMaxTilesForPenguin() {
    int maxReachableTiles = Integer.MIN_VALUE;
    int[] bestPosition = null;

    for (int[] placementPosition : board.getAllLegalPlacementPositions()) {
      int reachableTiles = board.getReachableTilesForPenguin(placementPosition);
      if (reachableTiles > maxReachableTiles) {
        maxReachableTiles = reachableTiles;
        bestPosition = placementPosition;
      }
    }

    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  public void playMaxFishPerTileForPenguin() {
    double maxReachableFishPerTile = Integer.MIN_VALUE;
    int[] bestPosition = null;

    for (int[] placementPosition : board.getAllLegalPlacementPositions()) {
      double reachableFishPerTile = board.getReachableFishPerTileForPenguin(placementPosition);
      if (reachableFishPerTile > maxReachableFishPerTile) {
        maxReachableFishPerTile = reachableFishPerTile;
        bestPosition = placementPosition;
      }
    }

    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  public void playMaxFishForAllPenguins() {
    int maxReachableFish = Integer.MIN_VALUE;
    int[] bestPosition = null;

    for (int[] placementPosition : board.getAllLegalPlacementPositions()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int reachableFish = board.getReachableFishForPenguin(placementPosition);
      if (reachableFish > maxReachableFish) {
        maxReachableFish = reachableFish;
        bestPosition = placementPosition;
      }
    }

    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  public void playMaxTilesForAllPenguins() {}

  public void playMaxFishPerTileForAllPenguins() {}

  public void playMinFishForOpponentPenguins() {}

  public void playMinTilesForOpponentPenguins() {}

  public void playFMinFishPerTileForOpponentPenguins() {}
}
