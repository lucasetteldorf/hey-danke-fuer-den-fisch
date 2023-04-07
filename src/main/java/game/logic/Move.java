package game.logic;

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
    this.oldPosition = Arrays.copyOf(move.oldPosition, move.oldPosition.length);
    this.newPosition = Arrays.copyOf(move.newPosition, move.newPosition.length);
  }

  public int[] getOldPosition() {
    return oldPosition;
  }

  public int[] getNewPosition() {
    return newPosition;
  }
}
