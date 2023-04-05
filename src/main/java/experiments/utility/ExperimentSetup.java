package experiments.utility;

import game.Game;
import game.players.*;

public class ExperimentSetup {
  public static void playGames(
      int numberOfGames,
      int playerCount,
      boolean isMctsInGame,
      String directoryName,
      String mode,
      int timeBudget,
      double c) {
    GameStatistics gameStatistics = new GameStatistics(numberOfGames, playerCount, isMctsInGame);

    String path =
        "/Users/Lucas/thesis-data/"
            + directoryName
            + "/"
            + mode
            + "-stats-"
            + numberOfGames
            + ".txt";
    Game game;
    Player[] players = new Player[2];
    System.out.println(numberOfGames + " games starting...");
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
        case "mcts-time-budget" -> {
          p1 = new MctsPlayer("MCTS", 4, "B", Math.sqrt(2), timeBudget);
          p2 = new RandomPlayer("Random", 4, "R");
          path =
              "/Users/Lucas/thesis-data/"
                  + directoryName
                  + "/"
                  + mode
                  + "-"
                  + timeBudget
                  + "ms.txt";
        }
        case "mcts-time-budget-greedy" -> {
          p1 = new MctsPlayer("MCTS", 4, "B", Math.sqrt(2), timeBudget);
          p2 = new GreedyPlayer("Greedy", 4, "R");
          path =
              "/Users/Lucas/thesis-data/"
                  + directoryName
                  + "/"
                  + mode
                  + "-"
                  + timeBudget
                  + "ms.txt";
        }
        case "mcts-c-value-random" -> {
          p1 = new MctsPlayer("MCTS", 4, "B", c, timeBudget);
          p2 = new RandomPlayer("Random", 4, "R");
        }
        case "mcts-c-value-greedy" -> {
          p1 = new MctsPlayer("MCTS", 4, "B", c, timeBudget);
          p2 = new GreedyPlayer("Greedy", 4, "R");
        }
        case "mcts-c-value-mcts" -> {
          p1 = new MctsPlayer("MCTS 1 (C value test)", 4, "B", c, timeBudget);
          p2 = new MctsPlayer("MCTS 2 (C value baseline)", 4, "R", Math.sqrt(2), timeBudget);
        }
        case "mcts-c-value-mcts-2" -> {
          p1 = new MctsPlayer("MCTS, C = " + c, 4, "B", c, timeBudget);
          p2 = new MctsPlayer("MCTS, C = 0.5", 4, "R", 0.5, timeBudget);
        }
      }
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        gameStatistics.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        gameStatistics.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      gameStatistics.updatePlayerWinCount(game.getBoard().getWinnerIndex());
      if (isMctsInGame) {
        for (int j = 0; j < players.length; j++) {
          Player player = players[j];
          if (player.getType() == PlayerType.MCTS) {
            MctsPlayer mctsPlayer = (MctsPlayer) player;
            gameStatistics.updateAverageMctsPlacementSimulations(
                j, mctsPlayer.getAveragePlacementSimulations());
            gameStatistics.updateAverageMctsMovementSimulations(
                j, mctsPlayer.getAverageMovementSimulations());
            gameStatistics.updateAverageMctsTotalSimulations(
                j, mctsPlayer.getAverageTotalSimulations());
          }
        }
      }
      System.out.println("Game " + (i + 1) + " done.");
    }

    if (c != -1) {
      path = "/Users/Lucas/thesis-data/" + directoryName + "/" + mode + "-" + c + ".txt";
    }

    OutputWriter outputWriter = new OutputWriter(path);
    outputWriter.writeStatistics(gameStatistics, players);
  }

  public static void updateStatistics(Game game, GameStatistics gameStatistics) {
    Player[] players = game.getBoard().getPlayers();
    boolean isMctsInGame = gameStatistics.isMctsInGame();

    for (int j = 0; j < players.length; j++) {
      gameStatistics.updatePlayerFishCount(j, players[j].getCollectedFishCount());
      gameStatistics.updatePlayerMoveCount(j, players[j].getMoveCount());
    }
    gameStatistics.updatePlayerWinCount(game.getBoard().getWinnerIndex());
    if (isMctsInGame) {
      for (int i = 0; i < players.length; i++) {
        Player player = players[i];
        if (player.getType() == PlayerType.MCTS) {
          MctsPlayer mctsPlayer = (MctsPlayer) player;
          gameStatistics.updateAverageMctsPlacementSimulations(
              i, mctsPlayer.getAveragePlacementSimulations());
          gameStatistics.updateAverageMctsMovementSimulations(
              i, mctsPlayer.getAverageMovementSimulations());
          gameStatistics.updateAverageMctsTotalSimulations(
              i, mctsPlayer.getAverageTotalSimulations());
        }
      }
    }
  }

  public static void writeStatistics(String path, Player[] players, GameStatistics gameStatistics) {
    OutputWriter outputWriter = new OutputWriter(path);
    outputWriter.writeStatistics(gameStatistics, players);
  }
}
