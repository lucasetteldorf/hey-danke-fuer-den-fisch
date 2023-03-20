package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;

public class Mcts {
  private static final long COMPUTATIONAL_BUDGET = 500;
  private static final int WIN_SCORE = 1;
  private static final double TIE_SCORE = 0.5;

  public Move getNextMove(GameBoard board, Player currentPlayer) {
    long start = System.currentTimeMillis();

    Tree tree = new Tree();
    Node root = tree.getRoot();
    root.getState().setBoard(board);
    root.getState().setCurrentPlayer(currentPlayer);
    root.initUnexpandedChildren();

    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
      // 1: Selection
      Node selectedNode = selectNode(root);

      // 2: Expansion
      // TODO working as intended?
      Node expandedNode = selectedNode;
      if (!selectedNode
          .getState()
          .getBoard()
          .isMovementPhaseOver(selectedNode.getState().getBoard().getPlayers())) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulateRandomPlayout(expandedNode);

      // 4: Backpropagation
      // TODO adjust backpropagation of result
      backpropagate(expandedNode, playoutResult);
    }

    Node winnerNode = root.getChildWithMaxVisits();
    tree.setRoot(winnerNode);
    return winnerNode.getState().getPreviousMove();
  }

  private Node selectNode(Node root) {
    Node node = root;
    // TODO working as intended?
    while (!node.getChildren().isEmpty()) {
      if (node.hasUnexpandedChildren()) {
        break;
      } else {
        node = Uct.findBestNode(node);
      }
    }
    return node;
  }

  // TODO working as intended?
  private Node expandNode(Node node) {
    Node expandedNode = node.getRandomUnexpandedChild();
    node.getUnexpandedChildren().remove(expandedNode);
    expandedNode.setParent(node);
    expandedNode.initUnexpandedChildren();
    node.addChild(expandedNode);
    return expandedNode;
  }

  private int simulateRandomPlayout(Node node) {
    Node tmpNode = new Node(node);
    State tmpState = tmpNode.getState();

    // TODO set score to Integer.MIN_VALUE if opponent wins?

    // TODO internal logic/update of states may not work as needed (update of player)
    while (!tmpState.getBoard().isMovementPhaseOver(tmpState.getBoard().getPlayers())) {
      tmpState.playRandomMove();
    }

    // TODO working as intended?
    return tmpState.getBoard().getWinnerIndex(tmpState.getBoard().getPlayers());
  }

  private void backpropagate(Node expandedNode, int playerIndex) {
    Node tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      // TODO may need to be adjusted
      if (playerIndex == -1) {
        tmp.updateScore(TIE_SCORE);
      } else if (tmp.getState()
          .getCurrentPlayer()
          .equals(tmp.getState().getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      tmp = tmp.getParent();
    }
  }
}
