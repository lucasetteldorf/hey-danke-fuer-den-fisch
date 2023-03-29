package experiments;

import experiments.utility.ExperimentSetup;

public class BasicMcts {
  private static final int NUMBER_OF_GAMES = 1;

  public static void main(String[] args) {
    // mcts vs. random
    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "mcts-vs-random");

    // random vs. mcts
    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "random-vs-mcts");

    // mcts vs. greedy
    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "mcts-vs-greedy");

    // greedy vs. mcts
    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "greedy-vs-mcts");

    // mcts vs. mcts
    ExperimentSetup.playGames(NUMBER_OF_GAMES, 2, true, "basic-mcts", "mcts-vs-mcts");
  }
}
