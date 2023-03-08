package mcts;

public class Uct {
    private static final double C = Math.sqrt(2);

    public static double calculateUctValue(int playerWins, int opponentsWins, int parentSimulations, int nodeSimulations) {
        return ((double) playerWins / (playerWins + opponentsWins)) + C * Math.sqrt(Math.log(parentSimulations) / nodeSimulations);
    }

    public static Node findBestNode(Node node) {
        int parentSimulations = node.getVisits();

        double max = 0;
        Node bestNode = null;
        // TODO probably wrong calculations
        for (Node child : node.getChildren()) {
            int nodeSimulations = child.getVisits();
            int playerWins = child.getPlayerWinsByIndex(node.getState().getCurrentPlayer().getIndex());
            int opponentsWins = -playerWins;
            for (int wins : child.getPlayerWins()) {
                opponentsWins += wins;
            }

            double uctValue = calculateUctValue(playerWins, opponentsWins, parentSimulations, nodeSimulations);
            if (uctValue > max) {
                max = uctValue;
                bestNode = node;
            }
        }

        return bestNode;
    }
}
