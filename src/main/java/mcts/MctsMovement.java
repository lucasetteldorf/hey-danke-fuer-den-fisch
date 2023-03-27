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

    NodeMovement root = new NodeMovement();
    root.setBoard(board);
    root.initUntriedMoves();
    long start = System.currentTimeMillis();
    initTree(root);

    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
      // 1: Selection
      NodeMovement selectedNode = selectNode(root);

      // 2: Expansion
      NodeMovement expandedNode = selectedNode;
      if (!selectedNode.getBoard().isMovementPhaseOver()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulateRandomPlayout(expandedNode);
      numberOfSimulations++;

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    System.out.println(numberOfSimulations + " simulations (movement)");
    NodeMovement bestNode = (NodeMovement) root.getChildWithMaxVisits();
    return bestNode.getPreviousMove();
  }

  private void initTree(NodeMovement root) {
    root.expandChildrenMovement();
    for (Node child : root.getChildren()) {
      NodeMovement childMovement = (NodeMovement) child;
      int playoutResult = simulateRandomPlayout(childMovement);
      backpropagate(childMovement, playoutResult);
    }
  }

  private NodeMovement selectNode(NodeMovement root) {
    NodeMovement node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedMoves()) {
        break;
      } else {
        node = (NodeMovement) Uct.findBestNode(node);
      }
    }
    return node;
  }

  private NodeMovement expandNode(NodeMovement node) {
    Move randomUntriedMove = node.getRandomUntriedMove();
    node.getUntriedMoves().remove(randomUntriedMove);
    NodeMovement expandedNode = new NodeMovement(node);
    expandedNode.setPreviousMove(randomUntriedMove);
    expandedNode.getBoard().movePenguin(randomUntriedMove);
    expandedNode.setParent(node);
    expandedNode.initUntriedMoves();
    node.addChild(expandedNode);
    return expandedNode;
  }

  private int simulateRandomPlayout(NodeMovement node) {
    NodeMovement tmp = new NodeMovement(node);

    while (!tmp.getBoard().isMovementPhaseOver()) {
      tmp.playRandomMove();
    }

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodeMovement expandedNode, int playerIndex) {
    NodeMovement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (playerIndex != -1 && currentPlayer.equals(tmp.getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      // TODO else setScore to MIN VALUE
      tmp = (NodeMovement) tmp.getParent();
    }
  }
}
