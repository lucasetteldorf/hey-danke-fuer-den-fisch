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

  public GameBoard(int[] fishCounts) {
    this.tiles = new HashMap<>();
    int index = 0;
    for (int i = 0; i < ROW_COUNT; i++) {
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        IceFloeTile tile = new IceFloeTile(fishCounts[index++], i, j);
        tiles.put(tile.hashCode(), tile);
      }
    }
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

  public IceFloeTile getTile(int rowIndex, int colIndex) {
    return this.tiles.get(hashCoordinates(rowIndex, colIndex));
  }

  private IceFloeTile[] getTileNeighbors(int rowIndex, int colIndex) {
    IceFloeTile tile = getTile(rowIndex, colIndex);
    if (tile == null) {
      return null;
    }

    IceFloeTile[] neighbors = new IceFloeTile[6];
    for (int i = 0; i < neighbors.length; i++) {
      if (tile.getNeighborCoordinates()[i] == null) {
        neighbors[i] = null;
      } else {
        neighbors[i] =
            getTile(tile.getNeighborCoordinates()[i][0], tile.getNeighborCoordinates()[i][1]);
      }
    }

    return neighbors;
  }

  public IceFloeTile[] getTiles() {
    IceFloeTile[] allTiles = new IceFloeTile[TILE_COUNT];

    for (int i = 0, k = 0; i < ROW_COUNT; i++) {
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        allTiles[k++] = getTile(i, j);
      }
    }

    return allTiles;
  }

  public boolean placePenguin(Penguin penguin, int rowIndex, int colIndex) {
    IceFloeTile selectedTile = getTile(rowIndex, colIndex);

    if (!penguin.isOnGameBoard()
        && selectedTile != null
        && selectedTile.isUnoccupied()
        && selectedTile.getFishCount() == 1) {
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
      int totalDiff = Math.abs(rowDiff) - Math.abs(colDiff);

      // top right (direction 0)
      if (rowDiff <= -1 && colDiff >= 1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllTilesValid(oldTile, newTile, 0);
      }

      // right (direction 1)
      if (rowDiff == 0 && colDiff >= 2) {
        return areAllTilesValid(oldTile, newTile, 1);
      }

      // bottom right (direction 2)
      if (rowDiff >= 1 && colDiff >= 1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllTilesValid(oldTile, newTile, 2);
      }

      // bottom left (direction 3)
      if (rowDiff >= 1 && colDiff <= -1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllTilesValid(oldTile, newTile, 3);
      }

      // left (direction 4)
      if (rowDiff == 0 && colDiff <= -2) {
        return areAllTilesValid(oldTile, newTile, 4);
      }

      // top left (direction 5)
      if (rowDiff <= -1 && colDiff <= -1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllTilesValid(oldTile, newTile, 5);
      }
    }

    return false;
  }

  private boolean areAllTilesValid(IceFloeTile oldTile, IceFloeTile newTile, int direction) {
    IceFloeTile neighbor = oldTile;

    int colDiff = Math.abs(newTile.getCoordinates()[1] - oldTile.getCoordinates()[1]);
    if (direction == 1 || direction == 4) {
      colDiff /= 2;
    }

    for (int i = 0; i < colDiff; i++) {
      neighbor =
          getTile(
              neighbor.getNeighborCoordinates()[direction][0],
              neighbor.getNeighborCoordinates()[direction][1]);

      if (neighbor == null || !neighbor.isUnoccupied()) {
        return false;
      }

      // TODO maybe check back if this actually works as intended
      if (i == colDiff - 1 && !neighbor.equals(newTile)) {
        return false;
      }
    }

    return true;
  }

  public void removePenguinAndTile(Penguin penguin) {
    if (penguin.getPosition() == null) {
      return;
    }

    IceFloeTile tile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);
    penguin.getPlayer().updateTileCount();
    penguin.getPlayer().updateFishCount(tile.getFishCount());
    penguin.removeFromGameBoard();
    this.tiles.remove(hashCoordinates(tile.getCoordinates()[0], tile.getCoordinates()[1]));
  }

  public boolean hasLegalMoves(Penguin penguin) {
    if (penguin.getPosition() == null) {
      return false;
    }

    IceFloeTile[] neighbors = getTileNeighbors(penguin.getPosition()[0], penguin.getPosition()[1]);
    if (neighbors == null) {
      return false;
    }

    for (IceFloeTile neighbor : neighbors) {
      if (neighbor != null && neighbor.isUnoccupied()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String toString() {
    StringBuilder str =
            new StringBuilder("\n  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n");

    for (int i = 0; i < ROW_COUNT; i++) {
      str.append(i).append(" | ");
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
        String tileStr = (getTile(i, j) == null) ? "X" : getTile(i, j).toString();
        str.append(spacingLeft).append(tileStr).append(spacingRight);
      }
      str.append("\n");
    }

    return str.toString();
  }
}
