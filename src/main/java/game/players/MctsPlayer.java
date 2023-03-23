package game.players;

import game.GameBoard;
import game.Move;
import mcts.MctsMovement;
import utility.RandomNumbers;

public class MctsPlayer extends Player {
  private final MctsMovement mctsMovement;

  public MctsPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    mctsMovement = new MctsMovement();
  }

  public int[] getBestPlacementPosition(GameBoard board) {
    return board
        .getAllLegalPlacementPositions()
        .get(RandomNumbers.getRandomIndex(board.getAllLegalPlacementPositions().size()));
  }

  public Move getBestMove(GameBoard board) {
    return mctsMovement.getNextMove(board);
  }
}
