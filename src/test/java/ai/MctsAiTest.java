package ai;

import game.Game;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import org.junit.jupiter.api.Test;
import utility.DataWriter;

public class MctsAiTest {
  private static final int NUMBER_OF_GAMES = 100;

  @Test
  void testMctsAi() {
    Player[] players;
    Game game;
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      MctsPlayer p1 = new MctsPlayer("MCTS AI", 4, "B");
      RandomPlayer p2 = new RandomPlayer("Random AI", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();
      DataWriter.writeDataLine(
          "/Users/Lucas/thesis-data/random-vs-mcts-" + NUMBER_OF_GAMES + ".csv", players);
    }
  }

  @Test
  void testMctsAiMoves() {
    MctsPlayer p1 = new MctsPlayer("MCTS AI", 4, "B");
    RandomPlayer p2 = new RandomPlayer("Random AI", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players);
    game.start();
  }
}
