package ai;

import game.Game;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.algorithm.MctsMovement;
import mcts.algorithm.MctsPlacement;
import mcts.heavyplayout.PlacementHeuristicType;
import org.junit.jupiter.api.Test;

public class PlacementHeuristicsTest {
  @Test
  void testMaxFishPerTilePlayer1() {
    Player p1 =
        new MctsPlayer(
            "Max Fish Per Tile",
            4,
            "B",
            new MctsPlacement(0.35, 100, PlacementHeuristicType.MAX_FISH_PER_TILE),
            new MctsMovement(0.35, 100));
    Player p2 = new GreedyPlayer("Greedy", 4, "R");
    Game game = new Game(new Player[]{p1, p2}, true, true);
    game.start();
  }

  @Test
  void testMaxFishPerTilePlayer2() {
    Player p1 = new GreedyPlayer("Greedy", 4, "B");
    Player p2 =
            new MctsPlayer(
                    "Max Fish Per Tile",
                    4,
                    "R",
                    new MctsPlacement(0.35, 100, PlacementHeuristicType.MAX_FISH_PER_TILE),
                    new MctsMovement(0.35, 100));
    Game game = new Game(new Player[]{p1, p2}, true, true);
    game.start();
  }
}
