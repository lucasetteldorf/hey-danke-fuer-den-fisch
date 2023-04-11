package experiments.utility;

import game.players.MctsPlayer;
import game.players.Player;
import game.players.PlayerType;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextWriter {
  public static void writeStatistics(GameStatistics gameStatistics, Player[] players, String path)
      throws IOException {
    PrintWriter printWriter = new PrintWriter(new FileWriter(path));

    printWriter.println(
        players[0].getName()
            + " vs. "
            + players[1].getName()
            + " ("
            + gameStatistics.getNumberOfGames()
            + " games)\n");
    printWriter.println(players[0].getName() + " win rate: " + gameStatistics.getWinRate(0));
    printWriter.println(players[1].getName() + " win rate: " + gameStatistics.getWinRate(1));
    printWriter.println("Number of ties: " + gameStatistics.getTies());
    printWriter.println("Percentage of ties: " + gameStatistics.getTiePercentage() + "\n");
    printWriter.println(
        players[0].getName()
            + " average collected fish count: "
            + gameStatistics.getAverageFishCount(0));
    printWriter.println(
        players[1].getName()
            + " average collected fish count: "
            + gameStatistics.getAverageFishCount(1)
            + "\n");
    printWriter.println(
        players[0].getName()
            + " average performed moves: "
            + gameStatistics.getAverageMoveCount(0));
    printWriter.println(
        players[1].getName()
            + " average performed moves: "
            + gameStatistics.getAverageMoveCount(1)
            + "\n");
    for (int i = 0; i < players.length; i++) {
      Player player = players[i];
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
                + gameStatistics.getAverageMctsPlacementSimulations(i));
        printWriter.println(
            name + " C value for UCT selection (movement): " + mctsPlayer.getMovementC());
        printWriter.println(
            name + " movement simulation time: " + mctsPlayer.getMovementSimulationTime() + "ms");
        printWriter.println(
            name
                + " average movement simulations: "
                + gameStatistics.getAverageMctsMovementSimulations(i));
        printWriter.println(
            name
                + " average total simulations. "
                + gameStatistics.getAverageMctsTotalSimulations(i)
                + "\n");
      }
    }
    printWriter.close();
  }
}
