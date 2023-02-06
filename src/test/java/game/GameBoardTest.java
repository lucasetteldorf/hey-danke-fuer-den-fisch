package game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    p1 = new Player("p1", 2, PenguinColor.BLUE);
    p2 = new Player("p2", 2, PenguinColor.RED);
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
  void testPlayers() {
    System.out.println(p1);
    System.out.println(p2);
  }

  @Test
  void testPenguins() {
    for (Penguin p : p1.getPenguins()) {
      System.out.println(p);
    }

    System.out.println();

    for (Penguin p : p2.getPenguins()) {
      System.out.println(p);
    }
  }

  @Test
  void testPenguinPlacement() {
    assertFalse(gameBoard.placePenguin(p1.getPenguins()[0], 0, 0));
    assertEquals(-1, p1.getPenguins()[0].getRowIndex());
    assertEquals(-1, p1.getPenguins()[0].getColIndex());
    assertFalse(p1.getPenguins()[0].isPlaced());

    assertTrue(gameBoard.placePenguin(p1.getPenguins()[0], 0, 1));
    assertEquals(0, p1.getPenguins()[0].getRowIndex());
    assertEquals(1, p1.getPenguins()[0].getColIndex());

    assertFalse(gameBoard.placePenguin(p1.getPenguins()[0],0, 2 ));

    assertTrue(gameBoard.placePenguin(p1.getPenguins()[1],0, 2 ));
    assertEquals(0, p1.getPenguins()[1].getRowIndex());
    assertEquals(2, p1.getPenguins()[1].getColIndex());

    assertFalse(gameBoard.placePenguin(p1.getPenguins()[1],0, 3 ));

    assertFalse(gameBoard.placePenguin(p2.getPenguins()[0],0, 1 ));
    assertFalse(gameBoard.placePenguin(p2.getPenguins()[1],0, 2 ));

    assertTrue(gameBoard.placePenguin(p2.getPenguins()[0],0, 3 ));
    assertEquals(0, p2.getPenguins()[0].getRowIndex());
    assertEquals(3, p2.getPenguins()[0].getColIndex());

    assertTrue(gameBoard.placePenguin(p2.getPenguins()[1],0, 4 ));
    assertEquals(0, p2.getPenguins()[1].getRowIndex());
    assertEquals(4, p2.getPenguins()[1].getColIndex());
  }
}
