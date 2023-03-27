package mcts;

import game.GameBoard;
import game.players.Player;

public class MctsPlacement {
  private static final long COMPUTATIONAL_BUDGET = 1000;
  private static final int WIN_SCORE = 1;
  private Player currentPlayer;

  public int[] getNextPlacement(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();
    int numberOfSimulations = 0;

    // TODO move start back to here?

    Node root = new Node();
    root.getState().setBoard(board);
    root.initUntriedPlacements();
    long start = System.currentTimeMillis();
    initTree(root);


    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
      // 1: Selection
      Node selectedNode = selectNode(root);

      // 2: Expansion
      // TODO condition needed for placement?
      Node expandedNode = selectedNode;
      // TODO correct condition?
      if (!selectedNode.getState().getBoard().isPlacementPhaseOver()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulateRandomPlayout(expandedNode);
      numberOfSimulations++;

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    System.out.println(numberOfSimulations + " simulations (placement)");
    Node bestNode = root.getChildWithMaxVisits();
    return bestNode.getState().getPreviousPlacement();
  }

  private void initTree(Node root) {
    root.expandChildrenPlacement();
    for (Node child : root.getChildren()) {
      int playoutResult = simulateRandomPlayout(child);
      backpropagate(child, playoutResult);
    }
  }

  private Node selectNode(Node root) {
    Node node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedPlacements()) {
        break;
      } else {
        node = Uct.findBestNode(node);
      }
    }
    return node;
  }

  private Node expandNode(Node node) {
    int[] randomUntriedPlacement = node.getRandomUntriedPlacement();
    node.getUntriedPlacements().remove(randomUntriedPlacement);
    State newState = new State(node.getState());
    newState.setPreviousPlacement(randomUntriedPlacement);
    newState.getBoard().placePenguin(randomUntriedPlacement[0], randomUntriedPlacement[1]);
    Node expandedNode = new Node(newState);
    expandedNode.setParent(node);
    expandedNode.initUntriedPlacements();
    node.addChild(expandedNode);
    return expandedNode;
  }

  private int simulateRandomPlayout(Node node) {
    Node tmpNode = new Node(node);
    State tmpState = tmpNode.getState();

    while (!tmpState.getBoard().isPlacementPhaseOver()) {
      tmpState.playRandomPlacement();
    }

    while (!tmpState.getBoard().isMovementPhaseOver()) {
      tmpState.playRandomMove();
    }

    return tmpState.getBoard().getWinnerIndex();
  }

  private void backpropagate(Node expandedNode, int playerIndex) {
    Node tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (playerIndex != -1 && currentPlayer.equals(tmp.getState().getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      // TODO else setScore to MIN VALUE
      tmp = tmp.getParent();
    }
  }
}
