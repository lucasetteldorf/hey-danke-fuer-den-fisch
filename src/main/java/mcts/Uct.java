//package ai.mcts;
//
//import java.util.Collections;
//import java.util.Comparator;
//
//public class Uct {
//    public static double calculateUctValue(int parentVisitCount, int nodeVisitCount, double nodeScore) {
////        if (nodeVisitCount == 0) {
////            return Integer.MAX_VALUE;
////        }
//
//        return (nodeScore / (double) nodeVisitCount) + Math.sqrt(2) * Math.sqrt(Math.log(parentVisitCount) / (double) nodeVisitCount);
//    }
//
//    public static Node findBestNode(Node node) {
//        int parentVisitCount = node.getState().getVisitCount();
//        return Collections.max(node.getChildren(), Comparator.comparing(child -> calculateUctValue(parentVisitCount, child.getState().getVisitCount(), child.getState().getScore())));
//    }
//}
