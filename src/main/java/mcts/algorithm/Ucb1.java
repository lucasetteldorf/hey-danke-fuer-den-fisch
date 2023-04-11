package mcts.algorithm;

import mcts.node.Node;

public class Ucb1 {
  public static double calculateUcb1Value(
      double c, double nodeScore, int nodeVisits, int parentVisits) {
    if (nodeVisits == 0) {
      return Integer.MAX_VALUE;
    }

    return (nodeScore / (double) nodeVisits)
        + 2 * c * Math.sqrt(2 * Math.log(parentVisits) / (double) nodeVisits);
  }

  public static Node findBestNode(Node node, double c) {
    int parentVisits = node.getVisits();
    double max = Integer.MIN_VALUE;
    Node bestNode = null;
    for (Node child : node.getChildren()) {
      double uctValue = calculateUcb1Value(c, child.getScore(), child.getVisits(), parentVisits);
      if (uctValue > max) {
        max = uctValue;
        bestNode = child;
      }
    }
    return bestNode;
  }
}
