package experiments.utility;

import game.players.MctsPlayer;
import game.players.Player;
import game.players.PlayerType;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputWriter {
  private PrintWriter printWriter;

  public OutputWriter(String filePath) {
    try {
      this.printWriter = new PrintWriter(new FileWriter(filePath));
    } catch (IOException e) {
      System.err.println("Invalid file path.");
    }
  }

  public void writeStatistics(GameStatistics gameStatistics, Player[] players) {
    printWriter.println(
        players[0].getName()
            + " vs. "
            + players[1].getName()
            + " ("
            + gameStatistics.getNumberOfGames()
            + " games)");
    printWriter.println(
        players[0].getName()
            + " average collected fish count: "
            + gameStatistics.getAverageFishCount(0));
    printWriter.println(
        players[1].getName()
            + " average collected fish count: "
            + gameStatistics.getAverageFishCount(1));
    printWriter.println(
        players[0].getName()
            + " average performed moves: "
            + gameStatistics.getAverageMoveCount(0));
    printWriter.println(
        players[1].getName()
            + " average performed moves: "
            + gameStatistics.getAverageMoveCount(1));
    if (gameStatistics.isMctsInGame()) {
      for (Player player : players) {
        if (player.getType() == PlayerType.MCTS) {
          MctsPlayer mctsPlayer = (MctsPlayer) player;
          String name = mctsPlayer.getName();
          printWriter.println(
              name + " C value for UCT selection (placement): " + mctsPlayer.getPlacementC());
          printWriter.println(
              name
                  + " placement simulation time : "
                  + mctsPlayer.getPlacementSimulationTime()
                  + "ms");
          printWriter.println(
              name
                  + " average placement simulations: "
                  + gameStatistics.getAverageMctsPlacementSimulations());
          printWriter.println(
              name + " C value for UCT selection (movement): " + mctsPlayer.getMovementC());
          printWriter.println(
              name
                  + " movement simulation time: "
                  + mctsPlayer.getMovementSimulationTime()
                  + "ms");
          printWriter.println(
              name
                  + " average movement simulations: "
                  + gameStatistics.getAverageMctsMovementSimulations());
          printWriter.println(
              name
                  + " average total simulations. "
                  + gameStatistics.getAverageMctsTotalSimulations());
        }
      }
    }
    printWriter.println(players[0].getName() + " win rate: " + gameStatistics.getWinRate(0));
    printWriter.println(players[1].getName() + " win rate: " + gameStatistics.getWinRate(1));
    printWriter.println("Number of ties: " + gameStatistics.getTies());
    printWriter.println("Percentage of ties: " + gameStatistics.getTiePercentage());
    printWriter.println("------------------------------------------------------------");
    printWriter.close();
  }
}
