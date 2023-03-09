package mcts;

import game.GameBoard;
import game.Move;

import java.util.List;

public class Mcts {
    private final static long COMPUTATIONAL_BUDGET = 500;
    private final static int WIN_SCORE = 1;

    private final int level;

    public Mcts() {
        // TODO adjust level
        this.level = 3;
    }

    public Move getNextMove(GameBoard board) {
        long start = System.currentTimeMillis();

        Tree tree = new Tree();
        Node root = tree.getRoot();
        // board keeps track of current player internally
        // TODO setting next players and checking if they can move may need to be implemented directly in move method instead of in Game class
        root.getState().setBoard(board);
//        root.setPlayerWins(new int[board.getPlayers().length]);

        while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
            // 1: Selection
            Node selectedNode = selectNode(root);

            // 2: Expansion
            // TODO add method to check if game has ended in a certain state (moves remaining)
            if (true) {
                expandNode(selectedNode);
            }

            // 3: Simulation
            Node expandedNode = selectedNode;
            if (expandedNode.getChildren().size() > 0) {
                expandedNode = selectedNode.getRandomChild();
            }
            int playoutResult = simulateRandomPlayout(expandedNode);

            // 4: Backpropagation
            // TODO adjust backpropagation of result
            backPropagation(expandedNode, playoutResult);
        }

        Node winnerNode = root.getChildWithMaxVisits();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getPreviousMove();
    }

    private Node selectNode(Node root) {
        Node node = root;
        // TODO an Variante 2 der Expansion aus VL anpassen, so werden nur Blätter gewählt
        while (!node.getChildren().isEmpty()) {
            node = Uct.findBestNode(node);
        }

        return node;
    }

    // TODO Variante 2 der Expansion aus der VL
    private void expandNode(Node node) {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        for (State state : possibleStates) {
            Node newNode = new Node(state);
            newNode.setParent(node);
            // TODO may need to set next player on the new node
            node.getChildren().add(newNode);
        }
    }

    private int simulateRandomPlayout(Node node) {
        Node tmpNode = new Node(node);
        State tmpState = tmpNode.getState();

        // TODO internal logic/update of states may not work as needed
        while (tmpState.getBoard().isMovementPhaseOver()) {
            tmpState.randomPlay();
        }

        // TODO working as intended?
        return tmpState.getBoard().getWinnerIndex();
    }

    private void backPropagation(Node expandedNode, int playerIndex) {
        Node tmp = expandedNode;
        while (tmp != null) {
            // TODO may need to be adjusted
            if (tmp.getState().getCurrentPlayer().getIndex() == playerIndex) {
                tmp.increasePlayerWins(playerIndex, WIN_SCORE);
            }
            tmp = tmp.getParent();
        }
    }
}
