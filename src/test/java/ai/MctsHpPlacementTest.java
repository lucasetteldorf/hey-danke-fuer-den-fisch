package ai;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import mcts.heavyplayout.MovementHeuristicType;
import mcts.heavyplayout.PlacementHeuristicType;
import org.junit.jupiter.api.Test;

public class MctsHpPlacementTest {
  @Test
  void testMaxFishPerTileForAllPenguins() {
    Player p1 =
        new MctsPlayer(
            "MCTS Max Fish Per Tile For All Penguins Placement",
            4,
            "B",
            PlacementHeuristicType.MAX_FISH_PER_TILE_FOR_ALL_PENGUINS,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            10);
    Player p2 = new RandomPlayer("Random", 4, "R");
    ExperimentSetup.playGames(new Player[] {p1, p2}, 100, "/Users/Lucas/test");
  }
}
