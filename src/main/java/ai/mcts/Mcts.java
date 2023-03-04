package ai.mcts;

import game.GameBoard;
import game.Player;

public class Mcts {
    private final static long COMPUTATIONAL_BUDGET = 3000;

    public GameBoard getNextMove(GameBoard board, Player currentPlayer, Player opponentPlayer) {
        long start = System.currentTimeMillis();

        Tree tree = new Tree();
        Node root = tree.getRoot();
        root.getState().setBoard(board);
        // ?
        root.getState().setCurrentPlayer(opponentPlayer);

        while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
            // Selection
            Node selectedNode = selectNode(root);

            // Expansion
            // TODO add method to check if game has ended in a certain state (moves remaining)
            if (true) {
                expandNode(selectedNode);
            }

            // Simulation
            Node expandedNode = selectedNode;
            if (expandedNode.getChildren().size() > 0) {
                expandedNode = selectedNode.getRandomChild();
            }
            int playoutResult = simulateRandomPlayout(expandedNode);

            // Backpropagation
            // TODO adjust backpropagation of result
            backPropagation(expandedNode);
        }

        return null;
    }

    private Node selectNode(Node root) {
        return null;
    }

    private void expandNode(Node node) {

    }

    private int simulateRandomPlayout(Node node) {
        return 0;
    }

    private void backPropagation(Node expandedNode) {

    }
}
