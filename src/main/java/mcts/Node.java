package mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utility.RandomNumbers;

public class Node {
  private Node parent;
  private List<Node> children;
  // TODO maybe use untried moves instead
  private List<Node> unexpandedChildren;
  private State state;
  private int visits;
  // TODO maybe change to wins (int)
  private double score;

  public Node() {
    this.state = new State();
    this.children = new ArrayList<>();
    this.unexpandedChildren = new ArrayList<>();
  }

  // copy constructor
  public Node(Node node) {
    if (node.parent != null) {
      this.parent = node.parent;
    }
    this.children = new ArrayList<>();
    for (Node child : node.getChildren()) {
      this.children.add(child);
    }
    this.unexpandedChildren = new ArrayList<>();
    for (Node unexpandedChild : node.getUnexpandedChildren()) {
      this.unexpandedChildren.add(unexpandedChild);
    }
    this.state = new State(node.state);
    this.visits = node.visits;
    this.score = node.score;
  }

  public Node(State state) {
    this.state = new State(state);
    this.children = new ArrayList<>();
    this.unexpandedChildren = new ArrayList<>();
  }

  public void initUnexpandedChildren() {
    this.unexpandedChildren = new ArrayList<>();
    for (State state : state.getAllPossibleStates()) {
      Node child = new Node(state);
      child.setParent(this);
      this.unexpandedChildren.add(child);
    }
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

  public List<Node> getUnexpandedChildren() {
    return unexpandedChildren;
  }

  public void setUnexpandedChildren(List<Node> unexpandedChildren) {
    this.unexpandedChildren = unexpandedChildren;
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

  public void setVisits(int visits) {
    this.visits = visits;
  }

  public void updateVisits() {
    this.visits++;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public void updateScore(double score) {
    this.score += score;
  }

  public void addChild(Node node) {
    children.add(node);
  }

  public boolean hasUnexpandedChildren() {
    // if this is true, there are still children than can be expanded
    return children.size() < children.size() + unexpandedChildren.size();
  }

  public Node getRandomChild() {
    return children.get(RandomNumbers.getRandomIndex(children.size()));
  }

  public Node getRandomUnexpandedChild() {
    return unexpandedChildren.get(RandomNumbers.getRandomIndex(unexpandedChildren.size()));
  }

  public Node getChildWithMaxVisits() {
    return Collections.max(children, Comparator.comparing(child -> child.getVisits()));
  }
}
