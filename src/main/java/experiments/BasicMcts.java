package experiments;

import experiments.utility.OutputWriter;
import experiments.utility.PlayerUtility;
import game.Game;
import game.players.MctsPlayer;
import game.players.Player;
import game.players.RandomPlayer;

public class BasicMcts {
  private static final int NUMBER_OF_GAMES = 1;

  public static void main(String[] args){
    mctsVsRandom();
  }

  public static void mctsVsRandom() {
    PlayerUtility playerUtility = new PlayerUtility(NUMBER_OF_GAMES, 2);

    Game game;
    Player[] players = new Player[2];
    for (int i = 0; i < NUMBER_OF_GAMES; i++) {
      MctsPlayer p1 = new MctsPlayer("MCTS", 4, "B");
      RandomPlayer p2 = new RandomPlayer("Random", 4, "R");
      players = new Player[] {p1, p2};
      game = new Game(players, false);
      game.start();

      for (int j = 0; j < players.length; j++) {
        playerUtility.updatePlayerFishCount(j, players[j].getCollectedFishCount());
        playerUtility.updatePlayerMoveCount(j, players[j].getMoveCount());
      }
      playerUtility.updatePlayerWinCount(game.getBoard().getWinnerIndex());
    }

    OutputWriter outputWriter =
        new OutputWriter(
            "/Users/Lucas/thesis-data/basic-mcts/mcts-vs-random-stats-" + NUMBER_OF_GAMES + ".txt");
    outputWriter.writeStatistics(playerUtility, players);
    }
}
