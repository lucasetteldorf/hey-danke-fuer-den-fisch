package mcts.heavyplayout;

import mcts.MctsPlacement;
import mcts.NodePlacement;

public class MctsPlacementMaxDirectFish extends MctsPlacement {
    public MctsPlacementMaxDirectFish(double c, int computationalBudget) {
        super(c, computationalBudget);
    }

    // heavy playout
    @Override
    protected int simulatePlayout(NodePlacement node) {
        NodePlacement tmp = new NodePlacement(node);

        while (!tmp.getBoard().isPlacementPhaseOver()) {
            PlacementHeuristics.playMaxDirectFish(tmp.getBoard());
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
