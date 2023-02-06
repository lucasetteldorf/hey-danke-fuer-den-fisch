package game;

public class IceFloeTile {
  private final int fishCount;
  private Penguin placedPenguin;
  private boolean isOnBoard;

  public IceFloeTile(int fishCount) {
    this.fishCount = fishCount;
    this.isOnBoard = true;
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

  public boolean isOnBoard() {
    return isOnBoard;
  }

  public void setOnBoard(boolean onBoard) {
    isOnBoard = onBoard;
  }

  public boolean isUnoccupied() {
    return this.placedPenguin == null;
  }

  @Override
  public String toString() {
    return String.valueOf(this.getFishCount());
  }
}
