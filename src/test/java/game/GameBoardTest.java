package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {
  static final int[] fishCounts = {
    3, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 3, 2, 3, 2, 3, 1, 1,
    3, 1, 2, 2, 1, 2, 2, 3, 3, 2, 2, 1, 2, 1, 2, 2, 3, 1, 1, 2, 1, 3, 1, 1, 1, 1, 2, 1
  };
  static GameBoard board;
  static HumanPlayer p1;
  static HumanPlayer p2;

  @BeforeEach
  void reset() {
    board = new GameBoard(fishCounts);

    p1 = new HumanPlayer("P1");
    Penguin[] p1Penguins = new Penguin[4];
    p2 = new HumanPlayer("P2");
    Penguin[] p2Penguins = new Penguin[4];
    for (int i = 0; i < 4; i++) {
      p1Penguins[i] = new Penguin("B", p1);
      p2Penguins[i] = new Penguin("R", p2);
    }
    p1.setPenguins(p1Penguins);
    p2.setPenguins(p2Penguins);
  }

  @Test
  void testFishCounts() {
    int oneFishCount = 0;
    int twoFishCount = 0;
    int threeFishCount = 0;

    for (IceFloeTile tile : board.getTiles()) {
      switch (tile.getFishCount()) {
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

    assertEquals(30, oneFishCount);
    assertEquals(20, twoFishCount);
    assertEquals(10, threeFishCount);
  }

  @Test
  void testPenguinPlacement() {
    Penguin p1 = new Penguin("B", null);
    Penguin p2 = new Penguin("R", null);
    assertNull(p1.getPosition());
    assertNull(p2.getPosition());

    // invalid position
    assertFalse(board.placePenguin(p1, 0, 0));
    // not one fish
    assertFalse(board.placePenguin(p1, 0, 1));
    assertNull(p1.getPosition());
    // valid placement
    assertTrue(board.placePenguin(p1, 0, 3));
    assertArrayEquals(new int[] {0, 3}, p1.getPosition());
    // already occupied
    assertFalse(board.placePenguin(p2, 0, 3));
    assertNull(p2.getPosition());
    // valid
    assertTrue(board.placePenguin(p2, 0, 5));
    assertArrayEquals(new int[] {0, 5}, p2.getPosition());
    // penguin already placed
    assertFalse(board.placePenguin(p2, 0, 7));

    Penguin p3 = new Penguin("G", null);
    // out of bounds
    assertFalse(board.placePenguin(p3, -1, -1));
  }

  @Test
  void testPenguinMovementTopRight() {
    board.placePenguin(p1.getPenguin(0), 6, 11);

    assertTrue(board.movePenguin(p1.getPenguin(0), 5, 12));

    assertTrue(board.movePenguin(p1.getPenguin(0), 3, 14));

    board.placePenguin(p2.getPenguin(0), 4, 13);
    assertFalse(board.movePenguin(p2.getPenguin(0), 3, 14));

    board.placePenguin(p1.getPenguin(1), 7, 0);
    assertTrue(board.movePenguin(p1.getPenguin(1), 0, 7));

    board.placePenguin(p2.getPenguin(1), 7, 10);
    assertFalse(board.movePenguin(p2.getPenguin(1), 3, 14));
    assertFalse(board.movePenguin(p2.getPenguin(1), 4, 13));
    assertFalse(board.movePenguin(p2.getPenguin(1), 5, 12));
    assertFalse(board.movePenguin(p2.getPenguin(1), 6, 11));
  }

  @Test
  void testPenguinMovementRight() {
    board.placePenguin(p1.getPenguin(0), 6, 11);

    assertTrue(board.movePenguin(p1.getPenguin(0), 6, 13));

    board.placePenguin(p2.getPenguin(0), 6, 9);
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 11));
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 13));

    board.placePenguin(p1.getPenguin(1), 0, 3);
    assertTrue(board.movePenguin(p1.getPenguin(1), 0, 13));

    assertFalse(board.movePenguin(p1.getPenguin(1), 0, 14));
    assertFalse(board.movePenguin(p1.getPenguin(1), 0, 20));
  }

  @Test
  void testPenguinMovementBottomRight() {
    board.placePenguin(p1.getPenguin(0), 6, 11);

    assertTrue(board.movePenguin(p1.getPenguin(0), 7, 12));

    board.placePenguin(p2.getPenguin(0), 2, 7);
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 11));
    assertFalse(board.movePenguin(p2.getPenguin(0), 7, 12));

    assertTrue(board.movePenguin(p2.getPenguin(0), 3, 8));
    assertTrue(board.movePenguin(p2.getPenguin(0), 5, 10));
  }

  @Test
  void testPenguinMovementBottomLeft() {
    board.placePenguin(p1.getPenguin(0), 6, 11);

    assertTrue(board.movePenguin(p1.getPenguin(0), 7, 10));

    board.placePenguin(p2.getPenguin(0), 4, 13);
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 11));
    assertFalse(board.movePenguin(p2.getPenguin(0), 7, 10));

    assertTrue(board.movePenguin(p2.getPenguin(0), 5, 12));
  }

  @Test
  void testPenguinMovementLeft() {
    board.placePenguin(p1.getPenguin(0), 6, 11);

    assertTrue(board.movePenguin(p1.getPenguin(0), 6, 7));
    assertTrue(board.movePenguin(p1.getPenguin(0), 6, 3));

    board.placePenguin(p2.getPenguin(0), 6, 9);

    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 7));
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 5));
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 3));
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 1));
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, 0));
    assertFalse(board.movePenguin(p2.getPenguin(0), 6, -4));
    assertFalse(board.movePenguin(p2.getPenguin(0), 7, 0));
  }

  @Test
  void testPenguinMovementTopLeft() {
    board.placePenguin(p1.getPenguin(0), 6, 11);

    assertTrue(board.movePenguin(p1.getPenguin(0), 5, 10));

    board.placePenguin(p2.getPenguin(0), 2, 7);

    assertFalse(board.movePenguin(p1.getPenguin(0), 2, 7));
    assertFalse(board.movePenguin(p1.getPenguin(0), 1, 6));
    assertFalse(board.movePenguin(p1.getPenguin(0), 0, 5));
    assertTrue(board.movePenguin(p1.getPenguin(0), 3, 8));

    assertTrue(board.movePenguin(p2.getPenguin(0), 1, 6));
    assertTrue(board.movePenguin(p2.getPenguin(0), 0, 5));
  }

  @Test
  void testAllMovementsAndScore() {
    assertTrue(board.placePenguin(p1.getPenguin(0), 2, 11));
    assertTrue(board.movePenguin(p1.getPenguin(0), 1, 12));
    assertTrue(board.movePenguin(p1.getPenguin(0), 2, 13));
    assertTrue(board.movePenguin(p1.getPenguin(0), 3, 12));
    assertTrue(board.movePenguin(p1.getPenguin(0), 3, 10));
    assertTrue(board.movePenguin(p1.getPenguin(0), 2, 9));
    assertTrue(board.movePenguin(p1.getPenguin(0), 0, 11));
    assertTrue(board.movePenguin(p1.getPenguin(0), 0, 13));

    assertEquals(12, p1.getCollectedFishCount());
    assertEquals(7, p1.getCollectedTileCount());
  }

  @Test
  void testInvalidMoves() {
    assertTrue(board.placePenguin(p1.getPenguin(0), 2, 11));
    assertFalse(board.movePenguin(p1.getPenguin(0), 4, 11));
    assertFalse(board.movePenguin(p1.getPenguin(0), 0, 11));

    assertFalse(board.movePenguin(p1.getPenguin(0), 2, 10));
    assertFalse(board.movePenguin(p1.getPenguin(0), 2, 12));

    assertFalse(board.movePenguin(p1.getPenguin(0), 2, 15));
    assertFalse(board.movePenguin(p1.getPenguin(0), -1, 14));

    // valid positions on board, but forbidden movement
    assertFalse(board.movePenguin(p1.getPenguin(0), 0, 11));
    assertFalse(board.movePenguin(p1.getPenguin(0), 5, 10));
    assertFalse(board.movePenguin(p1.getPenguin(0), 0, 5));
    assertFalse(board.movePenguin(p1.getPenguin(0), 1, 14));
    assertFalse(board.movePenguin(p1.getPenguin(0), 4, 3));
    assertFalse(board.movePenguin(p1.getPenguin(0), 3, 14));
  }

  @Test
  void testRemovePenguinAndTile() {
    assertTrue(board.placePenguin(p1.getPenguin(0), 0, 3));
    board.removePenguinAndTile(p1.getPenguin(0));
    assertNull(board.getTile(0, 3));
    assertEquals(1, p1.getCollectedTileCount());
    assertEquals(1, p1.getCollectedFishCount());
  }

  @Test
  void testHasLegalMoves() {
    assertTrue(board.placePenguin(p1.getPenguin(0), 0, 13));
    assertTrue(board.hasPenguinLegalMoves(p1.getPenguin(0)));
    assertTrue(board.placePenguin(p2.getPenguin(0), 0, 11));
    assertTrue(board.hasPenguinLegalMoves(p1.getPenguin(0)));
    assertTrue(board.movePenguin(p2.getPenguin(0), 1, 12));
    assertTrue(board.hasPenguinLegalMoves(p1.getPenguin(0)));
    assertTrue(board.movePenguin(p2.getPenguin(0), 1, 14));
    assertFalse(board.hasPenguinLegalMoves(p1.getPenguin(0)));
  }

  @Test
  void testGameplayLoop() {
    assertTrue(board.placePenguin(p1.getPenguin(0), 0, 3));
    assertTrue(board.placePenguin(p2.getPenguin(0), 0, 5));
    assertTrue(board.placePenguin(p1.getPenguin(1), 0, 7));
    assertTrue(board.placePenguin(p2.getPenguin(1), 0, 9));
    assertTrue(board.placePenguin(p1.getPenguin(2), 0, 11));
    assertTrue(board.placePenguin(p2.getPenguin(2), 0, 13));
    assertTrue(board.placePenguin(p1.getPenguin(3), 1, 0));
    assertTrue(board.placePenguin(p2.getPenguin(3), 1, 4));
    System.out.println(board);

    assertTrue(board.movePenguin(p1.getPenguin(3), 0, 1));

    assertTrue(board.movePenguin(p2.getPenguin(3), 2, 3));

    assertTrue(board.movePenguin(p1.getPenguin(1), 1, 8));

    assertTrue(board.movePenguin(p2.getPenguin(3), 2, 1));

    assertTrue(board.movePenguin(p1.getPenguin(2), 1, 12));

    assertTrue(board.hasPenguinLegalMoves(p1.getPenguin(0)));
    assertTrue(board.hasPenguinLegalMoves(p1.getPenguin(3)));
    assertTrue(board.hasPenguinLegalMoves(p2.getPenguin(3)));

    assertTrue(board.movePenguin(p2.getPenguin(3), 1, 2));

    assertFalse(board.hasPenguinLegalMoves(p1.getPenguin(0)));
    assertFalse(board.hasPenguinLegalMoves(p1.getPenguin(3)));
    assertFalse(board.hasPenguinLegalMoves(p2.getPenguin(3)));

    assertTrue(p1.hasPenguinsToMove(board));
    assertTrue(p2.hasPenguinsToMove(board));

    assertTrue(board.movePenguin(p1.getPenguin(1), 2, 7));

    assertTrue(board.movePenguin(p2.getPenguin(1), 1, 10));

    assertTrue(board.movePenguin(p1.getPenguin(2), 2, 13));

    assertTrue(board.movePenguin(p2.getPenguin(1), 2, 11));

    assertTrue(board.hasPenguinLegalMoves(p1.getPenguin(2)));
    assertTrue(board.movePenguin(p1.getPenguin(2), 1, 14));
    assertFalse(board.hasPenguinLegalMoves(p1.getPenguin(2)));

    assertTrue(p1.hasPenguinsToMove(board));
    assertTrue(p2.hasPenguinsToMove(board));

    assertTrue(board.movePenguin(p2.getPenguin(1), 4, 13));

    assertTrue(board.movePenguin(p1.getPenguin(1), 2, 5));

    assertTrue(board.movePenguin(p2.getPenguin(1), 3, 12));

    assertTrue(p1.hasPenguinsToMove(board));
    assertTrue(board.movePenguin(p1.getPenguin(1), 1, 6));
    assertFalse(p1.hasPenguinsToMove(board));
    System.out.println(board);

    assertTrue(board.hasPenguinLegalMoves(p2.getPenguin(1)));
    assertTrue(p2.hasPenguinsToMove(board));
    assertTrue(board.movePenguin(p2.getPenguin(1), 3, 14));
    assertFalse(board.hasPenguinLegalMoves(p2.getPenguin(1)));
    assertFalse(p2.hasPenguinsToMove(board));

    System.out.println(board);
  }

  @Test
  void testAllPossibleMovesForPenguin() {
    board.placePenguin(p1.getPenguin(0), 2, 11);
    System.out.println(board);

    for (int[] coordinates : board.getAllLegalMovesForPenguin(p1.getPenguin(0))) {
      System.out.println(Arrays.toString(coordinates));
    }
  }

  @Test
  void testAllPossibleMovesForPlayer() {
    // no penguins placed yet
    assertTrue(board.getAllLegalMovesForPlayer(p1).isEmpty());

    board.placePenguin(p1.getPenguin(0), 2, 11);
    for (int[] coordinates : board.getAllLegalMovesForPlayer(p1)) {
      System.out.print(Arrays.toString(coordinates) + " ");
    }
    System.out.println();

    board.placePenguin(p2.getPenguin(0), 1,10);
    for (int[] coordinates : board.getAllLegalMovesForPlayer(p1)) {
      System.out.print(Arrays.toString(coordinates) + " ");
    }
    System.out.println();

    board.placePenguin(p2.getPenguin(1), 4,13);
    for (int[] coordinates : board.getAllLegalMovesForPlayer(p1)) {
      System.out.print(Arrays.toString(coordinates) + " ");
    }
    System.out.println();

    board.placePenguin(p1.getPenguin(1), 0,11);
    for (int[] coordinates : board.getAllLegalMovesForPlayer(p1)) {
      System.out.print(Arrays.toString(coordinates) + " ");
    }
    System.out.println();
  }
}
