package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;

import java.util.List;

public class Mcts {
    private final static long COMPUTATIONAL_BUDGET = 500;
    private final static int WIN_SCORE = 1;

    private final int level;
    private final Player[] players;

    public Mcts(Player[] players) {
        // TODO adjust level
        this.level = 3;
        this.players = players;
    }

    public Move getNextMove(GameBoard board) {
        long start = System.currentTimeMillis();

        Tree tree = new Tree();
        Node root = tree.getRoot();
        root.getState().setBoard(board);
        // TODO set player to opponent?

        while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
            // 1: Selection
            Node selectedNode = selectNode(root);

            // 2: Expansion
            // TODO working as intended?
            if (!selectedNode.getState().getBoard().isMovementPhaseOver(players)) {
                expandNode(selectedNode);
            }

            // 3: Simulation
            Node expandedNode = selectedNode;
            if (!expandedNode.getChildren().isEmpty()) {
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

        // TODO set score to Integer.MIN_VALUE if opponent wins

        // TODO internal logic/update of states may not work as needed
        while (!tmpState.getBoard().isMovementPhaseOver(players)) {
            tmpState.randomPlay();
        }

        // TODO working as intended?
        return tmpState.getBoard().getWinnerIndex(players);
    }

    private void backPropagation(Node expandedNode, int playerIndex) {
        Node tmp = expandedNode;
        while (tmp != null) {
            tmp.updateVisits();
            // TODO may need to be adjusted
            if (tmp.getState().getCurrentPlayer().equals(players[playerIndex])) {
                tmp.updateScore(WIN_SCORE);
            }
            tmp = tmp.getParent();
        }
    }
}
