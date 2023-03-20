package game.players;

import game.GameBoard;
import game.Move;
import mcts.Mcts;
import utility.RandomNumbers;

public class MctsPlayer extends Player {
  private final Mcts mcts;

  public MctsPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    mcts = new Mcts();
  }

  public int[] getBestPlacementPosition(GameBoard board) {
    return board
        .getAllLegalPlacementPositions()
        .get(RandomNumbers.getRandomIndex(board.getAllLegalPlacementPositions().size()));
  }

  public Move getBestMove(GameBoard board) {
    return mcts.getNextMove(board, this);
  }
}
