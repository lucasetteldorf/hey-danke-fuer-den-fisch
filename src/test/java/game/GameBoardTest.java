package game;

import static org.junit.jupiter.api.Assertions.*;

import game.players.HumanPlayer;
import game.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {
  static final int[] fishCounts = {
    3, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 3, 2, 3, 2, 3, 1, 1,
    3, 1, 2, 2, 1, 2, 2, 3, 3, 2, 2, 1, 2, 1, 2, 2, 3, 1, 1, 2, 1, 3, 1, 1, 1, 1, 2, 1
  };
  static GameBoard board;
  static Player p1;
  static Player p2;
  static Player[] players;

  @BeforeEach
  void reset() {
    board = new GameBoard(fishCounts);
    p1 = new HumanPlayer("P1", 4, "B");
    p2 = new HumanPlayer("P2", 4, "R");
    players = new Player[] {p1, p2};
  }

  @Test
  void testFishCounts() {
    int oneFishCount = 0;
    int twoFishCount = 0;
    int threeFishCount = 0;

    for (IceFloeTile tile : board.getAllTiles()) {
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
    // contains 2 fish
    assertFalse(board.isLegalPlacementPosition(1, 2));
    // contains 3 fish
    assertFalse(board.isLegalPlacementPosition(1, 6));
    // invalid positions
    assertFalse(board.isLegalPlacementPosition(0, 0));
    assertFalse(board.isLegalPlacementPosition(-3, 22));
    assertFalse(board.isLegalPlacementPosition(38, -16));
    // valid positions
    assertTrue(board.isLegalPlacementPosition(0, 3));
    assertTrue(board.isLegalPlacementPosition(4, 7));

    board.placePenguin(p1, 0, 3);
    // tile is occupied
    assertFalse(board.isLegalPlacementPosition(0, 3));
    board.placePenguin(p2, 0, 3);
    board.printBoard();
  }

  @Test
  void testIsPlacementPhaseOver() {
    board.placePenguin(p1, 0, 3);
    board.placePenguin(p1, 0, 5);
    board.placePenguin(p1, 0, 7);
    assertTrue(p1.canPlacePenguin());
    board.placePenguin(p1, 0, 9);
    assertFalse(p1.canPlacePenguin());
    assertFalse(board.isPlacementPhaseOver());
    board.placePenguin(p2, 0, 11);
    board.placePenguin(p2, 0, 13);
    board.placePenguin(p2, 1, 0);
    assertTrue(p2.canPlacePenguin());
    assertFalse(board.isPlacementPhaseOver());
    board.placePenguin(p2, 1, 4);
    assertFalse(p2.canPlacePenguin());
    assertTrue(board.isPlacementPhaseOver());
    board.printBoard();
  }

  @Test
  void testGetAllLegalPlacementPositions() {
    System.out.println(board.getAllTiles().toString());
    for (int[] placementPosition : board.getAllLegalPlacementPositions()) {
      System.out.println(placementPosition[0] + " " + placementPosition[1]);
    }
  }
}
