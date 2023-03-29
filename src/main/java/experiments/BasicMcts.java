package experiments;

import experiments.utility.OutputWriter;
import experiments.utility.GameStatistics;
import game.Game;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;

public class BasicMcts {
  private static final int NUMBER_OF_GAMES = 2;

  public static void main(String[] args){
    mctsVsRandom();
//    mctsVsGreedy();
  }

  public static void mctsVsRandom() {
    GameStatistics gameStatistics = new GameStatistics(NUMBER_OF_GAMES, 2, true);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      MctsPlayer p1 = new MctsPlayer("MCTS", 4, "B");
      RandomPlayer p2 = new RandomPlayer("Random", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        gameStatistics.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        gameStatistics.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      gameStatistics.updatePlayerWinCount(game.getBoard().getWinnerIndex());
      gameStatistics.updateAverageMctsPlacementSimulations(p1.getAveragePlacementSimulations());
      gameStatistics.updateAverageMctsMovementSimulations(p1.getAverageMovementSimulations());
      gameStatistics.updateAverageMctsTotalSimulations(p1.getAverageTotalSimulations());
    }

    OutputWriter outputWriter =
        new OutputWriter(
            "/Users/Lucas/thesis-data/basic-mcts/mcts-vs-random-stats-" + NUMBER_OF_GAMES + ".txt");
    outputWriter.writeStatistics(gameStatistics, players);
    }

  public static void mctsVsGreedy() {
    GameStatistics gameStatistics = new GameStatistics(NUMBER_OF_GAMES, 2, true);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      MctsPlayer p1 = new MctsPlayer("MCTS", 4, "B");
      GreedyPlayer p2 = new GreedyPlayer("Greedy", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        gameStatistics.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        gameStatistics.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      gameStatistics.updatePlayerWinCount(game.getBoard().getWinnerIndex());
    }

    OutputWriter outputWriter =
            new OutputWriter(
                    "/Users/Lucas/thesis-data/basic-mcts/mcts-vs-greedy-stats-" + NUMBER_OF_GAMES + ".txt");
    outputWriter.writeStatistics(gameStatistics, players);
  }
}
