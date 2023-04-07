package game.players;

import game.logic.GameBoard;
import game.logic.Move;
import mcts.algorithm.MctsMovement;
import mcts.algorithm.MctsPlacement;
import mcts.heavyplayout.MovementHeuristicType;
import mcts.heavyplayout.PlacementHeuristicType;

public class MctsPlayer extends Player {
  private final MctsPlacement mctsPlacement;
  private final MctsMovement mctsMovement;

  public MctsPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    this.mctsPlacement = new MctsPlacement();
    this.mctsMovement = new MctsMovement();
  }

  public MctsPlayer(
      String name, int penguinCount, String penguinColor, double c, int computationalBudget) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    this.mctsPlacement = new MctsPlacement(c, computationalBudget, PlacementHeuristicType.NONE);
    this.mctsMovement = new MctsMovement(c, computationalBudget, MovementHeuristicType.NONE);
  }

  public MctsPlayer(
      String name,
      int penguinCount,
      String penguinColor,
      MctsPlacement mctsPlacement,
      MctsMovement mctsMovement) {
    super(PlayerType.MCTS, name, penguinCount, penguinColor);
    this.mctsPlacement = mctsPlacement;
    this.mctsMovement = mctsMovement;
  }

  public int[] getBestPlacementPosition(GameBoard board) {
    return mctsPlacement.getNextPlacementPosition(board);
  }

  public Move getBestMove(GameBoard board) {
    return mctsMovement.getNextMove(board);
  }

  public double getAveragePlacementSimulations() {
    return mctsPlacement.getAverageSimulations();
  }

  public double getAverageMovementSimulations() {
    return mctsMovement.getAverageSimulations();
  }

  public double getAverageTotalSimulations() {
    return (double)
            (mctsPlacement.getTotalNumberOfSimulations()
                + mctsMovement.getTotalNumberOfSimulations())
        / (mctsPlacement.getCallCount() + mctsMovement.getCallCount());
  }

  public int getPlacementSimulationTime() {
    return mctsPlacement.getComputationalBudget();
  }

  public int getMovementSimulationTime() {
    return mctsMovement.getComputationalBudget();
  }

  public double getPlacementC() {
    return mctsPlacement.getC();
  }

  public double getMovementC() {
    return mctsMovement.getC();
  }
}
