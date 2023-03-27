package game;

import static org.junit.jupiter.api.Assertions.*;

import game.players.GreedyPlayer;import game.players.Player;
import game.players.RandomPlayer;
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
    p1 = new GreedyPlayer("Greedy", 4, "B");
    p2 = new RandomPlayer("Random", 4, "R");
    players = new Player[] {p1, p2};
  }

  @Test
  void testGameBoard() {
    Game game = new Game(players, false);
    game.start();
  }
}
