package game;

public class Penguin {
  private final PenguinColor color;
  private final Player player;
  private int rowIndex;
  private int colIndex;

  public Penguin(PenguinColor color, Player player) {
    this.color = color;
    this.player = player;
    this.rowIndex = -1;
    this.colIndex = -1;
  }

  public PenguinColor getColor() {
    return color;
  }

  public Player getPlayer() {
    return player;
  }

  public int getRowIndex() {
    return rowIndex;
  }

  public void setRowIndex(int rowIndex) {
    this.rowIndex = rowIndex;
  }

  public int getColIndex() {
    return colIndex;
  }

  public void setColIndex(int colIndex) {
    this.colIndex = colIndex;
  }

  public boolean isPlaced() {
    return this.rowIndex != -1 && this.colIndex != -1;
  }

  @Override
  public String toString() {
    return "Penguin of player "
        + this.player.getName()
        + " (color: "
        + this.color
        + "): isPlaced: "
        + this.isPlaced()
        + ", rowIndex: "
        + this.rowIndex
        + ", colIndex: "
        + this.colIndex;
  }
}