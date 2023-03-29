package experiments;

import experiments.utility.ExperimentSetup;

public class Baseline {
  private static final int[] NUMBER_OF_GAMES = new int[] {100, 1000, 10000, 50000};

  public static void main(String[] args) {
    for (int numberOfGames : NUMBER_OF_GAMES) {
      // random vs. random
      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "random-vs-random");

      // random vs. greedy
      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "random-vs-greedy");

      // greedy vs. random
      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "greedy-vs-random");

      // greedy vs. greedy
      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "greedy-vs-greedy");
    }
  }
}
