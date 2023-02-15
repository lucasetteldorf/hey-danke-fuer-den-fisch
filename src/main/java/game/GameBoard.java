package game;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
  private final int TILE_COUNT = 60;

  private IceFloeTile[][] tiles;

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

    this.tiles = new IceFloeTile[8][15];
    for (int i = 0; i < this.tiles.length; i++) {
      for (int j = (i % 2 == 0) ? 1 : 0; j < this.tiles[i].length; j += 2) {
        int randomIndex = getRandomIndex(baseTiles.size());
        this.tiles[i][j] = baseTiles.get(randomIndex);
        baseTiles.remove(randomIndex);
      }
    }
  }

  private int getRandomIndex(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  @Override
  public String toString() {
    String str = "  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n";

    for (int i = 0; i < this.tiles.length; i++) {
      str += i + " | ";
      for (int j = (i % 2 == 0) ? 1 : 0; j < this.tiles[i].length; j += 2) {
        if (i % 2 == 0) {
          String spacingLeft = (j < 10) ? "  " : "   ";
          String spacingRight = (j < 9) ? " " : "  ";
          str +=  spacingLeft + this.tiles[i][j] + spacingRight;
        } else {
          String spacingLeft = (j < 10) ? "" : " ";
          String spacingRight = (j < 9) ? "   " : "    ";
          str += spacingLeft + this.tiles[i][j] + spacingRight;
        }

      }
      str += "\n";
    }

    return str;
  }
}
