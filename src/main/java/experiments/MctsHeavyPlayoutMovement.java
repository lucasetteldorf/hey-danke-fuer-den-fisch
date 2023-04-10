package experiments;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.algorithm.MctsMovement;
import mcts.algorithm.MctsPlacement;
import mcts.heavyplayout.MovementHeuristicType;

public class MctsHeavyPlayoutMovement {
  public static void startExperiments(int numberOfGames, int simulationTime) {
    double c = 0.35;
    String path = "/Users/Lucas/thesis-data/mcts-heavy-playout-movement/";
    String file;
    Player p1;
    Player p2;

    file = "baseline-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP 1", 4, "B", c, simulationTime);
    p2 = new MctsPlayer("MCTS LP 2", 4, "R", c, simulationTime);
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "max-total-fish-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max Total Fish HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.MAX_TOTAL_FISH));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "max-total-fish-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max Total Fish HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.MAX_TOTAL_FISH));
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "max-new-fish-per-tile-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max New Fish Per Tile HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.MAX_NEW_FISH_PER_TILE));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "max-new-fish-per-tile-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max New Fish Per Tile HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.MAX_NEW_FISH_PER_TILE));
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "max-new-total-fish-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max New Total Fish HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.MAX_NEW_TOTAL_FISH));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "max-new-total-fish-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max New Total Fish HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.MAX_NEW_TOTAL_FISH));
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "isolate-opponent-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Isolate Opponent HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.ISOLATE_OPPONENT));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "isolate-opponent-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Isolate Opponent HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.ISOLATE_OPPONENT));
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "secure-area-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Secure Area HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.SECURE_AREA));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);

    file = "secure-area-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Secure Area HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime),
            new MctsMovement(c, simulationTime, MovementHeuristicType.SECURE_AREA));
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
  }
}
