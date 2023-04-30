package experiments;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.algorithm.HeuristicType;
import utility.Resources;

public class MctsLightPlayoutBaseline {
  public static void startExperiments(double c, int simulationTime) {
    int numberOfGames = 3000;
    Player p1 =
        new MctsPlayer(
            "MCTS LP Baseline 1",
            4,
            "B",
            HeuristicType.NONE,
            HeuristicType.NONE,
            c,
            simulationTime);
    Player p2 =
        new MctsPlayer(
            "MCTS LP Baseline 2",
            4,
            "R",
            HeuristicType.NONE,
            HeuristicType.NONE,
            c,
            simulationTime);
    ExperimentSetup.playGames(
        new Player[] {p1, p2}, numberOfGames, Resources.ROOT_DIR + "lp-baseline/baseline");
  }
}
