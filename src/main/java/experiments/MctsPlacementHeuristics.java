package experiments;

import experiments.utility.ExperimentSetup;
import experiments.utility.GameStatistics;
import game.Game;
import game.players.MctsPlayer;
import game.players.Player;
import mcts.MctsMovement;
import mcts.heavyplayout.MctsPlacementMaxDirectFish;
import mcts.heavyplayout.MctsPlacementMaxFish;

public class MctsPlacementHeuristics {
  public static void main(String[] args) {
    int numberOfGames = 1;
    double c = 0.35;
    int simulationTime = 200;
    String path = "/Users/Lucas/thesis-data/mcts-heavy-playout-placement/";
    String file = "";
    GameStatistics gameStatistics;

    // test max fish placement
    Player p1;
    Player p2;
    Player[] players = null;
    Game game;

//    file = "max-fish-heuristic.txt";
//    gameStatistics = new GameStatistics(numberOfGames, 2, true);
//    for (int i = 0; i < numberOfGames; i++) {
//      p1 =
//          new MctsPlayer(
//              "MCTS Max Fish HP",
//              4,
//              "B",
//              new MctsPlacementMaxFish(c, simulationTime),
//              new MctsMovement(c, simulationTime));
//      p2 = new MctsPlayer("MCTS LP", 4, "R", c, simulationTime);
//      players = new Player[] {p1, p2};
//      game = new Game(players, false, false);
//      game.start();
//      ExperimentSetup.updateStatistics(game, gameStatistics);
//    }
//    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);

    file = "max-direct-fish-heuristic-txt";
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
    }
    ExperimentSetup.writeStatistics(path + file, players, gameStatistics);
  }
}
