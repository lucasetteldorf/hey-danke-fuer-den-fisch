package experiments;

import experiments.utility.ExperimentSetup;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import utility.Resources;

public class MctsLightPlayoutCValue {
  private static final double[] C_VALUES =
      new double[] {0.1, 0.2, 0.3, 0.4, 0.5};

  public static void startExperiments(int numberOfGames, int simulationTime) {
    for (double cValue : C_VALUES) {
      Player p1 = new MctsPlayer("MCTS LP (C = " + cValue + ")", 4, "B", cValue, simulationTime);
      Player p2 =
          new MctsPlayer(
              "MCTS LP Baseline (C = " + Math.sqrt(2) + ")", 4, "R", Math.sqrt(2), simulationTime);
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames / 2,
          Resources.ROOT_DIR
              + "mcts-light-playout-c-values-2/mcts-vs-baseline-"
              + cValue);
      ExperimentSetup.playGames(
          new Player[] {p2, p1},
          numberOfGames / 2,
          Resources.ROOT_DIR
              + "mcts-light-playout-c-values-2/baseline-vs-mcts-"
              + cValue);
    }
  }
}
