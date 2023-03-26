package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;

public class MctsMovement {
  private static final long COMPUTATIONAL_BUDGET = 1000;
  private static final int WIN_SCORE = 1;
  private Player currentPlayer;

  public Move getNextMove(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();
    int numberOfSimulations = 0;

    // TODO move start back to here?

    Node root = new Node();
    root.getState().setBoard(board);
    root.initUntriedMoves();
    long start = System.currentTimeMillis();
    initTree(root);

    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
      // 1: Selection
      Node selectedNode = selectNode(root);

      // 2: Expansion
      Node expandedNode = selectedNode;
      if (!selectedNode.getState().isTerminalState()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulateRandomPlayout(expandedNode);
      numberOfSimulations++;

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    System.out.println(numberOfSimulations + " simulations (movement)");
    Node bestNode = root.getChildWithMaxVisits();
    return bestNode.getState().getPreviousMove();
  }

  private void initTree(Node root) {
    root.expandChildrenMovement();
    for (Node child : root.getChildren()) {
      int playoutResult = simulateRandomPlayout(child);
      backpropagate(child, playoutResult);
    }
  }

  private Node selectNode(Node root) {
    Node node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedMoves()) {
        break;
      } else {
        node = Uct.findBestNode(node);
      }
    }
    return node;
  }

  private Node expandNode(Node node) {
    Move randomUntriedMove = node.getRandomUntriedMove();
    node.getUntriedMoves().remove(randomUntriedMove);
    State newState = new State(node.getState());
    newState.setPreviousMove(randomUntriedMove);
    newState.getBoard().movePenguin(randomUntriedMove);
    Node expandedNode = new Node(newState);
    expandedNode.setParent(node);
    expandedNode.initUntriedMoves();
    node.addChild(expandedNode);
    return expandedNode;
  }

  private int simulateRandomPlayout(Node node) {
    Node tmpNode = new Node(node);
    State tmpState = tmpNode.getState();

    while (!tmpState.isTerminalState()) {
      tmpState.playRandomMove();
    }

    return tmpState.getBoard().getWinnerIndex();
  }

  private void backpropagate(Node expandedNode, int playerIndex) {
    Node tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (playerIndex != -1
          && currentPlayer.equals(tmp.getState().getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      tmp = tmp.getParent();
    }
  }
}
