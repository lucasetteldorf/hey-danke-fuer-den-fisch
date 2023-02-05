package game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {
  static IceFloeTile[][] tiles;
  static GameBoard gameBoard;
  static Player p1;
  static Player p2;

  @BeforeAll
  static void beforeAll() {
    tiles =
        new IceFloeTile[][] {
          {
            new IceFloeTile(3),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1)
          },
          {
            new IceFloeTile(1),
            new IceFloeTile(2),
            new IceFloeTile(1),
            new IceFloeTile(3),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(2),
            new IceFloeTile(2)
          },
          {
            new IceFloeTile(1),
            new IceFloeTile(2),
            new IceFloeTile(2),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(2)
          },
          {
            new IceFloeTile(2),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(3),
            new IceFloeTile(2),
            new IceFloeTile(3),
            new IceFloeTile(2),
            new IceFloeTile(3)
          },
          {
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(3),
            new IceFloeTile(1),
            new IceFloeTile(2),
            new IceFloeTile(2),
            new IceFloeTile(1)
          },
          {
            new IceFloeTile(2),
            new IceFloeTile(2),
            new IceFloeTile(3),
            new IceFloeTile(3),
            new IceFloeTile(2),
            new IceFloeTile(2),
            new IceFloeTile(1),
            new IceFloeTile(2)
          },
          {
            new IceFloeTile(1),
            new IceFloeTile(2),
            new IceFloeTile(2),
            new IceFloeTile(3),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(2)
          },
          {
            new IceFloeTile(1),
            new IceFloeTile(3),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(1),
            new IceFloeTile(2),
            new IceFloeTile(1)
          }
        };

    gameBoard = new GameBoard(tiles);

    p1 = new Player(2, PenguinColor.BLUE);
    p2 = new Player(2, PenguinColor.RED);
  }

  @Test
  void testFishCounts() {
    int oneFishCount = 0;
    int twoFishCount = 0;
    int threeFishCount = 0;

    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        switch (tiles[i][j].getFishCount()) {
          case 1:
            oneFishCount++;
            break;
          case 2:
            twoFishCount++;
            break;
          case 3:
            threeFishCount++;
            break;
        }
      }
    }

    assertEquals(30, oneFishCount);
    assertEquals(20, twoFishCount);
    assertEquals(10, threeFishCount);
  }

  @Test
  void testPenguinPlacement() {
    assertEquals(false, gameBoard.placePenguin(p1.getPenguins()[0], 0, 0));
    assertEquals(-1, p1.getPenguins()[0].getRowIndex());
    assertEquals(-1, p1.getPenguins()[0].getColIndex());

    assertEquals(true, gameBoard.placePenguin(p1.getPenguins()[0], 0, 1));
    assertEquals(0, p1.getPenguins()[0].getRowIndex());
    assertEquals(1, p1.getPenguins()[0].getColIndex());
  }
}
