package game;

public class Player {
  private int collectedTilesCount;
  private int collectedFishCount;
  private final Penguin[] penguins;

  public Player(int playersCount, PenguinColor color) {
    this.collectedTilesCount = 0;
    this.collectedFishCount = 0;

    switch (playersCount) {
      case 2:
        this.penguins = new Penguin[4];
        break;
      case 3:
        this.penguins = new Penguin[3];
        break;
      case 4:
        this.penguins = new Penguin[2];
        break;
      default:
        this.penguins = new Penguin[0];
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

  public Penguin[] getPenguins() {
    return penguins;
  }
}
