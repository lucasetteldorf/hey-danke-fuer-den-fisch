package mcts;

import mcts.Mcts;

import java.util.Collections;
import java.util.Comparator;

public class Uct {
    private static final double C = Math.sqrt(2);

    public static double calculateUctValue(double playerWins, double opponentsWins, double parentSimulations, double nodeSimulations) {
        return (playerWins / (playerWins + opponentsWins)) + C * Math.sqrt(Math.log(parentSimulations) / nodeSimulations);
    }

    public static Node findBestNode(Node node) {
        double parentSimulations = node.getPlayerAWins() + node.getPlayerBWins();
        return Collections.max(node.getChildren(), Comparator.comparing(child -> calculateUctValue());
    }
}
