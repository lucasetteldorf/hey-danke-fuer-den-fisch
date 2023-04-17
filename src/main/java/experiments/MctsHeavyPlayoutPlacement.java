//package experiments;
//
//import experiments.utility.ExperimentSetup;
//import game.players.GreedyPlayer;
//import game.players.MctsPlayer;
//import game.players.Player;
//import mcts.heavyplayout.MovementHeuristicType;
//import mcts.heavyplayout.PlacementHeuristicType;
//import utility.Resources;
//
//public class MctsHeavyPlayoutPlacement {
//  public static void startExperiments(int numberOfGames, double c, int simulationTime) {
//    String path = Resources.ROOT_DIR + "mcts-heavy-playout-placement/";
//    String file;
//    Player p1;
//    Player p2 = new GreedyPlayer("Greedy", 4, "R");
//    Player p3 =
//        new MctsPlayer(
//            "MCTS LP Baseline (2)",
//            4,
//            "R",
//            PlacementHeuristicType.NONE,
//            MovementHeuristicType.NONE,
//            c,
//            simulationTime);
//
//    int experimentId = 0;
//
//    //    Player p4 =
//    //        new MctsPlayer(
//    //            "MCTS LP Baseline (1)",
//    //            4,
//    //            "B",
//    //            PlacementHeuristicType.NONE,
//    //            MovementHeuristicType.NONE,
//    //            c,
//    //            simulationTime);
//    //    file = experimentId + "-mcts-lp-vs-mcts-lp-baseline";
//    //    ExperimentSetup.playGames(new Player[] {p4, p3}, numberOfGames, path + file);
//    //    file = experimentId + "-mcts-lp-vs-greedy-baseline";
//    //    ExperimentSetup.playGames(new Player[] {p4, p2}, numberOfGames, path + file);
//    experimentId++;
//
//    //    p1 =
//    //        new MctsPlayer(
//    //            "MCTS Max Fish For Penguin Placement",
//    //            4,
//    //            "B",
//    //            PlacementHeuristicType.MAX_FISH_FOR_PENGUIN,
//    //            MovementHeuristicType.NONE,
//    //            c,
//    //            simulationTime);
//    //    file = experimentId + "-max-fish-for-penguin-vs-greedy";
//    //    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    //    file = experimentId + "-max-fish-for-penguin-vs-mcts-lp";
//    //    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    //    p1 =
//    //        new MctsPlayer(
//    //            "MCTS Max Tiles For Penguin Placement",
//    //            4,
//    //            "B",
//    //            PlacementHeuristicType.MAX_TILES_FOR_PENGUIN,
//    //            MovementHeuristicType.NONE,
//    //            c,
//    //            simulationTime);
//    //    file = experimentId + "-max-tiles-for-penguin-vs-greedy";
//    //    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    //    file = experimentId + "-max-tiles-for-penguin-vs-mcts-lp";
//    //    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    //    p1 =
//    //        new MctsPlayer(
//    //            "MCTS Max Fish Per Tile For Penguin Placement",
//    //            4,
//    //            "B",
//    //            PlacementHeuristicType.MAX_FISH_PER_TILE_FOR_PENGUIN,
//    //            MovementHeuristicType.NONE,
//    //            c,
//    //            simulationTime);
//    //    file = experimentId + "-max-fish-per-tile-for-penguin-vs-greedy";
//    //    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    //    file = experimentId + "-max-fish-per-tile-for-penguin-vs-mcts-lp";
//    //    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    //    p1 =
//    //        new MctsPlayer(
//    //            "MCTS Max Fish For All Penguins Placement",
//    //            4,
//    //            "B",
//    //            PlacementHeuristicType.MAX_FISH_FOR_ALL_PENGUINS,
//    //            MovementHeuristicType.NONE,
//    //            c,
//    //            simulationTime);
//    //    file = experimentId + "-max-fish-for-all-penguins-vs-greedy";
//    //    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    //    file = experimentId + "-max-fish-for-all-penguins-vs-mcts-lp";
//    //    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    //    p1 =
//    //        new MctsPlayer(
//    //            "MCTS Max Tiles For All Penguins Placement",
//    //            4,
//    //            "B",
//    //            PlacementHeuristicType.MAX_TILES_FOR_ALL_PENGUINS,
//    //            MovementHeuristicType.NONE,
//    //            c,
//    //            simulationTime);
//    //    file = experimentId + "-max-tiles-for-all-penguins-vs-greedy";
//    //    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    //    file = experimentId + "-max-tiles-for-all-penguins-vs-mcts-lp";
//    //    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    p1 =
//        new MctsPlayer(
//            "MCTS Max Fish Per Tile For All Penguins Placement",
//            4,
//            "B",
//            PlacementHeuristicType.MAX_FISH_PER_TILE_FOR_ALL_PENGUINS,
//            MovementHeuristicType.NONE,
//            c,
//            simulationTime);
//    file = experimentId + "-max-fish-per-tile-for-all-penguins-vs-greedy";
//    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    file = experimentId + "-max-fish-per-tile-for-all-penguins-vs-mcts-lp";
//    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    p1 =
//        new MctsPlayer(
//            "MCTS Min Fish For Opponent Penguins Placement",
//            4,
//            "B",
//            PlacementHeuristicType.MIN_FISH_FOR_OPPONENT_PENGUINS,
//            MovementHeuristicType.NONE,
//            c,
//            simulationTime);
//    file = experimentId + "-min-fish-for-opponent-penguins-vs-greedy";
//    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    file = experimentId + "min-fish-for-opponent-penguins-vs-mcts-lp";
//    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    p1 =
//        new MctsPlayer(
//            "MCTS Min Tiles For Opponent Penguins Placement",
//            4,
//            "B",
//            PlacementHeuristicType.MIN_TILES_FOR_OPPONENT_PENGUINS,
//            MovementHeuristicType.NONE,
//            c,
//            simulationTime);
//    file = experimentId + "-min-tiles-for-opponent-penguins-vs-greedy";
//    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    file = experimentId + "-min-tiles-for-opponent-penguins-vs-mcts-lp";
//    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//    experimentId++;
//
//    p1 =
//        new MctsPlayer(
//            "MCTS Min Fish Per Tile For Opponent Penguins Placement",
//            4,
//            "B",
//            PlacementHeuristicType.MIN_FISH_PER_TILE_FOR_OPPONENT_PENGUINS,
//            MovementHeuristicType.NONE,
//            c,
//            simulationTime);
//    file = experimentId + "-min-fish-per-tile-for-opponent-penguins-vs-greedy";
//    ExperimentSetup.playGames(new Player[] {p1, p2}, numberOfGames, path + file);
//    file = experimentId + "-min-fish-per-tile-for-opponent-penguins-vs-mcts-lp";
//    ExperimentSetup.playGames(new Player[] {p1, p3}, numberOfGames, path + file);
//  }
//}
