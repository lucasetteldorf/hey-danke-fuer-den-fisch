package game.players;

import game.GameBoard;
import game.Move;
import mcts.MctsMovement;
import mcts.MctsPlacement;import utility.RandomNumbers;

public class MctsPlayer extends Player {
  private final MctsPlacement mctsPlacement;
  private final MctsMovement mctsMovement;

  public MctsPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    mctsPlacement = new MctsPlacement();
    mctsMovement = new MctsMovement();
  }

  public int[] getBestPlacementPosition(GameBoard board) {
    return mctsPlacement.getNextPlacement(board);
  }

  public Move getBestMove(GameBoard board) {
    return mctsMovement.getNextMove(board);
  }
}
