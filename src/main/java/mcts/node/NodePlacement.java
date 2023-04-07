package mcts.node;

import game.logic.GameBoard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utility.RandomUtility;

public class NodePlacement extends Node {
  private final List<int[]> untriedPlacementPositions;
  private int[] previousPlacementPosition;

  public NodePlacement() {
    super();
    this.untriedPlacementPositions = new ArrayList<>();
  }

  public NodePlacement(GameBoard board) {
    super(board);
    this.untriedPlacementPositions = new ArrayList<>();
  }

  // copy constructor
  public NodePlacement(NodePlacement node) {
    super(node);
    this.untriedPlacementPositions = new ArrayList<>();
    for (int[] placement : node.getUntriedPlacementPositions()) {
      this.untriedPlacementPositions.add(new int[] {placement[0], placement[1]});
    }

    if (node.previousPlacementPosition != null) {
      this.previousPlacementPosition =
          Arrays.copyOf(node.previousPlacementPosition, node.previousPlacementPosition.length);
    }
  }

  public int[] getPreviousPlacementPosition() {
    return previousPlacementPosition;
  }

  public void setPreviousPlacementPosition(int[] previousPlacementPosition) {
    this.previousPlacementPosition = previousPlacementPosition;
  }

  public List<int[]> getUntriedPlacementPositions() {
    return untriedPlacementPositions;
  }

  public void initUntriedPlacements() {
    untriedPlacementPositions.addAll(board.getAllLegalPlacementPositions());
  }

  public boolean hasUntriedPlacements() {
    return untriedPlacementPositions.size() > 0;
  }

  public int[] getRandomUntriedPlacement() {
    return untriedPlacementPositions.get(
        RandomUtility.getRandomIndex(untriedPlacementPositions.size()));
  }

  public void expandChildrenPlacement() {
    for (int[] placementPosition : untriedPlacementPositions) {
      NodePlacement node = new NodePlacement(this.board);
      node.getBoard().placePenguin(placementPosition[0], placementPosition[1]);
      node.setParent(this);
      node.initUntriedPlacements();
      node.setPreviousPlacementPosition(placementPosition);
      addChild(node);
    }
    untriedPlacementPositions.clear();
  }
}
