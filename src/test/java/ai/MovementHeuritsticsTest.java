package ai;

import game.Game;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import mcts.algorithm.MctsMovement;
import mcts.algorithm.MctsPlacement;
import mcts.heavyplayout.MovementHeuristicType;
import org.junit.jupiter.api.Test;

public class MovementHeuritsticsTest {
  @Test
  void testMaxNewFishPerTile() {
    Player p1 =
        new MctsPlayer(
            "Max New Fish Per Tile",
            4,
            "B",
            new MctsPlacement(0.35, 50),
            new MctsMovement(0.35, 50, MovementHeuristicType.MAX_NEW_FISH_PER_TILE));
    Player p2 = new RandomPlayer("Random", 4, "R");
    Game game = new Game(new Player[] {p1, p2}, false, true);
    game.start();

    p1.reset();
    p2 = new GreedyPlayer("Greedy", 4, "R");
    game = new Game(new Player[] {p1, p2}, false, true);
    game.start();

    p1.reset();
    p2 = new MctsPlayer("MCTS LP", 4, "R");
    game = new Game(new Player[] {p1, p2}, false, true);
    game.start();
  }
}
