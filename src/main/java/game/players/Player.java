package game.players;

import game.GameBoard;
import java.util.Arrays;
import java.util.List;
import utility.ConsoleColors;
import utility.RandomNumbers;

public class Player {
  private final PlayerType type;
  private final String name;
  private final int penguinCount;
  private final String penguinColor;
  private final int[] penguinIndices;
  private int placedPenguinCount;
  private int collectedTileCount;
  private int collectedFishCount;
  private boolean penguinsRemovedFromBoard;

  public Player(PlayerType type, String name, int penguinCount, String penguinColor) {
    this.type = type;
    this.name = name;
    this.penguinCount = penguinCount;
    this.penguinColor = ConsoleColors.getColorString(penguinColor);
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
    this.collectedTileCount = player.collectedTileCount;
    this.collectedFishCount = player.collectedFishCount;
    this.penguinsRemovedFromBoard = player.penguinsRemovedFromBoard;
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

  public int getCollectedTileCount() {
    return collectedTileCount;
  }

  public void updateCollectedTileCount() {
    this.collectedTileCount++;
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
        RandomNumbers.getRandomIndex(legalPlacementPositions.size()));
  }

  public String getScore() {
    return this
        + ": "
        + this.collectedTileCount
        + " tiles and "
        + this.collectedFishCount
        + " fish collected";
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
