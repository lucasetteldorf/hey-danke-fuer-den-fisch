package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;

public class MctsMovement {
  private static final long COMPUTATIONAL_BUDGET = 500;
  private static final int WIN_SCORE = 1;
  private static final double TIE_SCORE = 0.5;
  private Player currentPlayer;

  public Move getNextMove(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

    long start = System.currentTimeMillis();
    int numberOfSimulations = 0;

    Tree tree = new Tree();
    Node root = tree.getRoot();
    root.getState().setBoard(board);
    root.initUntriedMoves();

    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
      // 1: Selection
      Node selectedNode = selectNode(root);

      // 2: Expansion
      // TODO working as intended?
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
    System.out.println(numberOfSimulations + " simulations");
    Node bestNode = root.getChildWithMaxVisits();
    tree.setRoot(bestNode);
    return bestNode.getState().getPreviousMove();
  }

  private Node selectNode(Node root) {
    Node node = root;
    // TODO working as intended?
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedMoves()) {
        break;
      } else {
        node = Uct.findBestNode(node);
      }
    }
    return node;
  }

  // TODO working as intended?
  private Node expandNode(Node node) {
    // TODO working as intended or two cases needed for untried moves and no untried moves?
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

    // TODO internal logic/update of states may not work as needed (update of player)
    while (!tmpState.isTerminalState()) {
      tmpState.playRandomMove();
    }

    // TODO working as intended?
    return tmpState.getBoard().getWinnerIndex();
  }

  private void backpropagate(Node expandedNode, int playerIndex) {
    Node tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      // TODO may need to be adjusted
      if (playerIndex == -1) {
        tmp.updateScore(TIE_SCORE);
      } else if (currentPlayer.equals(tmp.getState().getBoard().getPlayers()[playerIndex])) {
        // TODO are scores properly updated this way???
        tmp.updateScore(WIN_SCORE);
      }
      tmp = tmp.getParent();
    }
  }
}
