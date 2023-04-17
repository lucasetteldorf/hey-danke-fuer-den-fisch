package ai;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.heavyplayout.MovementHeuristicType;
import mcts.heavyplayout.PlacementHeuristicType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MctsHpPlacementTest {
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
  void testMaxOwnThreeFishTiles1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MOTFT)",
            4,
            "B",
            PlacementHeuristicType.MOTFT,
            MovementHeuristicType.NONE,
            Math.sqrt(2), 10);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "motft-1");
  }

  @Test
  void testMaxOwnThreeFishTiles2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MOTFT)",
            4,
            "B",
            PlacementHeuristicType.MOTFT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "motft-2");
  }

  @Test
  void testMinEnemyThreeFishTiles1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (METFT)",
            4,
            "B",
            PlacementHeuristicType.METFT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "metft-1");
  }

  @Test
  void testMinEnemyThreeFishTiles2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (METFT)",
            4,
            "B",
            PlacementHeuristicType.METFT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "metft-2");
  }

  @Test
  void testMaxOwnTotalFishCount1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MOTFC)",
            4,
            "B",
            PlacementHeuristicType.MOTFC,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "motfc-1");
  }

  @Test
  void testMaxOwnTotalFishCount2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MOTFC)",
            4,
            "B",
            PlacementHeuristicType.MOTFC,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "motfc-2");
  }

  @Test
  void testMinEnemyTotalFishCount1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (METFC)",
            4,
            "B",
            PlacementHeuristicType.METFC,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "metfc-1");
  }

  @Test
  void testMinEnemyTotalFishCount2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (METFC)",
            4,
            "B",
            PlacementHeuristicType.METFC,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "metfc-2");
  }

  @Test
  void testMaxOwnTotalTiles1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MOTT)",
            4,
            "B",
            PlacementHeuristicType.MOTT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "mott-1");
  }

  @Test
  void testMaxOwnTotalTiles2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (MOTT)",
            4,
            "B",
            PlacementHeuristicType.MOTT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "mott-2");
  }

  @Test
  void testMinEnemyTotalTiles1() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (METT)",
            4,
            "B",
            PlacementHeuristicType.METT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p1, p2}, 20, path + "mett-1");
  }

  @Test
  void testMinEnemyTotalTiles2() {
    Player p1 =
        new MctsPlayer(
            "MCTS HP (METT)",
            4,
            "B",
            PlacementHeuristicType.METT,
            MovementHeuristicType.NONE,
            Math.sqrt(2),
            20);
    ExperimentSetup.playGames(new Player[] {p2, p1}, 20, path + "mett-2");
  }
}
