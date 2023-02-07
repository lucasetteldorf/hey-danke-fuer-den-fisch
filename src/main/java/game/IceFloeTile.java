package game;

public class IceFloeTile {
  private final int fishCount;
  private boolean isOnBoard;
  private Penguin placedPenguin;

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
    String playerStr =
        (this.placedPenguin == null) ? "-" : this.placedPenguin.getPlayer().getName();
    String str = "f:" + this.fishCount + ";p:" + playerStr;

    return (this.isOnBoard) ? str : "[tile removed]";
  }
}
