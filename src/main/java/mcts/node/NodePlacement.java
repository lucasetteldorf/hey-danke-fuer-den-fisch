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

  // TODO DOES NOT PROPERLY WORK AS PLAYER 2!
  public void playMaxFishPerTilePlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    double maxFishCountPerTile = Double.MIN_VALUE;
    int[] bestPosition =
            possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      double fishCountPerTile = 0;
      // calculate the value for ALL penguins (tiles might be counted multiple times)
      for (int[] penguinPosition : newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer())) {
        fishCountPerTile += newBoard.getReachableFishCountPerTileForPenguin(penguinPosition);
      }
      if (fishCountPerTile > maxFishCountPerTile) {
        maxFishCountPerTile = fishCountPerTile;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public void playMaxFishPerNeighborTilePlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    double maxFishCountPerNeighborTile = Double.MIN_VALUE;
    int[] bestPosition =
            possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      double fishCountPerNeighborTile = 0;
      for (int[] penguinPosition :
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer())) {
        fishCountPerNeighborTile += newBoard.getReachableFishCountPerNeighborTile(penguinPosition);
      }
      if (fishCountPerNeighborTile > maxFishCountPerNeighborTile) {
        maxFishCountPerNeighborTile = fishCountPerNeighborTile;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  public void playMaxTotalFishPlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxTotalFish = Integer.MIN_VALUE;
    int[] bestPosition =
            possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      int fishCount = newBoard.getReachableFishCountForPlayer(board.getCurrentPlayer());
      if (fishCount > maxTotalFish) {
        maxTotalFish = fishCount;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  public void playMayTotalNeighborFishPlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxTotalNeighborFish = Integer.MIN_VALUE;
    int[] bestPosition =
            possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      int neighborFishCount =
              newBoard.getReachableNeighborFishCountForPlayer(board.getCurrentPlayer());
      if (neighborFishCount > maxTotalNeighborFish) {
        maxTotalNeighborFish = neighborFishCount;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public void playMaxOwnPossibilitiesPlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxOwnPossibilities = Integer.MIN_VALUE;
    int[] bestPosition =
            possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      int ownPossibilities = 0;
      for (int[] penguinPosition :
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer())) {
        ownPossibilities += newBoard.getAllLegalMovesForPenguin(penguinPosition).size();
      }
      if (ownPossibilities > maxOwnPossibilities) {
        maxOwnPossibilities = ownPossibilities;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public void playMinOpponentPossibilitiesPlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int minOpponentPossibilities = Integer.MAX_VALUE;
    int[] bestPosition =
            possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      int opponentPossibilities = 0;
      for (int[] opponentPenguinPosition :
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer())) {
        opponentPossibilities += board.getAllLegalMovesForPenguin(opponentPenguinPosition).size();
      }
      if (opponentPossibilities < minOpponentPossibilities) {
        minOpponentPossibilities = opponentPossibilities;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }
}
