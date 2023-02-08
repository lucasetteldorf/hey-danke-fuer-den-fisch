package game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {
  static IceFloeTile[][] tiles;
  static GameBoard gameBoard;
  static Player p1;
  static Player p2;

  static void init() {
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

    p1 = new Player("P1", 2, PenguinColor.BLUE);
    p2 = new Player("P2", 2, PenguinColor.RED);
  }

  @BeforeAll
  static void beforeAll() {
    init();
  }

  @AfterEach
  void afterEach() {
    init();
  }

  @Test
  @Order(1)
  void testPenguinPlacement() {
    assertFalse(gameBoard.placePenguin(p1.getPenguins()[0], 0, 0));
    assertEquals(-1, p1.getPenguins()[0].getRowIndex());
    assertEquals(-1, p1.getPenguins()[0].getColIndex());
    assertFalse(p1.getPenguins()[0].isPlaced());

    assertTrue(gameBoard.placePenguin(p1.getPenguins()[0], 0, 1));
    assertEquals(0, p1.getPenguins()[0].getRowIndex());
    assertEquals(1, p1.getPenguins()[0].getColIndex());

    assertFalse(gameBoard.placePenguin(p1.getPenguins()[0], 0, 2));

    assertTrue(gameBoard.placePenguin(p1.getPenguins()[1], 0, 2));
    assertEquals(0, p1.getPenguins()[1].getRowIndex());
    assertEquals(2, p1.getPenguins()[1].getColIndex());

    assertFalse(gameBoard.placePenguin(p1.getPenguins()[1], 0, 3));

    assertFalse(gameBoard.placePenguin(p2.getPenguins()[0], 0, 1));
    assertFalse(gameBoard.placePenguin(p2.getPenguins()[1], 0, 2));

    assertTrue(gameBoard.placePenguin(p2.getPenguins()[0], 0, 3));
    assertEquals(0, p2.getPenguins()[0].getRowIndex());
    assertEquals(3, p2.getPenguins()[0].getColIndex());

    assertTrue(gameBoard.placePenguin(p2.getPenguins()[1], 0, 4));
    assertEquals(0, p2.getPenguins()[1].getRowIndex());
    assertEquals(4, p2.getPenguins()[1].getColIndex());
  }

  @Test
  @Order(2)
  void testReachableTopRight() {
    gameBoard.placePenguin(p1.getPenguins()[0], 2, 5);
    assertTrue(
        gameBoard.isReachableTopRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 0, 6));

    gameBoard.placePenguin(p1.getPenguins()[1], 1, 2);
    gameBoard.placePenguin(p1.getPenguins()[2], 0, 2);
    assertFalse(
        gameBoard.isReachableTopRight(
            p1.getPenguins()[1].getRowIndex(), p1.getPenguins()[1].getColIndex(), 0, 2));
  }

  @Test
  @Order(3)
  void testReachableRight() {
    gameBoard.placePenguin(p1.getPenguins()[0], 7, 2);
    assertTrue(
        gameBoard.isReachableRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 7));

    gameBoard.placePenguin(p1.getPenguins()[1], 7, 5);
    assertFalse(
        gameBoard.isReachableRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 5));
    assertFalse(
        gameBoard.isReachableRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 7));
    assertTrue(
        gameBoard.isReachableRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 4));
  }

  @Test
  @Order(4)
  void testReachableBottomRight() {
    gameBoard.placePenguin(p1.getPenguins()[0], 2, 5);
    assertTrue(
        gameBoard.isReachableBottomRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 4, 6));

    gameBoard.placePenguin(p1.getPenguins()[1], 4, 6);
    assertFalse(
        gameBoard.isReachableBottomRight(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 4, 6));
  }

  @Test
  @Order(5)
  void testReachableTopLeft() {
    gameBoard.placePenguin(p1.getPenguins()[0], 2, 5);
    assertTrue(
        gameBoard.isReachableTopLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 0, 4));

    gameBoard.placePenguin(p1.getPenguins()[1], 0, 4);
    assertFalse(
        gameBoard.isReachableTopLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 0, 4));
  }

  @Test
  @Order(6)
  void testReachableLeft() {
    gameBoard.placePenguin(p1.getPenguins()[0], 7, 7);
    assertTrue(
        gameBoard.isReachableLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 0));

    gameBoard.placePenguin(p1.getPenguins()[1], 7, 2);
    assertFalse(
        gameBoard.isReachableLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 0));
    assertFalse(
        gameBoard.isReachableLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 2));
    assertTrue(
        gameBoard.isReachableLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 3));
  }

  @Test
  @Order(7)
  void testReachableBottomLeft() {
    gameBoard.placePenguin(p1.getPenguins()[0], 2, 5);
    assertTrue(
        gameBoard.isReachableBottomLeft(
            p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 3));

    gameBoard.placePenguin(p1.getPenguins()[1], 7, 3);
    assertFalse(
            gameBoard.isReachableBottomLeft(
                    p1.getPenguins()[0].getRowIndex(), p1.getPenguins()[0].getColIndex(), 7, 3));
  }

  @Test
  @Order(8)
  void testPenguinMovement() {
    gameBoard.placePenguin(p1.getPenguins()[0], 2, 3);
    System.out.println(gameBoard);

    assertTrue(gameBoard.movePenguin(p1.getPenguins()[0], 0, 4));
    System.out.println(gameBoard);

    gameBoard.placePenguin(p2.getPenguins()[0], 0, 2);
    System.out.println(gameBoard);

    assertFalse(gameBoard.movePenguin(p1.getPenguins()[0], 0, 0));
  }
}
