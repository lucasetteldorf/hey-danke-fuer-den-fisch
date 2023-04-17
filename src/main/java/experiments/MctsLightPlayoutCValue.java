package experiments;

import experiments.utility.ExperimentSetup;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import utility.Resources;

public class MctsLightPlayoutCValue {
  private static final double[] C_VALUES =
      new double[] {0.0, 0.5, 1.0, Math.sqrt(2), 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};

  public static void startExperiments(int numberOfGames, int simulationTime) {
    for (double cValue : C_VALUES) {
      Player p1 = new MctsPlayer("MCTS LP (C = " + cValue + ")", 4, "B", cValue, simulationTime);
      Player p2 = new GreedyPlayer("Greedy", 4, "R");
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames / 2,
          Resources.ROOT_DIR + "mcts-light-playout-c-values/mcts-vs-greedy-c-value-" + cValue);
      ExperimentSetup.playGames(
          new Player[] {p2, p1},
          numberOfGames / 2,
          Resources.ROOT_DIR + "mcts-light-playout-c-values/greedy-vs-mcts-c-value-" + cValue);

      p1 = new MctsPlayer("MCTS LP (C = " + cValue + ")", 4, "B", cValue, simulationTime);
      p2 =
          new MctsPlayer(
              "MCTS LP Baseline (C = " + Math.sqrt(2) + ")", 4, "R", Math.sqrt(2), simulationTime);
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames / 2,
          Resources.ROOT_DIR
              + "mcts-light-playout-c-values/mcts-vs-baseline-mcts-c-value-"
              + cValue);
      ExperimentSetup.playGames(
          new Player[] {p2, p1},
          numberOfGames / 2,
          Resources.ROOT_DIR
              + "mcts-light-playout-c-values/baseline-mcts-vs-mcts-c-value-"
              + cValue);
    }
  }
}
