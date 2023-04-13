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
    String path = Resources.ROOT_DIR + "mcts-heavy-playout-movement/";
    String file;
    Player p1;
    Player p2 = new GreedyPlayer("Greedy", 4, "R");
    Player p3 =
        new MctsPlayer(
            "MCTS LP Baseline (2)",
            4,
            "R",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.NONE,
            c,
            simulationTime);

    int experimentId = 0;

    Player p4 =
        new MctsPlayer(
            "MCTS LP Baseline (1)",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.NONE,
            c,
            simulationTime);
    file = experimentId + "-mcts-lp-vs-mcts-lp-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p3}, numberOfGames, path + file);
    file = experimentId + "-mcts-lp-vs-greedy-baseline";
    ExperimentSetup.playGames(new Player[] {p4, p2}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max Fish On New Tile Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_FISH_ON_NEW_TILE,
            c,
            simulationTime);
    file = experimentId + "-max-fish-on-new-tile-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-max-fish-on-new-tile-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max New Fish For Penguin Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_NEW_FISH_FOR_PENGUIN,
            c,
            simulationTime);
    file = experimentId + "-max-new-fish-for-penguin-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-max-new-fish-for-penguin-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max New Tiles For Penguin Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_NEW_TILES_FOR_PENGUIN,
            c,
            simulationTime);
    file = experimentId + "-max-new-tiles-for-penguin-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-max-new-tiles-for-penguin-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max Fish Per Tile For Penguin Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_NEW_FISH_PER_TILE_FOR_PENGUIN,
            c,
            simulationTime);
    file = experimentId + "-max-new-fish-per-tile-for-penguin-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-max-new-fish-per-tile-for-penguin-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max New Fish For All Penguins Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_NEW_FISH_FOR_ALL_PENGUINS,
            c,
            simulationTime);
    file = experimentId + "-max-new-fish-for-all-penguins-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-max-new-fish-for-all-penguins-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max New Tiles For All Penguins Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_NEW_TILES_FOR_ALL_PENGUINS,
            c,
            simulationTime);
    file = experimentId + "-max-new-tiles-for-all-penguins-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-max-new-tiles-for-all-penguins-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Max New Fish Per Tile For All Penguins Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MAX_NEW_FISH_PER_TILE_FOR_ALL_PENGUINS,
            c,
            simulationTime);
    file = experimentId + "-max-new-fish-per-tile-for-all-penguins-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "max-new-fish-per-tile-for-all-penguins-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Min New Fish For Opponent Penguins Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MIN_NEW_FISH_FOR_OPPONENT_PENGUINS,
            c,
            simulationTime);
    file = experimentId + "-min-new-fish-for-opponent-penguins-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-min-new-fish-for-opponent-penguins-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Min New Tiles For Opponent Penguins Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MIN_NEW_TILES_FOR_OPPONENT_PENGUINS,
            c,
            simulationTime);
    file = experimentId + "-min-new-tiles-for-opponent-penguins-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-min-new-tiles-for-opponent-penguins-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
    experimentId++;

    p1 =
        new MctsPlayer(
            "MCTS Min New Fish Per TIle For Opponent Penguins Movement",
            4,
            "B",
            PlacementHeuristicType.NONE,
            MovementHeuristicType.MIM_NEW_FISH_PER_TILE_FOR_OPPONENT_PENGUINS,
            c,
            simulationTime);
    file = experimentId + "-min-new-fish-per-tile-for-opponent-penguins-vs-greedy";
    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
    file = experimentId + "-min-new-fish-per-tile-for-opponent-penguins-vs-mcts-lp";
    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
  }
}
