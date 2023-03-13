package ai;

import game.Game;import game.players.MctsPlayer;import game.players.Player;import game.players.RandomPlayer;import org.junit.jupiter.api.Test;import utility.RandomNumbers;

public class MctsAiTest {
  @Test
  void testMctsAi() {
    RandomPlayer p1 = new RandomPlayer("Random AI", 4, "B");
    MctsPlayer p2 = new MctsPlayer("MCTS AI", 4, "R");
    Player[] players = new Player[]{p1, p2};
    p2.setPlayers(players);
    Game game = new Game(players);
    game.start();
  }
}
