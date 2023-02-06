package game;

public class Player {
  private final String name;
  private final Penguin[] penguins;
  private int collectedTilesCount;
  private int collectedFishCount;

  public Player(String name, int playersCount, PenguinColor color) {
    this.name = name;
    this.collectedTilesCount = 0;
    this.collectedFishCount = 0;

    switch (playersCount) {
      case 3:
        this.penguins = new Penguin[3];
        break;
      case 4:
        this.penguins = new Penguin[2];
        break;
      default:
        this.penguins = new Penguin[4];
        break;
    }
    for (int i = 0; i < this.penguins.length; i++) {
      this.penguins[i] = new Penguin(color, this);
    }
  }

  public void updateCollectedTilesCount() {
    this.collectedTilesCount++;
  }

  public void updateCollectedFishCount(int fishCount) {
    this.collectedFishCount += fishCount;
  }

  public int getCollectedTilesCount() {
    return collectedTilesCount;
  }

  public int getCollectedFishCount() {
    return collectedFishCount;
  }

  public Penguin[] getPenguins() {
    return penguins;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return this.name
        + ": "
        + this.collectedFishCount
        + " fish and "
        + this.collectedTilesCount
        + " tiles collected";
  }
}
