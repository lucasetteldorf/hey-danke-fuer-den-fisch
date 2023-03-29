package ai;

import game.Game;
import game.GameBoard;import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import org.junit.jupiter.api.Test;

public class MctsAiTest {
  @Test
  void testMctsVsRandom() {
    MctsPlayer p1 = new MctsPlayer("MCTS AI", 4, "B");
    RandomPlayer p2 = new RandomPlayer("Random AI", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players, false);
    game.start();
  }

  @Test
  void testRandomVsMcts() {
    RandomPlayer p1 = new RandomPlayer("Random AI", 4, "B");
    MctsPlayer p2 = new MctsPlayer("MCTS AI", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players, false);
    game.start();
  }

  @Test
  void testMctsVsGreedy() {
    MctsPlayer p1 = new MctsPlayer("MCTS AI", 4, "B");
    GreedyPlayer p2 = new GreedyPlayer("Greedy AI", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players, false);
    game.start();
  }

  @Test
  void testGreedyVsMcts() {
    GreedyPlayer p1 = new GreedyPlayer("Greedy AI", 4, "B");
    MctsPlayer p2 = new MctsPlayer("MCTS AI", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players, false);
    game.start();
  }

  @Test
  void testMctsVsMcts() {
    MctsPlayer p1 = new MctsPlayer("MCTS AI 1", 4, "B");
    MctsPlayer p2 = new MctsPlayer("MCTS AI 2", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players, false);
    game.start();
  }
}
