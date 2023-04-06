package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;

public class MctsMovement {
  private static final int WIN_SCORE = 1;
  private final int computationalBudget;
  private final double c;
  private Player currentPlayer;
  private int callCount;
  private int totalNumberOfSimulations;
  protected int numberOfSimulations;

  public MctsMovement() {
    this.computationalBudget = 100;
    this.c = 0.35;
  }

  public MctsMovement(double c, int computationalBudget) {
    this.c = c;
    this.computationalBudget = computationalBudget;
  }

  public Move getNextMove(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

    numberOfSimulations = 0;

    NodeMovement root = new NodeMovement();
    root.setBoard(board);
    root.initUntriedMoves();
    long start = System.currentTimeMillis();
    initTree(root);

    while ((System.currentTimeMillis() - start) < computationalBudget) {
      // 1: Selection
      NodeMovement selectedNode = selectNode(root);

      // 2: Expansion
      NodeMovement expandedNode = selectedNode;
      if (!selectedNode.getBoard().isMovementPhaseOver()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulatePlayout(expandedNode);

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    callCount++;
//    System.out.println(callCount + ": " + numberOfSimulations + " movement simulations (" + currentPlayer.getName() + ")");
    NodeMovement bestNode = (NodeMovement) root.getChildWithMaxVisits();
    return bestNode.getPreviousMove();
  }

  private void initTree(NodeMovement root) {
    root.expandChildrenMovement();
    for (Node child : root.getChildren()) {
      NodeMovement childMovement = (NodeMovement) child;
      int playoutResult = simulatePlayout(childMovement);
      backpropagate(childMovement, playoutResult);
    }
  }

  private NodeMovement selectNode(NodeMovement root) {
    NodeMovement node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedMoves()) {
        break;
      } else {
        node = (NodeMovement) Uct.findBestNode(node, c);
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

  protected int simulatePlayout(NodeMovement node) {
    NodeMovement tmp = new NodeMovement(node);

    while (!tmp.getBoard().isMovementPhaseOver()) {
      tmp.playRandomMove();
    }

    numberOfSimulations++;
    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodeMovement expandedNode, int playerIndex) {
    NodeMovement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (playerIndex != -1 && currentPlayer.equals(tmp.getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      tmp = (NodeMovement) tmp.getParent();
    }
  }

  public int getTotalNumberOfSimulations() {
    return totalNumberOfSimulations;
  }

  public double getAverageSimulations() {
    return (double) totalNumberOfSimulations / callCount;
  }

  public int getCallCount() {
    return callCount;
  }

  public int getComputationalBudget() {
    return computationalBudget;
  }

  public double getC() {
    return c;
  }
}
