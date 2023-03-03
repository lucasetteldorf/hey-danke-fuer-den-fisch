package ai;

import game.GameBoard;
import game.Penguin;
import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RandomAiTest {
  static final int[] fishCounts = {
    3, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 3, 2, 3, 2, 3, 1, 1,
    3, 1, 2, 2, 1, 2, 2, 3, 3, 2, 2, 1, 2, 1, 2, 2, 3, 1, 1, 2, 1, 3, 1, 1, 1, 1, 2, 1
  };
  static GameBoard board;
  static Player p1;
  static RandomAiPlayer aiP;

  @BeforeEach
  void reset() {
    board = new GameBoard(fishCounts);

    p1 = new Player("P1", 4, "B");
    aiP = new RandomAiPlayer("Random AI", 4, "R");
  }

  @Test
  void testRandomPenguinPlacement() {
    System.out.println(board);

    aiP.placePenguin(board);
    System.out.println(board);

    aiP.placePenguin(board);
    System.out.println(board);

    aiP.placePenguin(board);
    System.out.println(board);

    aiP.placePenguin(board);
    System.out.println(board);
  }

  @Test
  void testRandomPenguinMovement() {
    System.out.println(board);

    aiP.placePenguin(board);
    aiP.placePenguin(board);
    aiP.placePenguin(board);
    aiP.placePenguin(board);
    System.out.println(board);

    aiP.movePenguin(board);
    System.out.println(board);

    aiP.movePenguin(board);
    System.out.println(board);

    aiP.movePenguin(board);
    System.out.println(board);

    aiP.movePenguin(board);
    System.out.println(board);
  }
}
