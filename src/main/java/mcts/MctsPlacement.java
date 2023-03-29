package mcts;

import game.GameBoard;
import game.players.Player;

public class MctsPlacement {
  private static final int COMPUTATIONAL_BUDGET = 1000;
  private static final int WIN_SCORE = 1;
  private Player currentPlayer;
  private int callCount;
  private int totalNumberOfSimulations;

  public static int getSimulationTime() {
    return COMPUTATIONAL_BUDGET;
  }

  public int[] getNextPlacementPosition(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

    NodePlacement root = new NodePlacement();
    root.setBoard(board);
    root.initUntriedPlacements();
    long start = System.currentTimeMillis();
    initTree(root);

    while ((System.currentTimeMillis() - start) < COMPUTATIONAL_BUDGET) {
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
        node = (NodePlacement) Uct.findBestNode(node);
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
      // TODO else setScore to MIN VALUE
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
}
