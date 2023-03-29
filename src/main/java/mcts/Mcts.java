package mcts;

import game.GameBoard;
import game.Move;

public class Mcts {
  private final MctsPlacement mctsPlacement;
  private final MctsMovement mctsMovement;

  public Mcts() {
    this.mctsPlacement = new MctsPlacement();
    this.mctsMovement = new MctsMovement();
  }

  public int[] computeBestPlacementPosition(GameBoard board) {
    return mctsPlacement.getNextPlacementPosition(board);
  }

  public Move computeBestMove(GameBoard board) {
    return mctsMovement.getNextMove(board);
  }

  public MctsPlacement getMctsPlacement() {
    return mctsPlacement;
  }

  public MctsMovement getMctsMovement() {
    return mctsMovement;
  }
}
