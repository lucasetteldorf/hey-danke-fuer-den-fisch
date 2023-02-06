package game;

public class IceFloeTile {
  private final int fishCount;
  private Penguin placedPenguin;

  public IceFloeTile(int fishCount) {
    this.fishCount = fishCount;
  }

  public int getFishCount() {
    return fishCount;
  }

  public Penguin getPlacedPenguin() {
    return placedPenguin;
  }

  public void setPlacedPenguin(Penguin placedPenguin) {
    this.placedPenguin = placedPenguin;
  }

  public boolean isUnoccupied() {
    return this.placedPenguin == null;
  }

  @Override
  public String toString() {
    return String.valueOf(this.getFishCount());
  }
}
