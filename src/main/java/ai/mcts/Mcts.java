//package ai.mcts;
//
//import game.GameBoard;
//
//import java.util.List;
//
//public class Mcts {
//    private final static long COMPUTATIONAL_BUDGET = 1000;
//    private final static int WIN_SCORE = 1;
//
//    public GameBoard getNextMove(GameBoard board, int playerIndex) {
//        long start = System.currentTimeMillis();
//
//        Tree tree = new Tree();
//        Node root = tree.getRoot();
//        root.getState().setBoard(board);
//        root.getState().setPlayerIndex(board.getNextPlayerIndex(playerIndex));
//
//        while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
//            // Selection
//            Node selectedNode = selectNode(root);
//
//            // Expansion
//            // TODO add method to check if game has ended in a certain state (moves remaining)
//            if (true) {
//                expandNode(selectedNode);
//            }
//
//            // Simulation
//            Node expandedNode = selectedNode;
//            if (expandedNode.getChildren().size() > 0) {
//                expandedNode = selectedNode.getRandomChild();
//            }
//            int playoutResult = simulateRandomPlayout(expandedNode);
//
//            // Backpropagation
//            // TODO adjust backpropagation of result
//            backPropagation(expandedNode, playoutResult);
//        }
//
//        Node winnerNode = root.getChildWithMaxVisits();
//        tree.setRoot(winnerNode);
//        return winnerNode.getState().getBoard();
//    }
//
//    private Node selectNode(Node root) {
//        Node node = root;
//        while (!node.getChildren().isEmpty()) {
//            node = Uct.findBestNode(node);
//        }
//
//        return node;
//    }
//
//    private void expandNode(Node node) {
//        List<State> possibleStates = node.getState().getAllPossibleStates();
//        for (State state : possibleStates) {
//            Node newNode = new Node(state);
//            newNode.setParent(node);
//            newNode.getState().setPlayerIndex(node.getState().getOpponentIndex());
//            node.getChildren().add(newNode);
//        }
//    }
//
//    private int simulateRandomPlayout(Node node) {
//        Node tmpNode = new Node(node);
//        State tmpState = tmpNode.getState();
//
//        return 0;
//    }
//
//    private void backPropagation(Node expandedNode, int playerIndex) {
//        Node tmp = expandedNode;
//        while (tmp != null) {
//            tmp.getState().increaseVisitCount();
//            if (tmp.getState().getPlayerIndex() == playerIndex) {
//                tmp.getState().increaseScore(WIN_SCORE);
//            }
//            tmp = tmp.getParent();
//        }
//    }
//}
