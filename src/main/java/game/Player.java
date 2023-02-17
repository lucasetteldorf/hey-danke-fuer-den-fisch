package game;

public class Player {
  private final String name;
  private Penguin[] penguins;
  private int currentPenguinIndex;
  private int currentPenguin;
  private int collectedTileCount;
  private int collectedFishCount;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Penguin[] getPenguins() {
    return penguins;
  }

  public void setPenguins(Penguin[] penguins) {
    this.penguins = penguins;
  }

  public Penguin getPenguin(int index) {
    Penguin penguin = (index >= 0 && index < this.penguins.length) ? this.penguins[index] : null;
    return penguin;
  }

  public void updateCurrentPenguinIndex() {
    this.currentPenguinIndex++;
  }

  public Penguin getCurrentPenguin() {
    return this.penguins[this.currentPenguinIndex];
  }

  public int getCollectedTileCount() {
    return collectedTileCount;
  }

  public void updateTileCount() {
    this.collectedTileCount++;
  }

  public int getCollectedFishCount() {
    return collectedFishCount;
  }

  public void updateFishCount(int fishCount) {
    this.collectedFishCount += fishCount;
  }

  public boolean hasUnplacedPenguins() {
    for (Penguin penguin : this.penguins) {
      if (!penguin.isOnGameBoard()) {
        return true;
      }
    }

    return false;
  }

  public String getScore() {
    return this + ": " + this.collectedTileCount + " tiles and " + this.collectedFishCount + " fish collected";
  }

  @Override
  public String toString() {
    String str = this.name + " (" + getPenguin(0).getColor() + ")";
    return str;
  }
}
