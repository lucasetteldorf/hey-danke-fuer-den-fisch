package game;

import java.util.Random;

public class GameBoard {
  private final int ICE_FLOE_TILE_COUNT = 60;
  private final int ROW_COUNT = 8;
  private final int[][] EVEN_ROW_NEIGHBOR_INDICES =
      new int[][] {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {0, -1}};
  private final int[][] ODD_ROW_NEIGHBOR_INDICES =
      new int[][] {{-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}, {0, -1}};

  private final IceFloeTile[][] tiles;

  public GameBoard() {
    this.tiles = initialize();
  }

  public GameBoard(IceFloeTile[][] tiles) {
    this.tiles = tiles;
  }

  private IceFloeTile[][] initialize() {
    IceFloeTile[] basicTiles = new IceFloeTile[ICE_FLOE_TILE_COUNT];
    int fishCount = 1;

    for (int i = 0; i < basicTiles.length; i++) {
      if (i == 30 || i == 50) {
        fishCount++;
      }

      basicTiles[i] = new IceFloeTile(fishCount);
    }

    IceFloeTile[][] randomTiles = new IceFloeTile[ROW_COUNT][];
    for (int i = 0; i < randomTiles.length; i++) {
      int tilesCount = (i % 2 == 0) ? 7 : 8;
      randomTiles[i] = new IceFloeTile[tilesCount];
    }

    for (int i = 0; i < randomTiles.length; i++) {
      for (int j = 0; j < randomTiles[i].length; j++) {
        int randomIndex = this.getRandomNumber(basicTiles.length);
        randomTiles[i][j] = basicTiles[randomIndex];
        IceFloeTile[] tmp = new IceFloeTile[basicTiles.length - 1];
        for (int k = 0, l = 0; k < basicTiles.length; k++) {
          if (k != randomIndex) {
            tmp[l++] = basicTiles[k];
          }
        }

        basicTiles = tmp;
      }
    }

    return randomTiles;
  }

  private int getRandomNumber(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  public boolean placePenguin(Penguin penguin, int destRowIndex, int destColIndex) {
    if (penguin.isPlaced()
        || destRowIndex < 0
        || destRowIndex > 7
        || destColIndex < 0
        || destColIndex > 7
        || destRowIndex % 2 == 0 && destColIndex > 6) {
      return false;
    }

    IceFloeTile selectedTile = this.tiles[destRowIndex][destColIndex];
    if (selectedTile != null && selectedTile.isUnoccupied() && selectedTile.getFishCount() == 1) {
      selectedTile.setPlacedPenguin(penguin);
      penguin.setRowIndex(destRowIndex);
      penguin.setColIndex(destColIndex);

      return true;
    }

    return false;
  }

  public boolean movePenguin(Penguin penguin, int destRowIndex, int destColIndex) {
    if (destRowIndex < 0
        || destRowIndex > 7
        || destColIndex < 0
        || destColIndex > 7
        || destRowIndex % 2 == 0 && destColIndex > 6) {
      return false;
    }

    int srcRowIndex = penguin.getRowIndex();
    int srcColIndex = penguin.getColIndex();
    IceFloeTile srcTile = this.tiles[srcRowIndex][srcColIndex];
    IceFloeTile destTile = this.tiles[destRowIndex][destColIndex];

    if (isValidMove(srcRowIndex, srcColIndex, destRowIndex, destColIndex)) {
      srcTile.setPlacedPenguin(null);
      srcTile.setOnBoard(false);
      destTile.setPlacedPenguin(penguin);

      penguin.getPlayer().updateCollectedTilesCount();
      penguin.getPlayer().updateCollectedFishCount(srcTile.getFishCount());
      penguin.setRowIndex(destRowIndex);
      penguin.setColIndex(destColIndex);

      return true;
    }

    return false;
  }

  private boolean isValidMove(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcRowIndex == destRowIndex && srcColIndex == destColIndex) {
      return false;
    }

    if (destColIndex < srcColIndex && srcRowIndex == destRowIndex) {
      return isReachableLeft(srcRowIndex, srcColIndex, destRowIndex, destColIndex);
    } else if (destColIndex > srcColIndex && srcRowIndex == destRowIndex) {
      return isReachableRight(srcRowIndex, srcColIndex, destRowIndex, destColIndex);
    } else if (destRowIndex < srcRowIndex && destColIndex >= srcColIndex) {
      return isReachableTopRight(srcRowIndex, srcColIndex, destRowIndex, destColIndex);
    } else if (destRowIndex < srcRowIndex && destColIndex <= srcColIndex) {
      return isReachableTopLeft(srcRowIndex, srcColIndex, destRowIndex, destColIndex);
    } else if (destRowIndex > srcRowIndex && destColIndex >= srcColIndex) {
      return isReachableBottomRight(srcRowIndex, srcColIndex, destRowIndex, destColIndex);
    } else if (destRowIndex > srcRowIndex && destColIndex <= srcColIndex) {
      return isReachableBottomLeft(srcRowIndex, srcColIndex, destRowIndex, destColIndex);
    }

    return false;
  }

  public boolean isReachableTopRight(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcRowIndex == 0 || srcColIndex == 7) {
      return false;
    }

