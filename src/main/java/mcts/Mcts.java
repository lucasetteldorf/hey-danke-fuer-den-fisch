package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;

public class Mcts {
  private static final long COMPUTATIONAL_BUDGET = 500;
  private static final int WIN_SCORE = 1;
  private static final double TIE_SCORE = 0.5;

  private final int level;
  private Player[] players;

  public Mcts() {
    // TODO adjust level/initialize tree with some levels?!
    this.level = 3;
  }

  public Player[] getPlayers() {
    return players;
  }

  public void setPlayers(Player[] players) {
    this.players = players;
  }

  public Move getNextMove(GameBoard board, Player currentPlayer) {
    long start = System.currentTimeMillis();

    Tree tree = new Tree();
    Node root = tree.getRoot();
    root.getState().setBoard(board);
    // TODO working as intended?
    root.getState().setCurrentPlayer(currentPlayer);
    root.initUnexpandedChildren(root.getState().getAllPossibleStates());

    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
      // 1: Selection
      Node selectedNode = selectNode(root);

      // 2: Expansion
      // TODO working as intended?
      Node expandedNode = selectedNode;
      if (!selectedNode.getState().getBoard().isMovementPhaseOver(players)) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
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
    Node expandedNode;
    if (node.hasUnexpandedChildren()) {
      expandedNode = node.getRandomUnexpandedChild();
      node.getUnexpandedChildren().remove(expandedNode);
    } else {
      expandedNode = new Node(node.getState().getRandomPossibleState());
    }
    expandedNode.setParent(node);
    node.addChild(expandedNode);
    return expandedNode;
  }

  private int simulateRandomPlayout(Node node) {
    Node tmpNode = new Node(node);
    State tmpState = tmpNode.getState();

    // TODO set score to Integer.MIN_VALUE if opponent wins?

    // TODO internal logic/update of states may not work as needed (update of palayer)
    while (!tmpState.getBoard().isMovementPhaseOver(players)) {
      tmpState.playRandomMove();
    }

    // TODO working as intended?
    return tmpState.getBoard().getWinnerIndex(players);
  }

  private void backPropagation(Node expandedNode, int playerIndex) {
    Node tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      // TODO may need to be adjusted
      if (playerIndex == -1) {
        tmp.updateScore(TIE_SCORE);
      } else if (tmp.getState().getCurrentPlayer().equals(players[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      tmp = tmp.getParent();
    }
  }
}
