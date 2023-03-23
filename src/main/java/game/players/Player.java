package game.players;

import game.GameBoard;
import game.Penguin;
import utility.ConsoleColors;
import utility.RandomNumbers;

public class Player {
  private final PlayerType type;
  private final String name;
  private final String penguinColor;
  private final Penguin[] penguins;
  private int penguinToPlaceIndex;
  private int collectedTileCount;
  private int collectedFishCount;
  private boolean penguinsRemovedFromBoard;

  public Player(PlayerType type, String name, int penguinCount, String penguinColor) {
    this.type = type;
    this.name = name;
    this.penguinColor = ConsoleColors.getColorString(penguinColor);
    this.penguins = initPenguins(penguinCount, penguinColor);
  }

  // copy constructor
  public Player(Player player) {
    this.type = player.type;
    this.name = player.name;
    this.penguinColor = player.penguinColor;
    this.penguins = new Penguin[player.penguins.length];
    for (int i = 0; i < player.penguins.length; i++) {
      this.penguins[i] = new Penguin(player.penguins[i]);
    }
    this.penguinToPlaceIndex = player.penguinToPlaceIndex;
    this.collectedTileCount = player.collectedTileCount;
    this.collectedFishCount = player.collectedFishCount;
    this.penguinsRemovedFromBoard = player.penguinsRemovedFromBoard;
  }

  private Penguin[] initPenguins(int penguinCount, String color) {
    Penguin[] penguins = new Penguin[penguinCount];
    for (int i = 0; i < penguins.length; i++) {
      penguins[i] = new Penguin(color, i);
    }
    return penguins;
  }

  public PlayerType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getPenguinColor() {
    return penguinColor;
  }

  public Penguin[] getPenguins() {
    return penguins;
  }

  public Penguin getPenguin(int index) {
    return penguins[index];
  }

  public Penguin getPenguinToPlace() {
    return this.penguins[this.penguinToPlaceIndex];
  }

  public void updatePenguinToPlace() {
    this.penguinToPlaceIndex++;
  }

  public boolean canPlacePenguin() {
    return this.penguinToPlaceIndex < this.penguins.length;
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
    return board
        .getAllLegalPlacementPositions()
        .get(RandomNumbers.getRandomIndex(board.getAllLegalPlacementPositions().size()));
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
      return penguinColor.equals(player.penguinColor);
    }
    return false;
  }

  @Override
  public String toString() {
    return name + " (" + penguinColor + ")";
  }
}
