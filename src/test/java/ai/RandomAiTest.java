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

    p1 = new Player("P1");
    Penguin[] p1Penguins = new Penguin[4];
    aiP = new RandomAiPlayer();
    Penguin[] p2Penguins = new Penguin[4];
    for (int i = 0; i < 4; i++) {
      p1Penguins[i] = new Penguin("B", p1);
      p2Penguins[i] = new Penguin("R", aiP);
    }
    p1.setPenguins(p1Penguins);
    aiP.setPenguins(p2Penguins);
  }

  @Test
  void testRandomPenguinPlacement() {
    System.out.println(board);

    aiP.placePenguinRandomly(board);
    System.out.println(board);

    aiP.placePenguinRandomly(board);
    System.out.println(board);

    aiP.placePenguinRandomly(board);
    System.out.println(board);

    aiP.placePenguinRandomly(board);
    System.out.println(board);
  }

  @Test
  void testRandomPenguinMovement() {
    System.out.println(board);

    aiP.placePenguinRandomly(board);
    aiP.placePenguinRandomly(board);
    aiP.placePenguinRandomly(board);
    aiP.placePenguinRandomly(board);
    System.out.println(board);

    aiP.moveRandomPenguinRandomly(board);
    System.out.println(board);

    aiP.moveRandomPenguinRandomly(board);
    System.out.println(board);

    aiP.moveRandomPenguinRandomly(board);
    System.out.println(board);

    aiP.moveRandomPenguinRandomly(board);
    System.out.println(board);
  }
}
