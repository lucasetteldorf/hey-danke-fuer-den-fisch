package experiments;

public class Baseline {
  private static final int[] NUMBER_OF_GAMES = new int[] {100, 1000, 10000, 50000};

  public static void main(String[] args) {
    for (int numberOfGames : NUMBER_OF_GAMES) {
      // random vs. random
      //      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "random-vs-random", -1,
      // -1);

      // random vs. greedy
      //      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "random-vs-greedy", -1,
      // -1);

      // greedy vs. random
      //      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "greedy-vs-random", -1,
      // -1);

      // greedy vs. greedy
      //      ExperimentSetup.playGames(numberOfGames, 2, false, "baseline", "greedy-vs-greedy", -1,
      // -1);
    }
  }
}
