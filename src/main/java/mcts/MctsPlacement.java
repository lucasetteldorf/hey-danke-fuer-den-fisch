package mcts;

import game.GameBoard;
import game.players.Player;

public class MctsPlacement {
  private static final int WIN_SCORE = 1;
  private final int computationalBudget;
  private final double c;
  private Player currentPlayer;
  private int callCount;
  private int totalNumberOfSimulations;
  private int numberOfSimulations;

  public MctsPlacement() {
    this.computationalBudget = 1000;
    this.c = Math.sqrt(2);
  }

  public MctsPlacement(double c, int computationalBudget) {
    this.c = c;
    this.computationalBudget = computationalBudget;
  }

  public int[] getNextPlacementPosition(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

//        numberOfSimulations = 0;

    NodePlacement root = new NodePlacement();
    root.setBoard(board);
    root.initUntriedPlacements();
    long start = System.currentTimeMillis();
    initTree(root);

    while ((System.currentTimeMillis() - start) < computationalBudget) {
      // 1: Selection
      NodePlacement selectedNode = selectNode(root);

      // 2: Expansion
      NodePlacement expandedNode = selectedNode;
      if (!selectedNode.getBoard().isPlacementPhaseOver()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulateRandomPlayout(expandedNode);

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    callCount++;
//        System.out.println(callCount + ": " + numberOfSimulations + " simulations (placement)");
    NodePlacement bestNode = (NodePlacement) root.getChildWithMaxVisits();
    return bestNode.getPreviousPlacementPosition();
  }

  private void initTree(NodePlacement root) {
    root.expandChildrenPlacement();
    for (Node child : root.getChildren()) {
      NodePlacement childPlacement = (NodePlacement) child;
      int playoutResult = simulateRandomPlayout(childPlacement);
      backpropagate(childPlacement, playoutResult);
    }
  }

  private NodePlacement selectNode(NodePlacement root) {
    NodePlacement node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedPlacements()) {
        break;
      } else {
        node = (NodePlacement) Uct.findBestNode(node, c);
      }
    }
    return node;
  }

  private NodePlacement expandNode(NodePlacement node) {
    int[] randomUntriedPlacementPosition = node.getRandomUntriedPlacement();
    node.getUntriedPlacementPositions().remove(randomUntriedPlacementPosition);
    NodePlacement expandedNode = new NodePlacement(node);
    expandedNode.setPreviousPlacementPosition(randomUntriedPlacementPosition);
    expandedNode
        .getBoard()
        .placePenguin(randomUntriedPlacementPosition[0], randomUntriedPlacementPosition[1]);
    expandedNode.setParent(node);
    expandedNode.initUntriedPlacements();
    node.addChild(expandedNode);
    return expandedNode;
  }

  private int simulateRandomPlayout(NodePlacement node) {
    NodePlacement tmp = new NodePlacement(node);

    while (!tmp.getBoard().isPlacementPhaseOver()) {
      tmp.playRandomPlacement();
    }

    while (!tmp.getBoard().isMovementPhaseOver()) {
      tmp.playRandomMove();
    }

//        numberOfSimulations++;
    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodePlacement expandedNode, int playerIndex) {
    NodePlacement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (playerIndex != -1 && currentPlayer.equals(tmp.getBoard().getPlayers()[playerIndex])) {
        tmp.updateScore(WIN_SCORE);
      }
      tmp = (NodePlacement) tmp.getParent();
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
