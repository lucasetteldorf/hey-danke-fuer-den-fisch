package experiments;

import experiments.utility.OutputWriter;
import experiments.utility.PlayerUtility;
import game.Game;
import game.players.GreedyPlayer;
import game.players.Player;
import game.players.RandomPlayer;

public class Baseline {
  private static final int NUMBER_OF_GAMES = 50000;

  public static void main(String[] args) {
    randomVsRandom();
    randomVsGreedy();
    greedyVsGreedy();
  }

  public static void randomVsRandom() {
    PlayerUtility playerUtility = new PlayerUtility(NUMBER_OF_GAMES, 2);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      RandomPlayer p1 = new RandomPlayer("Random 1", 4, "B");
      RandomPlayer p2 = new RandomPlayer("Random 2", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        playerUtility.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        playerUtility.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      playerUtility.updatePlayerWinCount(game.getBoard().getWinnerIndex());
    }

    OutputWriter outputWriter =
        new OutputWriter(
            "/Users/Lucas/thesis-data/baseline/random-vs-random-stats-" + NUMBER_OF_GAMES + ".txt");
    outputWriter.writeStatistics(playerUtility, players);
  }

  public static void randomVsGreedy() {
    PlayerUtility playerUtility = new PlayerUtility(NUMBER_OF_GAMES, 2);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      RandomPlayer p1 = new RandomPlayer("Random", 4, "B");
      GreedyPlayer p2 = new GreedyPlayer("Greedy", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        playerUtility.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        playerUtility.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      playerUtility.updatePlayerWinCount(game.getBoard().getWinnerIndex());
    }

    OutputWriter outputWriter =
            new OutputWriter(
                    "/Users/Lucas/thesis-data/baseline/random-vs-greedy-stats-" + NUMBER_OF_GAMES + ".txt");
    outputWriter.writeStatistics(playerUtility, players);
  }

  public static void greedyVsGreedy() {
    PlayerUtility playerUtility = new PlayerUtility(NUMBER_OF_GAMES, 2);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      GreedyPlayer p1 = new GreedyPlayer("Greedy 1", 4, "B");
      GreedyPlayer p2 = new GreedyPlayer("Greedy 2", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        playerUtility.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        playerUtility.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      playerUtility.updatePlayerWinCount(game.getBoard().getWinnerIndex());
    }

    OutputWriter outputWriter =
            new OutputWriter(
                    "/Users/Lucas/thesis-data/baseline/greedy-vs-greedy-" + NUMBER_OF_GAMES + ".txt");
    outputWriter.writeStatistics(playerUtility, players);
  }
}
