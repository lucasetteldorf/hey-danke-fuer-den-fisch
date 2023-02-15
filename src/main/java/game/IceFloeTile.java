package game;

public class IceFloeTile {
  private final int fishCount;
  private Penguin placedPenguin;
  private IceFloeTile[] neighbors;

  public IceFloeTile(int fishCount) {
    this.fishCount = fishCount;
  }

  public int getFishCount() {
    return fishCount;
  }

  public boolean isUnoccupied() {
    return placedPenguin == null;
  }

  @Override
  public String toString() {
    return String.valueOf(this.fishCount);
  }
}
