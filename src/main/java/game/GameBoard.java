package game;

import java.util.*;

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

  public boolean hasPenguinLegalMoves(Penguin penguin) {
    return getAllLegalMovesForPenguin(penguin).size() > 0;
  }

  public List<int[]> getAllLegalMovesForPlayer(Player player) {
    List<int[]> possibleMoveCoordinates = new ArrayList<>();

    for (Penguin penguin : player.getPenguins()) {
      if (penguin.isOnGameBoard()) {
        possibleMoveCoordinates.addAll(getAllLegalMovesForPenguin(penguin));
      }
    }

    return possibleMoveCoordinates;
  }

  public List<int[]> getAllLegalMovesForPenguin(Penguin penguin) {
    List<int[]> possibleMoveCoordinates = new ArrayList<>();

    IceFloeTile srcTile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);

    int[][] neighborCoordinates = srcTile.getNeighborCoordinates();
    // iterate through all neighbors
    for (int i = 0; i < neighborCoordinates.length; i++) {
      if (neighborCoordinates[i] == null) {
        continue;
      }
      IceFloeTile neighbor = getTile(neighborCoordinates[i][0], neighborCoordinates[i][1]);
      // continue to move in this direction until no more move can be made
      while (neighbor != null && neighbor.isUnoccupied()) {
        possibleMoveCoordinates.add(
            new int[] {neighbor.getCoordinates()[0], neighbor.getCoordinates()[1]});

        if (neighbor.getNeighborCoordinates()[i] == null) {
          break;
        }
        neighbor =
            getTile(
                neighbor.getNeighborCoordinates()[i][0], neighbor.getNeighborCoordinates()[i][1]);
      }
    }

    return possibleMoveCoordinates;
  }

  public List<int[]> getLegalPlacementCoordinates() {
    List<int[]> placementCoordinates = new ArrayList<>();

    for (IceFloeTile tile : getTiles()) {
        if (tile != null & tile.isUnoccupied() && tile.getFishCount() == 1) {
          placementCoordinates.add(tile.getCoordinates());
        }
    }

    return placementCoordinates;
  }

  @Override
  public String toString() {
    StringBuilder str =
        new StringBuilder(
            "\n  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n");

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
