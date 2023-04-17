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

  // TODO just max. for placed penguin again instead of all?
  public void playMaxOwnThreeFishTiles() {
    int[] bestPlacementPosition = null;
    int maxThreeFishTiles = Integer.MIN_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int threeFishTiles =
          newBoard.getThreeFishTilesCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (threeFishTiles > maxThreeFishTiles) {
        maxThreeFishTiles = threeFishTiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void playMinEnemyThreeFishTiles() {
    int[] bestPlacementPosition = null;
    int minThreeFishTiles = Integer.MAX_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int threeFishTiles =
          newBoard.getThreeFishTilesCountForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (threeFishTiles < minThreeFishTiles) {
        minThreeFishTiles = threeFishTiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  // TODO just max. for placed penguin again instead of all?
  public void playMaxOwnTotalFishCount() {
    int[] bestPlacementPosition = null;
    int maxFishCount = Integer.MIN_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void playMinEnemyTotalFishCount() {
    int[] bestPlacementPosition = null;
    int minFishCount = Integer.MAX_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (fishCount < minFishCount) {
        minFishCount = fishCount;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  // TODO just max. for placed penguin again instead of all?
  public void playMaxOwnTotalTiles() {
    int[] bestPlacementPosition = null;
    int maxTiles = Integer.MIN_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int tiles =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (tiles > maxTiles) {
        maxTiles = tiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void playMinEnemyTotalTiles() {
    int[] bestPlacementPosition = null;
    int minTiles = Integer.MAX_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int tiles =
          newBoard.getReachableTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (tiles < minTiles) {
        minTiles = tiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }
}
