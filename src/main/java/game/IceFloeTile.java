package game;

import java.util.Arrays;

public class IceFloeTile {
  private static final int[][] TILE_NEIGHBOR_DISTANCES =
      new int[][] {{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};

  private final int fishCount;
  private final int[] coordinates;
  // order: top right, right, bottom right, bottom left, left, top left
  private final int[][] neighborCoordinates;
  private Penguin placedPenguin;

  public IceFloeTile(int fishCount, int rowIndex, int colIndex) {
    this.fishCount = fishCount;
    this.coordinates = new int[] {rowIndex, colIndex};
    this.neighborCoordinates = initializeNeighborCoordinates(rowIndex, colIndex);
  }

  private int[][] initializeNeighborCoordinates(int rowIndex, int colIndex) {
    int[][] neighborCoordinates = new int[6][2];

    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
      int neighborRowIndex = rowIndex + TILE_NEIGHBOR_DISTANCES[i][0];
      int neighborColIndex = colIndex + TILE_NEIGHBOR_DISTANCES[i][1];

      // invalid/non-existing neighbors are null
      if (neighborRowIndex >= 0
          && neighborRowIndex <= 7
          && neighborColIndex >= 0
          && neighborColIndex <= 14) {
        neighborCoordinates[i][0] = neighborRowIndex;
        neighborCoordinates[i][1] = neighborColIndex;
      } else {
        neighborCoordinates[i] = null;
      }
    }

    return neighborCoordinates;
  }

  public int getFishCount() {
    return fishCount;
  }

  public int[] getCoordinates() {
    return coordinates;
  }

  public int[][] getNeighborCoordinates() {
    return neighborCoordinates;
  }


  public void setPlacedPenguin(Penguin placedPenguin) {
    this.placedPenguin = placedPenguin;
  }

  public boolean isUnoccupied() {
    return placedPenguin == null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IceFloeTile tile) {
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
    return isUnoccupied() ? String.valueOf(this.fishCount) : String.valueOf(this.placedPenguin);
  }
}
