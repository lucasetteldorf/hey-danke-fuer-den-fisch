package experiments;

import experiments.utility.ExperimentSetup;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import utility.Resources;

public class MctsLightPlayoutSimulationTimes {
  private static final int[] SIMULATION_TIMES = new int[] {1000};

  public static void startExperiments(int numberOfGames, double c) {
    for (int simulationTime : SIMULATION_TIMES) {
      Player p1 = new MctsPlayer("MCTS LP", 4, "B", c, simulationTime);
      Player p2 = new RandomPlayer("Random", 4, "R");
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames,
          Resources.ROOT_DIR + "mcts-light-playout-simulation-times/mcts-vs-random-" + simulationTime + "ms");

      p1 = new MctsPlayer("MCTS LP", 4, "B", c, simulationTime);
      p2 = new GreedyPlayer("Greedy", 4, "R");
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames,
          Resources.ROOT_DIR + "mcts-light-playout-simulation-times/mcts-vs-greedy-" + simulationTime + "ms");
    }
  }
}
