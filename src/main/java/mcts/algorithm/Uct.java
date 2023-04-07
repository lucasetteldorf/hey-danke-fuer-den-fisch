package mcts.algorithm;

import mcts.node.Node;

public class Uct {
  public static double calculateUctValue(
      double c, double nodeScore, int nodeVisits, int parentVisits) {
    if (nodeVisits == 0) {
      return Integer.MAX_VALUE;
    }

    return (nodeScore / (double) nodeVisits)
        + c * Math.sqrt(Math.log(parentVisits) / (double) nodeVisits);
  }

  public static Node findBestNode(Node node, double c) {
    int parentVisits = node.getVisits();
    double max = Integer.MIN_VALUE;
    Node bestNode = null;
    for (Node child : node.getChildren()) {
      double uctValue = calculateUctValue(c, child.getScore(), child.getVisits(), parentVisits);
      if (uctValue > max) {
        max = uctValue;
        bestNode = child;
      }
    }
    return bestNode;
  }
}
