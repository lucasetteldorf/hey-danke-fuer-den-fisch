package game;

import utility.ConsoleColors;

public class Penguin {
  // values: "B" = blue, "R" = red, "G" = green, "Y" = yellow
  private final String color;
  private final Player player;
  private int[] position;

  public Penguin(String color, Player player) {
    this.player = player;
    switch (color) {
      case "B":
        this.color = ConsoleColors.BLUE_PLAYER;
        break;
      case "R":
        this.color = ConsoleColors.RED_PLAYER;
        break;
      case "G":
        this.color = ConsoleColors.GREEN_PLAYER;
        break;
      case "Y":
        this.color = ConsoleColors.YELLOW_PLAYER;
        break;
      default:
        this.color = ConsoleColors.RESET;
        break;
    }
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

  public String getColor() {
    return color;
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
    return this.color;
  }
}
