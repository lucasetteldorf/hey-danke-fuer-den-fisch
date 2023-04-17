package experiments;

import experiments.utility.ExperimentSetup;
import game.players.GreedyPlayer;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.heavyplayout.MovementHeuristicType;
import mcts.heavyplayout.PlacementHeuristicType;
import utility.Resources;

public class MctsHeavyPlayoutPlacement {
  public static void startExperiments(int numberOfGames, double c, int simulationTime) {
    String path = Resources.ROOT_DIR + "mcts-heavy-playout-placement/";
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

    file = "lp-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p5}, numberOfGames, path + file);

    numberOfGames /= 2;

    file = "lp-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p3}, numberOfGames, path + file);
    file = "greedy-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p5}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MOTFT 1",
            4,
            "B",
            PlacementHeuristicType.MOTFT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "motft-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "motft-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MOTFT 2",
            4,
            "R",
            PlacementHeuristicType.MOTFT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "greedy-vs-motft-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-motft-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP METFT 1",
            4,
            "B",
            PlacementHeuristicType.METFT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "metft-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "metft-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP METFT 2",
            4,
            "R",
            PlacementHeuristicType.METFT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "greedy-vs-metft-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-metft-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MOTFC 1",
            4,
            "B",
            PlacementHeuristicType.MOTFC,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "motfc-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "motfc-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MOTFC 2",
            4,
            "R",
            PlacementHeuristicType.MOTFC,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "greedy-vs-motfc-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-motfc-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP METFC 1",
            4,
            "B",
            PlacementHeuristicType.METFC,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "metfc-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "metfc-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP METFC 2",
            4,
            "R",
            PlacementHeuristicType.METFC,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "greedy-vs-metfc-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-metfc-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP MOTT 1",
            4,
            "B",
            PlacementHeuristicType.MOTT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "mott-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "mott-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP MOTT 2",
            4,
            "R",
            PlacementHeuristicType.MOTT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "greedy-vs-mott-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-mott-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);

    p1 =
        new MctsPlayer(
            "MCTS HP METT 1",
            4,
            "B",
            PlacementHeuristicType.METT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "mett-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    file = "mett-vs-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p1, p5}, numberOfGames, path + file);
    p1 =
        new MctsPlayer(
            "MCTS HP METT 2",
            4,
            "R",
            PlacementHeuristicType.METT,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = "greedy-vs-mett-baseline";
    ExperimentSetup.playGames(new Player[] {p2, p1}, numberOfGames, path + file);
    file = "lp-vs-mett-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p1}, numberOfGames, path + file);
  }
}
