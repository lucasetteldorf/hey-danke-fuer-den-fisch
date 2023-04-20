package experiments;

import experiments.utility.ExperimentSetup;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.heavyplayout.MovementHeuristicType;
import mcts.heavyplayout.PlacementHeuristicType;
import utility.Resources;

public class MctsHeavyPlayoutMovement {
  public static void startExperiments(int numberOfGames, double c, int simulationTime) {
    String path = Resources.ROOT_DIR + "mcts-heavy-playout-movement-fixed/";
    String file;
    Player p1;
    Player p2 = new GreedyPlayer("Greedy Baseline 1", 4, "B");
    Player p3 = new GreedyPlayer("Greedy Baseline 2", 4, "R");
    Player p4 =
        new MctsPlayer(
            "MCTS LP Baseline 1",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    Player p5 =
        new MctsPlayer(
            "MCTS LP Baseline 2",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.NONE,
            c,
            simulationTime);

    numberOfGames /= 2;

    p1 =
            new MctsPlayer(
                    "MCTS HP MORTFT 1",
                    4,
                    "B",
                    PlacementHeuristicType.NONE,
                    MovementHeuristicType.MORTFT,
                    c,
                    simulationTime);
    file = "mortft-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "g-vs-mortft-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
            new MctsPlayer(
                    "MCTS HP MORTFT 2",
                    4,
                    "R",
                    PlacementHeuristicType.NONE,
                    MovementHeuristicType.MORTFT,
                    c,
                    simulationTime);
    file = "greedy-vs-mortft-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-mortft-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
            new MctsPlayer(
                    "MCTS HP MERTFT 1",
                    4,
                    "B",
                    PlacementHeuristicType.NONE,
                    MovementHeuristicType.MERTFT,
                    c,
                    simulationTime);
    file = "mertft-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "g-vs-mertft-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
            new MctsPlayer(
                    "MCTS HP MERTFT 2",
                    4,
                    "R",
                    PlacementHeuristicType.NONE,
                    MovementHeuristicType.MERTFT,
                    c,
                    simulationTime);
    file = "greedy-vs-mertft-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-mertft-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP G 1",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.G,
            c,
            simulationTime);
    file = "g-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "g-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP G 2",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.G,
            c,
            simulationTime);
    file = "greedy-vs-g-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-g-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MORFC 1",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORFC,
            c,
            simulationTime);
    file = "morfc-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "morfc-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MORFC 2",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORFC,
            c,
            simulationTime);
    file = "greedy-vs-morfc-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-morfc-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MERFC 1",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERFC,
            c,
            simulationTime);
    file = "merfc-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "merfc-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MERFC 2",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERFC,
            c,
            simulationTime);
    file = "greedy-vs-merfc-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-merfc-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MORT 1",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORT,
            c,
            simulationTime);
    file = "mort-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "mort-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MORT 2",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MORT,
            c,
            simulationTime);
    file = "greedy-vs-mort-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-mort-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MERT 1",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERT,
            c,
            simulationTime);
    file = "mert-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "mert-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MERT 2",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MERT,
            c,
            simulationTime);
    file = "greedy-vs-mert-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-mert-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);
  }
}
