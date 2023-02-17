package game;

public class Penguin {
  // values: "B" = blue, "R" = red, "G" = green, "Y" = yellow
  private final String color;
  private final Player player;
  private int[] position;

  public Penguin(String color, Player player) {
    this.color = color;
    this.player = player;
  }

  public String getColor() {
    return color;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPosition(int rowIndex, int colIndex) {
    this.position[0] = rowIndex;
    this.position[1] = colIndex;
  }

  public int[] getPosition() {
    return position;
  }

  public void place(int rowIndex, int colIndex) {
    this.position = new int[2];
    setPosition(rowIndex, colIndex);
  }

  public void removeFromGameBoard() {
    this.position = null;
  }

  public boolean isOnGameBoard() {
    return this.position != null;
  }

  @Override
  public String toString() {
    return color;
  }
}
