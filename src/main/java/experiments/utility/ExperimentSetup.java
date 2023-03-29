package experiments.utility;

import game.Game;
import game.players.*;

public class ExperimentSetup {
  public static void playGames(
      int numberOfGames, int playerCount, boolean isMctsInGame, String directoryName, String mode) {
    GameStatistics gameStatistics = new GameStatistics(numberOfGames, playerCount, isMctsInGame);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < numberOfGames; i++) {
      Player p1 = null;
      Player p2 = null;
      switch (mode) {
        case "random-vs-random" -> {
          p1 = new RandomPlayer("Random 1", 4, "B");
          p2 = new RandomPlayer("Random 2", 4, "R");
        }
        case "random-vs-greedy" -> {
          p1 = new RandomPlayer("Random", 4, "B");
          p2 = new GreedyPlayer("Greedy", 4, "R");
        }
        case "greedy-vs-random" -> {
          p1 = new GreedyPlayer("Greedy", 4, "B");
          p2 = new RandomPlayer("Random", 4, "R");
        }
        case "greedy-vs-greedy" -> {
          p1 = new GreedyPlayer("Greedy 1", 4, "B");
          p2 = new GreedyPlayer("Greedy 2", 4, "R");
        }
        case "mcts-vs-random" -> {
          p1 = new MctsPlayer("MCTS", 4, "B");
          p2 = new RandomPlayer("Random", 4, "R");
        }
        case "random-vs-mcts" -> {
          p1 = new RandomPlayer("Random", 4, "B");
          p2 = new MctsPlayer("MCTS", 4, "R");
        }
        case "mcts-vs-greedy" -> {
          p1 = new MctsPlayer("MCTS", 4, "B");
          p2 = new GreedyPlayer("Greedy", 4, "R");
        }
        case "greedy-vs-mcts" -> {
          p1 = new GreedyPlayer("Greedy", 4, "B");
          p2 = new MctsPlayer("MCTS", 4, "R");
        }
        case "mcts-vs-mcts" -> {
          p1 = new MctsPlayer("MCTS 1", 4, "B");
          p2 = new MctsPlayer("MCTS 2", 4, "R");
        }
      }
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        gameStatistics.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        gameStatistics.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      gameStatistics.updatePlayerWinCount(game.getBoard().getWinnerIndex());
      if (isMctsInGame) {
        for (Player player : players) {
          if (player.getType() == PlayerType.MCTS) {
            MctsPlayer mctsPlayer = (MctsPlayer) player;
            gameStatistics.updateAverageMctsPlacementSimulations(
                mctsPlayer.getAveragePlacementSimulations());
            gameStatistics.updateAverageMctsMovementSimulations(
                mctsPlayer.getAverageMovementSimulations());
            gameStatistics.updateAverageMctsTotalSimulations(
                mctsPlayer.getAverageTotalSimulations());
          }
        }
      }
    }

    OutputWriter outputWriter =
        new OutputWriter(
            "/Users/Lucas/thesis-data/"
                + directoryName
                + "/"
                + mode
                + "-stats-"
                + numberOfGames
                + ".txt");
    outputWriter.writeStatistics(gameStatistics, players);
  }
}
