package mcts;

import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node {
    private Node parent;
    private List<Node> children;
    private State state;
    private int visits;
    private double score;

    public Node() {
        this.state = new State();
        this.children = new ArrayList<>();
    }

    public Node(Node node) {
        if (node.parent != null) {
            this.parent = node.parent;
        }
        this.children = new ArrayList<>();
        for (Node child : node.getChildren()) {
            this.children.add(child);
        }
        this.state = new State(node.state);
        this.visits = node.visits;
        this.score = node.score;
    }

    public Node(State state) {
        this.state = state;
        this.children = new ArrayList<>();
        // TODO set visits and score?
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getVisits() {
        return visits;
    }

    public void updateVisits() {
        visits++;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public double getScore() {
        return score;
    }

    public void updateScore(double score) {
        this.score += score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public Node getRandomChild() {
        return children.get(RandomNumbers.getRandomIndex(this.children.size()));
    }

    public Node getChildWithMaxVisits() {
        return Collections.max(children, Comparator.comparing(child -> child.getVisits()));
    }
}
