package game.players;

import game.logic.GameBoard;
import java.util.Arrays;
import java.util.List;

import utility.RandomUtility;

public class Player {
  private final PlayerType type;
  private final String name;
  private final int penguinCount;
  private final String penguinColor;
  private final int[] penguinIndices;
  private int placedPenguinCount;
  private int collectedFishCount;
  private boolean penguinsRemovedFromBoard;
  private int totalTurnCount;

  public Player(PlayerType type, String name, int penguinCount, String penguinColor) {
    this.type = type;
    this.name = name;
    this.penguinCount = penguinCount;
    this.penguinColor = penguinColor;
    this.penguinIndices = new int[penguinCount];
    Arrays.fill(penguinIndices, -1);
  }

  // copy constructor
  public Player(Player player) {
    this.type = player.type;
    this.name = player.name;
    this.penguinCount = player.penguinCount;
    this.placedPenguinCount = player.placedPenguinCount;
    this.penguinColor = player.penguinColor;
    this.penguinIndices = Arrays.copyOf(player.penguinIndices, penguinCount);
    this.collectedFishCount = player.collectedFishCount;
    this.penguinsRemovedFromBoard = player.penguinsRemovedFromBoard;
  }

  public void reset() {
    Arrays.fill(penguinIndices, -1);
    this.placedPenguinCount = 0;
    this.collectedFishCount = 0;
    this.penguinsRemovedFromBoard = false;
    this.totalTurnCount = 0;
  }

  public PlayerType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public void updatePlacedPenguinCount() {
    placedPenguinCount++;
  }

  public boolean canPlacePenguin() {
    return placedPenguinCount < penguinCount;
  }

  public String getPenguinColor() {
    return penguinColor;
  }

  public int[] getPenguinIndices() {
    return penguinIndices;
  }

  public void addPenguinIndex(int index) {
    penguinIndices[placedPenguinCount] = index;
  }

  public boolean ownsPenguinAtIndex(int index) {
    for (int penguinIndex : penguinIndices) {
      if (penguinIndex == index) {
        return true;
      }
    }
    return false;
  }

  public int getCollectedFishCount() {
    return collectedFishCount;
  }

  public void updateCollectedFishCount(int fishCount) {
    this.collectedFishCount += fishCount;
  }

  public boolean arePenguinsRemovedFromBoard() {
    return penguinsRemovedFromBoard;
  }

  public void setPenguinsRemovedFromBoard(boolean penguinsRemovedFromBoard) {
    this.penguinsRemovedFromBoard = penguinsRemovedFromBoard;
  }

  public int[] getRandomPlacementPosition(GameBoard board) {
    List<int[]> legalPlacementPositions = board.getAllLegalPlacementPositions();
    return legalPlacementPositions.get(
        RandomUtility.getRandomIndex(legalPlacementPositions.size()));
  }

  public void updateTotalTurnCount() {
    totalTurnCount++;
  }

  public int getTotalTurnCount() {
    return totalTurnCount;
  }

  public String getScore() {
    return this + ": " + this.collectedFishCount + " fish collected";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Player player) {
      return this.penguinColor.equals(player.penguinColor);
    }
    return false;
  }

  @Override
  public String toString() {
    return name + " (" + penguinColor + ")";
  }
}
