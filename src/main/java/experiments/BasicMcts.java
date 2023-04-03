package experiments;

import experiments.utility.ExperimentSetup;

public class BasicMcts {
  private static final int[] NUMBER_OF_GAMES = new int[] {100, 250, 500, 1000};
  private static final double[] C_VALUES =
      new double[] {4.5, 5};
  private static final int[] TIME_BUDGETS = new int[] {10, 50, 100, 250, 500, 1000};

  public static void main(String[] args) {
    //    for (int numberOfGames : NUMBER_OF_GAMES) {
    //      // mcts vs. random
    //      ExperimentSetup.playGames(numberOfGames, 2, true, "basic-mcts", "mcts-vs-random");
    //      System.out.println(numberOfGames + " MCTS vs. Random");
    //    }

    //    for (int numberOfGames : NUMBER_OF_GAMES) {
    //      // mcts vs. greedy
    //      ExperimentSetup.playGames(numberOfGames, 2, true, "basic-mcts", "mcts-vs-greedy");
    //      System.out.println(numberOfGames + " MCTS vs. Greedy");
    //    }

    // test different time budgets
    //    ExperimentSetup.playGames(2000, 2, true, "mcts-time-budget", "mcts-time-budget", 1000,
    // -1);
    //    System.out.println("1000ms time budget done");

    // test different values for C parameter
    for (double c : C_VALUES) {
      ExperimentSetup.playGames(2000, 2, true, "mcts-c-value", "mcts-c-value-random", 10, c);
      System.out.println("C value " + c + " MCTS vs. Random done");
      ExperimentSetup.playGames(2000, 2, true, "mcts-c-value", "mcts-c-value-mcts", 10, c);
      System.out.println("C value " + c + " MCTS vs. MCTS with C = sqrt(2) done");
    }

    // random vs. mcts
    //    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "random-vs-mcts");

    // greedy vs. mcts
    //    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "greedy-vs-mcts");

    // mcts vs. mcts
    //    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "mcts-vs-mcts");
  }
}
