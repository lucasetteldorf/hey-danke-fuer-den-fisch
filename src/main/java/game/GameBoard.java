package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GameBoard {
  private final int TILE_COUNT = 60;
  private final int ROW_COUNT = 8;
  private final int COL_COUNT = 15;
  // order: top right, right, bottom right, bottom left, left, top left
  private final int[][] TILE_NEIGHBOR_DISTANCES =
      new int[][] {{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};

  private HashMap<Integer, IceFloeTile> tiles;

  public GameBoard() {
    createGameBoard();
  }

  private void createGameBoard() {
    ArrayList<IceFloeTile> baseTiles = new ArrayList<>();
    int fishCount = 1;
    for (int i = 0; i < TILE_COUNT; i++) {
      baseTiles.add(new IceFloeTile(fishCount));

      if (i == 29 || i == 49) {
        fishCount++;
      }
    }

    this.tiles = new HashMap<>();
    for (int i = 0; i < ROW_COUNT; i++) {
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        int randomIndex = getRandomIndex(baseTiles.size());
        IceFloeTile randomTile = baseTiles.get(randomIndex);
        randomTile.setCoordinates(i, j);
        int[][] tileNeighborCoordinates = calculateTileNeighborCoordinates(i, j);
        randomTile.setNeighborCoordinates(tileNeighborCoordinates);
        tiles.put(randomTile.hashCode(), randomTile);
        baseTiles.remove(randomIndex);
      }
    }
  }

  private int getRandomIndex(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  private int[][] calculateTileNeighborCoordinates(int rowIndex, int colIndex) {
    int[][] neighborCoordinates = new int[6][2];

    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
      int neighborRowIndex = rowIndex + TILE_NEIGHBOR_DISTANCES[i][0];
      int neighborColIndex = colIndex + TILE_NEIGHBOR_DISTANCES[i][1];

      // invalid/non-existing neighbors have values 0, 0 (which is not a valid tile on the board)
      if (neighborRowIndex >= 0
          && neighborRowIndex <= 7
          && neighborColIndex >= 0
          && neighborColIndex <= 14) {
        neighborCoordinates[i][0] = neighborRowIndex;
        neighborCoordinates[i][1] = neighborColIndex;
      }
    }

    return neighborCoordinates;
  }

  public boolean placePenguin(Penguin penguin, int rowIndex, int colIndex) {
    IceFloeTile selectedTile = getTile(rowIndex, colIndex);

    if (selectedTile != null && selectedTile.isUnoccupied() && selectedTile.getFishCount() == 1) {
      selectedTile.setPlacedPenguin(penguin);
      penguin.setPosition(rowIndex, colIndex);
      return true;
    }

    return false;
  }

  public boolean movePenguin(Penguin penguin, int rowIndex, int colIndex) {
    IceFloeTile oldTile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);
    IceFloeTile newTile = getTile(rowIndex, colIndex);

    if (isValidMove(oldTile, newTile)) {
      penguin.setPosition(rowIndex, colIndex);
      newTile.setPlacedPenguin(penguin);
      tiles.remove(hashCoordinates(oldTile.getCoordinates()[0], oldTile.getCoordinates()[1]));

      return true;
    }

    return false;
  }

  private boolean isValidMove(IceFloeTile oldTile, IceFloeTile newTile) {
    if (newTile != null && !oldTile.equals(newTile) && newTile.isUnoccupied()) {
      int rowDiff = newTile.getCoordinates()[0] - oldTile.getCoordinates()[0];
      int colDiff = newTile.getCoordinates()[1] - oldTile.getCoordinates()[1];

      // top right (direction 0)
      if (rowDiff <= -1 && colDiff >= 1) {
        return areAllNeighborsValid(oldTile, newTile, 0);
      }

      // right (direction 1)
      if (rowDiff == 0 && colDiff >= 2) {
        return areAllNeighborsValid(oldTile, newTile, 1);
      }

      // bottom right (direction 2)
      if (rowDiff >= 1 && colDiff >= 1) {
        return areAllNeighborsValid(oldTile, newTile, 2);
      }

      // bottom left (direction 3)
      if (rowDiff >= 1 && colDiff <= -1) {
        return areAllNeighborsValid(oldTile, newTile, 3);
      }

      // left (direction 4)
      if (rowDiff == 0 && colDiff <= -2) {
        return areAllNeighborsValid(oldTile, newTile, 4);
      }

      // top left (direction 5)
      if (rowDiff <= -1 && colDiff <= -1) {
        return areAllNeighborsValid(oldTile, newTile, 5);
      }
    }

    return false;
  }

  private boolean areAllNeighborsValid(IceFloeTile oldTile, IceFloeTile newTile, int direction) {
    IceFloeTile neighbor =
        getTile(
            oldTile.getNeighborCoordinates()[direction][0],
            oldTile.getNeighborCoordinates()[direction][1]);

    int colDiff = newTile.getCoordinates()[1] - oldTile.getCoordinates()[1];
    for (int i = 0; i < colDiff - 1; i++) {
      if (neighbor == null || !neighbor.isUnoccupied()) {
        return false;
      }
      neighbor =
          getTile(
              neighbor.getNeighborCoordinates()[direction][0],
              neighbor.getNeighborCoordinates()[direction][1]);
    }

    return false;
  }

  public IceFloeTile getTile(int rowIndex, int colIndex) {
    return this.tiles.get(Arrays.hashCode(new int[] {rowIndex, colIndex}));
  }

  private int hashCoordinates(int rowIndex, int colIndex) {
    return Arrays.hashCode(new int[] {rowIndex, colIndex});
  }

  @Override
  public String toString() {
    String str = "\n  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n";

    for (int i = 0; i < ROW_COUNT; i++) {
      str += i + " | ";
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        String spacingLeft;
        String spacingRight;
        if (i % 2 == 0) {
          spacingLeft = (j < 10) ? "  " : "   ";
          spacingRight = (j < 9) ? " " : "  ";
        } else {
          spacingLeft = (j < 10) ? "" : " ";
          spacingRight = (j < 9) ? "   " : "    ";
        }
        str += spacingLeft + getTile(i, j) + spacingRight;
      }
      str += "\n";
    }

    return str;
  }
}
