//package mcts;
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
//    private int[] playerWins;
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
//        this.playerWins = new int[this.state.getBoard().getPlayers().length];
//    }
//
//    public Node(State state) {
//        this.state = state;
//        this.children = new ArrayList<>();
//        this.playerWins = new int[this.state.getBoard().getPlayers().length];
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
//    public int[] getPlayerWins() {
//        return this.playerWins;
//    }
//
//    public void setPlayerWins(int[] playerWins) {
//        this.playerWins = playerWins;
//    }
//
//    public int getPlayerWinsByIndex(int index) {
//        return this.playerWins[index];
//    }
//
//    public void increasePlayerWins(int index, int value) {
//        this.playerWins[index] += value;
//    }
//
//    public int getVisits() {
//        int sum = 0;
//        for (int wins : this.playerWins) {
//            sum += wins;
//        }
//        return sum;
//    }
//
//    public Node getRandomChild() {
//        return children.get(RandomNumbers.getRandomIndex(this.children.size()));
//    }
//
//    public Node getChildWithMaxVisits() {
//        return Collections.max(this.children, Comparator.comparing(child -> child.getVisits()));
//    }
//}
