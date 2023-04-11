package experiments;

import experiments.utility.ExperimentSetup;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;

public class MctsSimulationTimes {
  private static final int[] SIMULATION_TIMES = new int[] {1000};

  public static void main(String[] args) {
    double c = 1 / Math.sqrt(2);
    int numberOfGames = 1000;

    Player p1 = new MctsPlayer("MCTS LP", 4, "B", c, 500);
    Player p2 = new GreedyPlayer("Greedy", 4, "R");
    ExperimentSetup.playGames(
            new Player[] {p1, p2},
            numberOfGames,
            "/Users/Lucas/thesis-data-new/mcts-simulation-times/mcts-vs-random-"
                    + 500
                    + "ms.txt");

    for (int simulationTime : SIMULATION_TIMES) {
      p1 = new MctsPlayer("MCTS LP", 4, "B", c, simulationTime);
      p2 = new RandomPlayer("Random", 4, "R");
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames,
          "/Users/Lucas/thesis-data-new/mcts-simulation-times/mcts-vs-random-"
              + simulationTime
              + "ms.txt");

      p1 = new MctsPlayer("MCTS LP", 4, "B", c, simulationTime);
      p2 = new GreedyPlayer("Greedy", 4, "R");
      ExperimentSetup.playGames(
          new Player[] {p1, p2},
          numberOfGames,
          "/Users/Lucas/thesis-data-new/mcts-simulation-times/mcts-vs-greedy-"
              + simulationTime
              + "ms.txt");
    }
  }
}
