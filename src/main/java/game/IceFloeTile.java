package game;

import java.util.Arrays;

public class IceFloeTile {
  // order: top right, right, bottom right, bottom left, left, top left
  private static final int[][] TILE_NEIGHBOR_DISTANCES =
      new int[][] {{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};

  private final int fishCount;
  private final int[] position;
  private final int[][] neighborPositions;
  private boolean isUnoccupied;

  public IceFloeTile(int fishCount, int row, int col) {
    this.fishCount = fishCount;
    this.position = new int[] {row, col};
    this.neighborPositions = initNeighborPositions(row, col);
    this.isUnoccupied = true;
  }

  // copy constructor
  public IceFloeTile(IceFloeTile tile) {
    this.fishCount = tile.fishCount;
    this.position = tile.position;
    this.neighborPositions = tile.neighborPositions;
    this.isUnoccupied = tile.isUnoccupied;
  }

  private int[][] initNeighborPositions(int row, int col) {
    int[][] neighborPositions = new int[6][2];
    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
      int neighborRow = row + TILE_NEIGHBOR_DISTANCES[i][0];
      int neighborCol = col + TILE_NEIGHBOR_DISTANCES[i][1];

      if (neighborRow >= 0 && neighborRow <= 7 && neighborCol >= 0 && neighborCol <= 14) {
        neighborPositions[i][0] = neighborRow;
        neighborPositions[i][1] = neighborCol;
      } else {
        neighborPositions[i] = null;
      }
    }
    return neighborPositions;
  }

  public int getFishCount() {
    return fishCount;
  }

  public int[] getPosition() {
    return position;
  }

  public int getRow() {
    return position[0];
  }

  public int getCol() {
    return position[1];
  }

  public int[][] getNeighborPositions() {
    return neighborPositions;
  }

  public boolean isUnoccupied() {
    return isUnoccupied;
  }

  public void setUnoccupied(boolean unoccupied) {
    isUnoccupied = unoccupied;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IceFloeTile tile) {
      return position[0] == tile.position[0] && position[1] == tile.position[1];
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(position);
  }

  @Override
  public String toString() {
    return String.valueOf(fishCount);
  }
}
