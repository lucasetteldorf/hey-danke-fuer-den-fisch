package game;

import java.util.Arrays;

public class Move {
  private final int[] oldPosition;
  private final int[] newPosition;

  public Move(int[] oldPosition, int[] newPosition) {
    this.oldPosition = oldPosition;
    this.newPosition = newPosition;
  }

  // copy constructor
  public Move(Move move) {
    this.oldPosition = Arrays.copyOf(move.oldPosition, 2);
    this.newPosition = Arrays.copyOf(move.newPosition, 2);
  }

  public int getOldRow() {
    return oldPosition[0];
  }

  public int getOldCol() {
    return oldPosition[1];
  }

  public int[] getNewPosition() {
    return newPosition;
  }

  public int getNewRow() {
    return newPosition[0];
  }

  public int getNewCol() {
    return newPosition[1];
  }
}
