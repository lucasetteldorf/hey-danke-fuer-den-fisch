package game;

import utility.ConsoleColors;

public class Penguin {
  // values: "B" = blue, "R" = red, "G" = green, "Y" = yellow
  private final String color;
  private final String colorStr;
  private final Player player;
  private int[] position;

  public Penguin(String color, Player player) {
    this.color = color;
    this.player = player;
    switch (color) {
      case "B":
        colorStr = ConsoleColors.BLUE_PLAYER;
        break;
      case "R":
        colorStr = ConsoleColors.RED_PLAYER;
        break;
      case "G":
        colorStr = ConsoleColors.GREEN_PLAYER;
        break;
      case "Y":
        colorStr = ConsoleColors.YELLOW_PLAYER;
        break;
      default:
        colorStr = ConsoleColors.RESET;
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
    return this.colorStr;
  }
}
