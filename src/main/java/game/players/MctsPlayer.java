package game.players;

import game.GameBoard;
import game.Move;
import mcts.Mcts;

public class MctsPlayer extends Player {
  private final Mcts mcts;

  public MctsPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    this.mcts = new Mcts();
  }

  public MctsPlayer(
      String name, int penguinCount, String penguinColor, double c, int computationalBudget) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    this.mcts = new Mcts(c, computationalBudget);
  }

  public int[] getBestPlacementPosition(GameBoard board) {
    return mcts.computeBestPlacementPosition(board);
  }

  public Move getBestMove(GameBoard board) {
    return mcts.computeBestMove(board);
  }

  public double getAveragePlacementSimulations() {
    return mcts.getMctsPlacement().getAverageSimulations();
  }

  public double getAverageMovementSimulations() {
    return mcts.getMctsMovement().getAverageSimulations();
  }

  public double getAverageTotalSimulations() {
    return (double)
            (mcts.getMctsPlacement().getTotalNumberOfSimulations()
                + mcts.getMctsMovement().getTotalNumberOfSimulations())
        / (mcts.getMctsPlacement().getCallCount() + mcts.getMctsMovement().getCallCount());
  }

  public int getPlacementSimulationTime() {
    return mcts.getMctsPlacement().getComputationalBudget();
  }

  public int getMovementSimulationTime() {
    return mcts.getMctsMovement().getComputationalBudget();
  }

  public double getPlacementC() {
    return mcts.getMctsPlacement().getC();
  }

  public double getMovementC() {
    return mcts.getMctsMovement().getC();
  }
}
