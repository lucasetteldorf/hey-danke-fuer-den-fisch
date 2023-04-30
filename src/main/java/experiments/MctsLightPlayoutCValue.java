package experiments;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import utility.Resources;

public class MctsLightPlayoutCValue {
  private static final double[] C_VALUES =
      new double[] {
        0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8,
        1.9, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
      };

  public static void startExperiments(int numberOfGames, int simulationTime) {
    for (double cValue : C_VALUES) {
      Player p1 = new MctsPlayer("MCTS LP (C = " + cValue + ")", 4, "B", cValue, simulationTime);
      Player p2 =
          new MctsPlayer(
              "MCTS LP Baseline (C = " + Math.sqrt(2) + ")", 4, "R", Math.sqrt(2), simulationTime);
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames,
          Resources.ROOT_DIR + "lp-c-value/mcts-vs-baseline-" + cValue);
      ExperimentSetup.playGames(
          new Player[] {p2, p1},
          numberOfGames,
          Resources.ROOT_DIR + "lp-c-value/baseline-vs-mcts-" + cValue);
    }
  }
}
