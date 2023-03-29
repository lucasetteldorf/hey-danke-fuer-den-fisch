package experiments.utility;

import game.players.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mcts.MctsMovement;
import mcts.MctsPlacement;

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
      printWriter.println(
          "MCTS placement simulation time : " + MctsPlacement.getSimulationTime() + "ms");
      printWriter.println(
          "MCTS movement simulation time: " + MctsMovement.getSimulationTime() + "ms");
      printWriter.println(
          "MCTS average placement simulations: "
              + gameStatistics.getAverageMctsPlacementSimulations()
              + " / "
              + MctsPlacement.getSimulationTime()
              + "ms");
      printWriter.println(
          "MCTS average movement simulations: "
              + gameStatistics.getAverageMctsMovementSimulations()
              + " / "
              + MctsMovement.getSimulationTime()
              + "ms");
      printWriter.println(
          "MCTS average total simulations. " + gameStatistics.getAverageMctsTotalSimulations());
    }
    printWriter.println(players[0].getName() + " win rate: " + gameStatistics.getWinRate(0));
    printWriter.println(players[1].getName() + " win rate: " + gameStatistics.getWinRate(1));
    printWriter.println("Number of ties: " + gameStatistics.getTies());
    printWriter.println("Percentage of ties: " + gameStatistics.getTiePercentage());
    printWriter.println("------------------------------------------------------------");
    printWriter.close();
  }
}
