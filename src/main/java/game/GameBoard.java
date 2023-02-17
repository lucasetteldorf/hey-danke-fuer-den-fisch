package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GameBoard {
  private static final int TILE_COUNT = 60;
  private static final int ROW_COUNT = 8;
  private static final int COL_COUNT = 15;

  private HashMap<Integer, IceFloeTile> tiles;

  public GameBoard() {
    createGameBoard();
  }

  private void createGameBoard() {
    ArrayList<Integer> fishCounts = new ArrayList<>();
    int fishCount = 1;
    for (int i = 0; i < TILE_COUNT; i++) {
      fishCounts.add(fishCount);

      if (i == 29 || i == 49) {
        fishCount++;
      }
    }

    this.tiles = new HashMap<>();
    for (int i = 0; i < ROW_COUNT; i++) {
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        int randomIndex = getRandomIndex(fishCounts.size());
        IceFloeTile randomTile = new IceFloeTile(fishCounts.get(randomIndex), i, j);
        tiles.put(randomTile.hashCode(), randomTile);
        fishCounts.remove(randomIndex);
      }
    }
  }

  private int getRandomIndex(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  private int hashCoordinates(int rowIndex, int colIndex) {
    return Arrays.hashCode(new int[] {rowIndex, colIndex});
  }

  private IceFloeTile getTile(int rowIndex, int colIndex) {
    return this.tiles.get(hashCoordinates(rowIndex, colIndex));
  }

  public boolean placePenguin(Penguin penguin, int rowIndex, int colIndex) {
    IceFloeTile selectedTile = getTile(rowIndex, colIndex);

    if (selectedTile != null && selectedTile.isUnoccupied() && selectedTile.getFishCount() == 1) {
      selectedTile.setPlacedPenguin(penguin);
      penguin.place(rowIndex, colIndex);
      return true;
    }

    return false;
  }

  public boolean movePenguin(Penguin penguin, int rowIndex, int colIndex) {
    IceFloeTile oldTile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);
    IceFloeTile newTile = getTile(rowIndex, colIndex);

    if (isValidMove(oldTile, newTile)) {
      newTile.setPlacedPenguin(penguin);
      penguin.setPosition(rowIndex, colIndex);
      penguin.getPlayer().updateTileCount();
      penguin.getPlayer().updateFishCount(oldTile.getFishCount());
      tiles.remove(hashCoordinates(oldTile.getCoordinates()[0], oldTile.getCoordinates()[1]));

      return true;
    }

    return false;
  }

  private boolean isValidMove(IceFloeTile oldTile, IceFloeTile newTile) {
    if (newTile != null && !oldTile.equals(newTile) && newTile.isUnoccupied()) {
      int rowDiff = newTile.getCoordinates()[0] - oldTile.getCoordinates()[0];
      int colDiff = newTile.getCoordinates()[1] - oldTile.getCoordinates()[1];

      // TODO test if this implementation actually works

      // top right (direction 0)
      if (rowDiff <= -1 && colDiff >= 1) {
        return areAllTilesValid(oldTile, newTile, 0);
      }

      // right (direction 1)
      if (rowDiff == 0 && colDiff >= 2) {
        return areAllTilesValid(oldTile, newTile, 1);
      }

      // bottom right (direction 2)
      if (rowDiff >= 1 && colDiff >= 1) {
        return areAllTilesValid(oldTile, newTile, 2);
      }

      // bottom left (direction 3)
      if (rowDiff >= 1 && colDiff <= -1) {
        return areAllTilesValid(oldTile, newTile, 3);
      }

      // left (direction 4)
      if (rowDiff == 0 && colDiff <= -2) {
        return areAllTilesValid(oldTile, newTile, 4);
      }

      // top left (direction 5)
      if (rowDiff <= -1 && colDiff <= -1) {
        return areAllTilesValid(oldTile, newTile, 5);
      }
    }

    return false;
  }

  private boolean areAllTilesValid(IceFloeTile oldTile, IceFloeTile newTile, int direction) {
    IceFloeTile neighbor =
        getTile(
            oldTile.getNeighborCoordinates()[direction][0],
            oldTile.getNeighborCoordinates()[direction][1]);

    // TODO may not work as intended (row movement)
    int colDiff = Math.abs(newTile.getCoordinates()[1] - oldTile.getCoordinates()[1]);
    if (direction == 1 || direction == 4) {
      colDiff /= 2;
    }

    for (int i = 0; i < colDiff; i++) {
      if (neighbor == null || !neighbor.isUnoccupied()) {
        return false;
      }
      neighbor =
          getTile(
              neighbor.getNeighborCoordinates()[direction][0],
              neighbor.getNeighborCoordinates()[direction][1]);
    }

    return true;
  }

  // TODO has penguin any more legal moves (or directly in penguin class?)

  @Override
  public String toString() {
    String str =
        "\n  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n";

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
