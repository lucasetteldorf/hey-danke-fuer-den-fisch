package game;

public class Penguin {
  // values: "B" = blue, "R" = red, "G" = green, "Y" = yellow
  private final String color;
  private final int[] position;

  public Penguin(String color) {
    this.color = color;
    this.position = new int[] {-1, -1};
  }

  public String getColor() {
    return color;
  }

  public void removeFromBoard() {
    setPosition(-1, -1);
  }

  public void setPosition(int rowIndex, int colIndex) {
    this.position[0] = rowIndex;
    this.position[1] = colIndex;
  }

  public int[] getPosition() {
    return position;
  }

  // TODO may not work as intended
  public boolean isOnGameBoard() {
    return this.position[0] != -1 && this.position[1] != -1;
  }

  @Override
  public String toString() {
    return color;
  }
}
