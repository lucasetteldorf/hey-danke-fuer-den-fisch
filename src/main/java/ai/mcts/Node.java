//package ai.mcts;
//
//import utility.RandomNumbers;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class Node {
//    private Node parent;
//    private List<Node> children;
//    private State state;
//
//    public Node() {
//        this.state = new State();
//        this.children = new ArrayList<>();
//    }
//
//    public Node(Node node) {
//        if (node.parent != null) {
//            this.parent = node.parent;
//        }
//        this.children = new ArrayList<>();
//        for (Node child : node.getChildren()) {
//            this.children.add(child);
//        }
//        this.state = new State(node.state);
//    }
//
//    public Node(State state) {
//        this.state = state;
//        this.children = new ArrayList<>();
//    }
//
//    public Node getParent() {
//        return parent;
//    }
//
//    public void setParent(Node parent) {
//        this.parent = parent;
//    }
//
//    public List<Node> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<Node> children) {
//        this.children = children;
//    }
//
//    public State getState() {
//        return state;
//    }
//
//    public void setState(State state) {
//        this.state = state;
//    }
//
//    public Node getRandomChild() {
//        int possibleMoveCount = this.children.size();
//        return children.get(RandomNumbers.getRandomIndex(possibleMoveCount));
//    }
//
//    // TODO review
//    public Node getChildWithMaxVisits() {
//        return Collections.max(this.children, Comparator.comparing(child -> {
//            return child.getState().getVisitCount();
//        }));
//    }
//}
