package mcts;

import java.util.Collections;
import java.util.Comparator;

public class Uct {
  private static final double C = Math.sqrt(2);

  public static double calculateUctValue(double nodeScore, int nodeVisits, int parentVisits) {
    return (nodeScore / (double) nodeVisits)
        + C * Math.sqrt(Math.log(parentVisits) / (double) nodeVisits);
  }

  public static Node findBestNode(Node node) {
    int parentVisits = node.getVisits();
    return Collections.max(
        node.getChildren(),
        Comparator.comparing(
            child -> calculateUctValue(child.getScore(), child.getVisits(), parentVisits)));
  }
}
