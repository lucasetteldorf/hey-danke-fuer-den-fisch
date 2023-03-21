package ai;

import game.Game;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import org.junit.jupiter.api.Test;

public class MctsAiTest {
  @Test
  void testMctsAi() {
    MctsPlayer p1 = new MctsPlayer("MCTS AI", 4, "B");
    RandomPlayer p2 = new RandomPlayer("Random AI", 4, "R");
    Player[] players = new Player[]{p1, p2};
    Game game = new Game(players);
    game.start();
  }
}
