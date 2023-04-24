package experiments;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.algorithm.HeuristicType;
import utility.Resources;

public class MctsHeavyPlayoutPlacement {
  public static void startExperiments(int numberOfGames, double c, int simulationTime) {
    String path = Resources.ROOT_DIR + "hp-placement/";
    String file;
    Player p1;
    Player p2 =
        new MctsPlayer(
            "MCTS LP Baseline 1",
            4,
            "B",
            HeuristicType.NONE,
            HeuristicType.NONE,
            c,
            simulationTime);
    Player p3 =
        new MctsPlayer(
            "MCTS LP Baseline 2",
            4,
            "R",
            HeuristicType.NONE,
            HeuristicType.NONE,
            c,
            simulationTime);

//    file = "lp-vs-lp-baseline";
//    ExperimentSetup.playGames(new Player[] {p2, p3}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MORTFT 1",
            4,
            "B",
            HeuristicType.MORTFT,
            HeuristicType.NONE,
            c,
            simulationTime);
    file = "mortft-vs-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MORTFT 2",
            4,
            "R",
            HeuristicType.MORTFT,
            HeuristicType.NONE,
            c,
            simulationTime);
    file = "lp-vs-mortft";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MERTFT 1",
            4,
            "B",
            HeuristicType.MERTFT,
            HeuristicType.NONE,
            c,
            simulationTime);
    file = "mertft-vs-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MERTFT 2",
            4,
            "R",
            HeuristicType.MERTFT,
            HeuristicType.NONE,
            c,
            simulationTime);
    file = "lp-vs-mertft";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MORFC 1", 4, "B", HeuristicType.MORFC, HeuristicType.NONE, c, simulationTime);
    file = "morfc-vs-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MORFC 2", 4, "R", HeuristicType.MORFC, HeuristicType.NONE, c, simulationTime);
    file = "lp-vs-morfc";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MERFC 1", 4, "B", HeuristicType.MERFC, HeuristicType.NONE, c, simulationTime);
    file = "merfc-vs-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MERFC 2", 4, "R", HeuristicType.MERFC, HeuristicType.NONE, c, simulationTime);
    file = "lp-vs-merfc";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MORT 1", 4, "B", HeuristicType.MORT, HeuristicType.NONE, c, simulationTime);
    file = "mort-vs-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MORT 2", 4, "R", HeuristicType.MORT, HeuristicType.NONE, c, simulationTime);
    file = "lp-vs-mort";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MERT 1", 4, "B", HeuristicType.MERT, HeuristicType.NONE, c, simulationTime);
    file = "mert-vs-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MERT 2", 4, "R", HeuristicType.MERT, HeuristicType.NONE, c, simulationTime);
    file = "lp-vs-mert";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
  }
}
