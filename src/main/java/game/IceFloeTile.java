package game;

import java.util.Arrays;

public class IceFloeTile {
  private final int fishCount;
  private final int[] coordinates = new int[2];
  private Penguin placedPenguin;
  // order: top right, right, bottom right, bottom left, left, top left
  private int[][] neighborCoordinates;

  public IceFloeTile(int fishCount) {
    this.fishCount = fishCount;
  }

  public int getFishCount() {
    return fishCount;
  }

  public void setCoordinates(int rowIndex, int colIndex) {
    this.coordinates[0] = rowIndex;
    this.coordinates[1] = colIndex;
  }

  public int[][] getNeighborCoordinates() {
    return neighborCoordinates;
  }

  public void setNeighborCoordinates(int[][] neighborCoordinates) {
    this.neighborCoordinates = neighborCoordinates;
  }

  public boolean isUnoccupied() {
    return placedPenguin == null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof IceFloeTile) {
      IceFloeTile tile = (IceFloeTile) obj;
      return this.coordinates[0] == tile.coordinates[0]
          && this.coordinates[1] == tile.coordinates[1];
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.coordinates);
  }

  @Override
  public String toString() {
    String str =
        isUnoccupied() ? String.valueOf(this.fishCount) : String.valueOf(this.placedPenguin);
    return str;
  }
}
