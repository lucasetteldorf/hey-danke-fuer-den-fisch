package experiments;

import experiments.utility.ExperimentSetup;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.algorithm.MctsMovement;
import mcts.algorithm.MctsPlacement;
import mcts.heavyplayout.PlacementHeuristicType;

public class MctsPlacementHeuristics {
  public static void main(String[] args) {
    int numberOfGames = 50;
    double c = 0.35;
    int simulationTime = 10;
    String path = "/Users/Lucas/thesis-data/mcts-heavy-playout-placement/";
    String file;
    Player p1;
    Player p2;
    Player[] players;

    file = "baseline-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP 1", 4, "B", c, simulationTime);
    p2 = new MctsPlayer("MCTS LP 2", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-fish-per-tile-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max Fish Per Tile HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_FISH_PER_TILE),
            new MctsMovement(c, simulationTime));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-fish-per-tile-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max Fish Per Tile HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_FISH_PER_TILE),
            new MctsMovement(c, simulationTime));
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-fish-per-neighbor-tile-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max Fish Per Neighbor Tile HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_FISH_PER_NEIGHBOR_TILE),
            new MctsMovement(c, simulationTime));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file =
        "max-fish-per-neighbor-tile-reverse-"
            + numberOfGames
            + "games-"
            + simulationTime
            + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max Fish Per Neighbor Tile HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_FISH_PER_NEIGHBOR_TILE),
            new MctsMovement(c, simulationTime));
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-total-fish-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max Total Fish HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_TOTAL_FISH),
            new MctsMovement(c, simulationTime));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-total-fish-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max Total Fish HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_TOTAL_FISH),
            new MctsMovement(c, simulationTime));
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-total-neighbor-fish-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max Total Neighbor Fish HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_TOTAL_NEIGHBOR_FISH),
            new MctsMovement(c, simulationTime));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file =
        "max-total-neighbor-fish-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max Total Neighbor Fish HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_TOTAL_NEIGHBOR_FISH),
            new MctsMovement(c, simulationTime));
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-own-possibilities-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Max Own Possibilities HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_OWN_POSSIBILITIES),
            new MctsMovement(c, simulationTime));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "max-own-possibilities-reverse-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Max Own Possibilities HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MAX_OWN_POSSIBILITIES),
            new MctsMovement(c, simulationTime));
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file = "min-opponent-possibilities-" + numberOfGames + "games-" + simulationTime + "ms.txt";
    p1 =
        new MctsPlayer(
            "MCTS Min Opponent Possibilities HP",
            4,
            "B",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MIN_OPPONENT_POSSIBILITIES),
            new MctsMovement(c, simulationTime));
    p2 = new MctsPlayer("MCTS LP Baseline", 4, "R", c, simulationTime);
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);

    file =
        "min-opponent-possibilities-reverse-"
            + numberOfGames
            + "games-"
            + simulationTime
            + "ms.txt";
    p1 = new MctsPlayer("MCTS LP Baseline", 4, "B", c, simulationTime);
    p2 =
        new MctsPlayer(
            "MCTS Min Opponent Possibilities HP",
            4,
            "R",
            new MctsPlacement(c, simulationTime, PlacementHeuristicType.MIN_OPPONENT_POSSIBILITIES),
            new MctsMovement(c, simulationTime));
    players = new Player[] {p1, p2};
    ExperimentSetup.playGames(players, true, numberOfGames, path + file);
  }
}