    int colIndex = srcColIndex + ((srcRowIndex + 1) % 2);
    for (int i = srcRowIndex - 1; i >= destRowIndex; i--) {
      if (!this.tiles[i][colIndex].isOnBoard() || !this.tiles[i][colIndex].isUnoccupied()) {
        return false;
      }

      colIndex += ((i + 1) % 2);
    }

    return true;
  }

  public boolean isReachableRight(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcColIndex == 7
        || srcRowIndex % 2 == 0 && srcColIndex == 6
        || srcRowIndex != destRowIndex) {
      return false;
    }

    for (int i = srcColIndex + 1; i <= destColIndex; i++) {
      if (!this.tiles[srcRowIndex][i].isOnBoard() || !this.tiles[srcRowIndex][i].isUnoccupied()) {
        return false;
      }
    }

    return true;
  }

  public boolean isReachableBottomRight(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcRowIndex == 7 || srcColIndex == 7) {
      return false;
    }

    int colIndex = srcColIndex + ((srcRowIndex + 1) % 2);
    for (int i = srcRowIndex + 1; i <= destRowIndex; i++) {
      if (!this.tiles[i][colIndex].isOnBoard() || !this.tiles[i][colIndex].isUnoccupied()) {
        return false;
      }

      colIndex += ((i + 1) % 2);
    }

    return true;
  }

  public boolean isReachableTopLeft(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcRowIndex == 0 || srcColIndex == 0 && srcRowIndex % 2 == 1) {
      return false;
    }

    int colIndex = srcColIndex - (srcRowIndex % 2);
    for (int i = srcRowIndex - 1; i >= destRowIndex; i--) {
      if (!this.tiles[i][colIndex].isOnBoard() || !this.tiles[i][colIndex].isUnoccupied()) {
        return false;
      }

      colIndex -= i % 2;
    }

    return true;
  }

  public boolean isReachableLeft(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcColIndex == 0 || srcRowIndex != destRowIndex) {
      return false;
    }

    for (int i = srcColIndex - 1; i >= destColIndex; i--) {
      if (!this.tiles[srcRowIndex][i].isOnBoard() || !this.tiles[srcRowIndex][i].isUnoccupied()) {
        return false;
      }
    }

    return true;
  }

  public boolean isReachableBottomLeft(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    if (srcRowIndex == 7 || srcColIndex == 0 && srcRowIndex % 2 == 1) {
      return false;
    }

    int colIndex = srcColIndex - (srcRowIndex % 2);
    for (int i = srcRowIndex + 1; i <= destRowIndex; i++) {
      if (!this.tiles[i][colIndex].isOnBoard() || !this.tiles[i][colIndex].isUnoccupied()) {
        return false;
      }

      colIndex -= i % 2;
    }

    return true;
  }

  public boolean hasPossibleMoves(Penguin penguin) {
    IceFloeTile penguinTile = this.tiles[penguin.getRowIndex()][penguin.getColIndex()];
    IceFloeTile[] tileNeighbors = getNeighbors(penguin.getRowIndex(), penguin.getColIndex());

    for (IceFloeTile neighbor : tileNeighbors) {
        if (neighbor != null && neighbor.isOnBoard() && neighbor.isUnoccupied()) {
          return true;
        }
    }

    return false;
  }

  public IceFloeTile[] getNeighbors(int rowIndex, int colIndex) {
    IceFloeTile[] neighbors = new IceFloeTile[6];

    for (int i = 0; i < 6; i++) {
      IceFloeTile neighbor = null;

      if (rowIndex % 2 == 0) {
        int neighborRowIndex = rowIndex + EVEN_ROW_NEIGHBOR_INDICES[i][0];
        int neighborColIndex = colIndex + EVEN_ROW_NEIGHBOR_INDICES[i][1];

        if (neighborRowIndex >= 0
            && neighborRowIndex <= 7
            && neighborColIndex >= 0
            && neighborColIndex <= 7) {
          neighbor = this.tiles[neighborRowIndex][neighborColIndex];
        }

        neighbors[i] = neighbor;
      } else {
        int neighborRowIndex = rowIndex + ODD_ROW_NEIGHBOR_INDICES[i][0];
        int neighborColIndex = colIndex + ODD_ROW_NEIGHBOR_INDICES[i][1];

        if (neighborRowIndex >= 0
            && neighborRowIndex <= 7
            && neighborColIndex >= 0
            && neighborColIndex <= 6) {
          neighbor = this.tiles[neighborRowIndex][neighborColIndex];
        }

        neighbors[i] = neighbor;
      }
    }

    return neighbors;
  }

  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();

    int index = 1;
    for (int i = 0; i < this.tiles.length; i++) {
      for (int j = 0; j < this.tiles[i].length; j++) {
        String tileInfo =
            (this.tiles[i][j].isOnBoard())
                ? "[i:" + index + ";" + this.tiles[i][j] + "]"
                : this.tiles[i][j] + "";
        index++;

        if (i % 2 == 0) {
          str.append("    ").append(tileInfo);
        } else {
          str.append(tileInfo).append("  ");
        }
      }

      str.append("\n");
    }

    return str.toString();
  }
}
