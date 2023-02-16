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

  public static void main(String[] args) {
    GameBoard board = new GameBoard();
    System.out.println(board);
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

  public IceFloeTile getTile(int rowIndex, int colIndex) {
    return this.tiles.get(Arrays.hashCode(new int[] {rowIndex, colIndex}));
  }

  @Override
  public String toString() {
    String str = "  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n";

    for (int i = 0; i < ROW_COUNT; i++) {
      str += i + " | ";
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        IceFloeTile tile = tiles.get(Arrays.hashCode(new int[] {i, j}));

        String spacingLeft;
        String spacingRight;
        if (i % 2 == 0) {
          spacingLeft = (j < 10) ? "  " : "   ";
          spacingRight = (j < 9) ? " " : "  ";
        } else {
          spacingLeft = (j < 10) ? "" : " ";
          spacingRight = (j < 9) ? "   " : "    ";
        }
        str += spacingLeft + tile + spacingRight;
      }
      str += "\n";
    }

    return str;
  }
}
