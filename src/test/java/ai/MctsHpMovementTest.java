package ai;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.heavyplayout.MovementHeuristicType;
import mcts.heavyplayout.PlacementHeuristicType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MctsHpMovementTest {
  static Player p2;
  static String path = "src/main/resources/test-data/";

  void resetBaseline() {
    p2 =
        new MctsPlayer(
            "MCTS LP (Baseline)",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
  }

  @BeforeEach
  void beforeEach() {
    resetBaseline();
  }

  @Test
  void testGreedy1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (G)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.G,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "g-1");
  }

  @Test
  void testGreedy2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (G)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.G,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "g-2");
  }

  @Test
  void testMaxOwnReachableFishCount1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MORFC)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORFC,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "morfc-1");
  }

  @Test
  void testMaxOwnReachableFishCount2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MORFC)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORFC,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "morfc-2");
  }

  @Test
  void testMinEnemyReachableFishCount1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MERFC)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERFC,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "merfc-1");
  }

  @Test
  void testMinEnemyReachableFishCount2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MERFC)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERFC,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "merfc-2");
  }

  @Test
  void testMaxOwnReachableTiles1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MORT)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORT,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "mort-1");
  }

  @Test
  void testMaxOwnReachableTiles2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MORT)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORT,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "mort-2");
  }

  @Test
  void testMinEnemyReachableTiles1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MERT)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERT,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "mert-1");
  }

  @Test
  void testMinEnemyReachableTiles2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MERT)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERT,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "mert-2");
  }
}
