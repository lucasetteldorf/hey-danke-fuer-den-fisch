package mcts.heavyplayout;

import mcts.MctsPlacement;
import mcts.NodePlacement;

public class MctsPlacementMaxFish extends MctsPlacement {
  public MctsPlacementMaxFish(double c, int computationalBudget) {
    super(c, computationalBudget);
  }

  // heavy playout
  @Override
  protected int simulatePlayout(NodePlacement node) {
    NodePlacement tmp = new NodePlacement(node);

    while (!tmp.getBoard().isPlacementPhaseOver()) {
      PlacementHeuristics.playMaxFish(tmp.getBoard());
    }

    while (!tmp.getBoard().isMovementPhaseOver()) {
      // TODO ??
      tmp.playRandomMove();
    }

    numberOfSimulations++;
    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }
}
