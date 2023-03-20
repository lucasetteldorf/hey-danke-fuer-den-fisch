package ai;

import game.Game;
import game.players.Player;
import game.players.RandomPlayer;
import org.junit.jupiter.api.Test;

public class RandomAiTest {
  @Test
  void testRandomAi() {
    RandomPlayer p1 = new RandomPlayer("Random AI 1", 4, "B");
    RandomPlayer p2 = new RandomPlayer("Random AI 2", 4, "R");
    Player[] players = new Player[] {p1, p2};
    Game game = new Game(players);
    game.start();
  }
}
