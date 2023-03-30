package mcts;

public class Uct {
  private static final double C = Math.sqrt(2);

  public static double calculateUctValue(double nodeScore, int nodeVisits, int parentVisits) {
    if (nodeVisits == 0) {
      return Integer.MAX_VALUE;
    }

    return (nodeScore / (double) nodeVisits)
        + C * Math.sqrt(Math.log(parentVisits) / (double) nodeVisits);
  }

  public static Node findBestNode(Node node) {
    // TODO problem that first element if always chosen when multiple maximums exist?
    int parentVisits = node.getVisits();
    double max = Integer.MIN_VALUE;
    Node bestNode = null;
    for (Node child : node.getChildren()) {
      double uctValue = calculateUctValue(child.getScore(), child.getVisits(), parentVisits);
      if (uctValue > max) {
        max = uctValue;
        bestNode = child;
      }
    }
    return bestNode;
  }
}
