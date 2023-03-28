package experiments.utility;

import game.players.Player;
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

  public void writeStatistics(PlayerUtility playerUtility, Player[] players) {
    printWriter.println(
        players[0].getName()
            + " vs. "
            + players[1].getName()
            + " ("
            + playerUtility.getNumberOfGames()
            + " games)");
    printWriter.println(
        players[0].getName()
            + " average collected fish count: "
            + playerUtility.getAverageFishCount(0));
    printWriter.println(
        players[1].getName()
            + " average collected fish count: "
            + playerUtility.getAverageFishCount(1));
    printWriter.println(
        players[0].getName() + " average performed moves: " + playerUtility.getAverageMoveCount(0));
    printWriter.println(
        players[1].getName() + " average performed moves: " + playerUtility.getAverageMoveCount(1));
    printWriter.println(players[0].getName() + " win rate: " + playerUtility.getWinRate(0));
    printWriter.println(players[1].getName() + " win rate: " + playerUtility.getWinRate(1));
    printWriter.println("--------------------------------------------------");
    printWriter.close();
  }
}
