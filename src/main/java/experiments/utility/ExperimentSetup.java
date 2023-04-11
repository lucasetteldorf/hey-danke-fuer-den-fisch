package experiments.utility;

import game.Game;
import game.players.*;
import java.io.IOException;

public class ExperimentSetup {
  public static void playGames(Player[] players, int numberOfGames, String path) {
    GameStatistics gameStatistics = new GameStatistics(numberOfGames, players.length);
    CsvWriter.createCsv(path);
    Game game;
    for (int i = 1; i <= numberOfGames; i++) {
      game = new Game(players, false, false);
      game.start();
      for (int j = 0; j < players.length; j++) {
        gameStatistics.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        gameStatistics.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      gameStatistics.updatePlayerWinCount(game.getBoard().getWinnerIndex());
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
      CsvWriter.appendLineToCsv(path, game);
      for (Player player : players) {
        player.reset();
      }
      System.out.println("Game " + i + " done");
    }
    try {
      TextWriter.writeStatistics(gameStatistics, players, path);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    System.out.println(numberOfGames + " games done (" + path + ")");
  }
}
