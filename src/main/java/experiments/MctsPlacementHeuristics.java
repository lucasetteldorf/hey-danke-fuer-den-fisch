package experiments;

import experiments.utility.ExperimentSetup;
import experiments.utility.GameStatistics;
import game.Game;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.MctsMovement;
import mcts.heavyplayout.MctsPlacementMaxDirectFish;
import mcts.heavyplayout.MctsPlacementMaxFish;
import mcts.heavyplayout.MctsPlacementMaxOwnPossibilities;
import mcts.heavyplayout.MctsPlacementMinOpponentPossibilities;

public class MctsPlacementHeuristics {
  public static void main(String[] args) {
    int numberOfGames = 1000;
    double c = 0.35;
    int simulationTime = 20;
    String path = "/Users/Lucas/thesis-data/mcts-heavy-playout-placement/";
    String file;
    GameStatistics gameStatistics;

    // test max fish placement
    Player p1;
    Player p2;
    Player[] players = null;
    Game game;

    file = "max-fish-heuristic.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p1 =
          new MctsPlayer(
              "MCTS Max Fish HP",
              4,
              "B",
              new MctsPlacementMaxFish(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p2 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "max-fish-heuristic-reverse.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p2 =
          new MctsPlayer(
              "MCTS Max Fish HP",
              4,
              "B",
              new MctsPlacementMaxFish(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p1 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "max-direct-fish-heuristic.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p1 =
          new MctsPlayer(
              "MCTS Max Direct Fish HP",
              4,
              "B",
              new MctsPlacementMaxDirectFish(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p2 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "max-direct-fish-heuristic-reverse.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p2 =
          new MctsPlayer(
              "MCTS Max Direct Fish HP",
              4,
              "B",
              new MctsPlacementMaxDirectFish(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p1 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "max-own-possibilities.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p1 =
          new MctsPlayer(
              "MCTS Max Own Possibilities HP",
              4,
              "B",
              new MctsPlacementMaxOwnPossibilities(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p2 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "max-own-possibilities-reverse.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p2 =
          new MctsPlayer(
              "MCTS Max Own Possibilities HP",
              4,
              "B",
              new MctsPlacementMaxOwnPossibilities(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p1 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "min-opponent-possibilities.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p1 =
          new MctsPlayer(
              "MCTS Min Opponent Possibilities HP",
              4,
              "B",
              new MctsPlacementMinOpponentPossibilities(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p2 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");

    file = "min-opponent-possibilities-reverse.txt";
    gameStatistics = new GameStatistics(numberOfGames, 2, true);
    for (int i = 0; i < numberOfGames; i++) {
      p2 =
          new MctsPlayer(
              "MCTS Min Opponent Possibilities HP",
              4,
              "B",
              new MctsPlacementMinOpponentPossibilities(c, simulationTime),
              new MctsMovement(c, simulationTime));
      p1 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
      players = new Player[] {p1, p2};
      game = new Game(players, false, false);
      game.start();
      ExperimentSetup.updateStatistics(game, gameStatistics);
      System.out.println((i + 1));
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
    System.out.println(file + " done");
  }
}
