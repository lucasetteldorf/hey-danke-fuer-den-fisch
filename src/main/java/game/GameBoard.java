package game;

import java.util.Random;

public class GameBoard {
  private final int ICE_FLOE_TILE_COUNT = 60;
  private final int ROW_COUNT = 8;

  private final IceFloeTile[][] tiles;

  public GameBoard() {
    this.tiles = initialize();
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
    if (destRowIndex < 0 || destRowIndex > 7 || destColIndex < 0 || destColIndex > 7) {
      return false;
    }

    IceFloeTile selectedTile = this.tiles[destRowIndex][destColIndex];

    if (selectedTile != null && selectedTile.isUnoccupied() && selectedTile.getFishCount() == 1) {
      selectedTile.setUnoccupied(false);
      penguin.setRowIndex(destRowIndex);
      penguin.setColIndex(destColIndex);

      return true;
    }

    return false;
  }

  public boolean movePenguin(Penguin penguin, int destRowIndex, int destColIndex) {
    if (destColIndex < 0 || destColIndex > 7) {
      return false;
    }

    int srcRowIndex = penguin.getRowIndex();
    int srcColIndex = penguin.getColIndex();
    IceFloeTile srcTile = this.tiles[srcRowIndex][srcColIndex];
    IceFloeTile destTile = this.tiles[destRowIndex][destColIndex];

    if (isValidMove(srcRowIndex, srcColIndex, destRowIndex, destColIndex)) {
      srcTile.setUnoccupied(true);
      destTile.setUnoccupied(false);

      penguin.getPlayer().updateCollectedTilesCount();
      penguin.getPlayer().updateCollectedFishCount(srcTile.getFishCount());
      penguin.setRowIndex(destRowIndex);
      penguin.setColIndex(destColIndex);

      srcTile = null;

      return true;
    }

    return false;
  }

  private boolean isValidMove(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {

    return false;
  }

  private boolean isReachableTopRight(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    return false;
  }

  private boolean isReachableRight(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    return false;
  }

  private boolean isReachableBottomRight(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    return false;
  }

  private boolean isReachableTopLeft(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    return false;
  }

  private boolean isReachableLeft(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    return false;
  }

  private boolean isReachableBottomLeft(
      int srcRowIndex, int srcColIndex, int destRowIndex, int destColIndex) {
    return false;
  }

  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();

    for (int i = 0; i < this.tiles.length; i++) {
      for (int j = 0; j < this.tiles[i].length; j++) {
        if (i % 2 == 0 && j < this.tiles[i].length - 1) {
          str.append("--").append(this.tiles[i][j]).append("-");
        } else if (i % 2 == 0 && j == this.tiles[i].length - 1) {
          str.append("--").append(this.tiles[i][j]).append("--");
        } else if (i % 2 == 1 && j < this.tiles[i].length - 1) {
          str.append(this.tiles[i][j]).append("---");
        } else {
          str.append(this.tiles[i][j]);
        }
      }

      str.append("\n");
    }

    return str.toString();
  }
}
