package mcts.algorithm;

import game.logic.GameBoard;
import game.logic.Move;
import game.players.Player;
import mcts.node.NodeMovement;

public class MctsMovement {
  private static final int WIN_SCORE = 1;
  private static final int TIE_SCORE = 0;
  private static final int LOSE_SCORE = -1;
  private final int computationalBudget;
  private final double c;
  HeuristicType type;

  private Player currentPlayer;
  private int callCount;
  private int totalNumberOfSimulations;

  public MctsMovement() {
    this.computationalBudget = 100;
    this.c = 0.5;
    this.type = HeuristicType.NONE;
  }

  public MctsMovement(double c, int computationalBudget, HeuristicType type) {
    this.c = c;
    this.computationalBudget = computationalBudget;
    this.type = type;
  }

  public Move getNextMove(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

    NodeMovement root = new NodeMovement();
    root.setBoard(board);
    root.initUntriedMoves();
    // init starting tree
    root.expandChildrenMovement();
    long start = System.currentTimeMillis();
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
    NodeMovement bestNode = (NodeMovement) root.getChildWithMaxVisits();
    return bestNode.getPreviousMove();
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
      switch (type) {
        case MNTFC -> tmp.moveMaxNewTileFishCount();
        case MORTFT -> tmp.moveMaxOwnReachableThreeFishTiles();
        case MERTFT -> tmp.moveMinEnemyReachableThreeFishTiles();
        case MORFC -> tmp.moveMaxOwnReachableFishCount();
        case MERFC -> tmp.moveMinEnemyReachableFishCount();
        case MORT -> tmp.moveMaxOwnReachableTiles();
        case MERT -> tmp.moveMinEnemyReachableTiles();
        default -> tmp.moveRandom();
      }
    }

    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodeMovement expandedNode, int winnerIndex) {
    NodeMovement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (winnerIndex == -1) {
        tmp.updateScore(TIE_SCORE);
      } else if (currentPlayer.equals(tmp.getBoard().getPlayers()[winnerIndex])) {
        tmp.updateScore(WIN_SCORE);
      } else if (!currentPlayer.equals(tmp.getBoard().getPlayers()[winnerIndex])) {
        tmp.updateScore(LOSE_SCORE);
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

  public void resetStats() {
    callCount = 0;
    totalNumberOfSimulations = 0;
  }
}
