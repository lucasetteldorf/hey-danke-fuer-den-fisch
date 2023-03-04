package ai.mcts;

import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class Node {
  private Node parent;
  private List<Node> children;
  private State state;

  public Node() {
    this.state = new State();
    this.children = new ArrayList<>();
  }

  public Node(State state) {
    this.state = state;
    this.children = new ArrayList<>();
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

  // TODO implement
  public Node getRandomChild() {
    int possibleMoveCount = this.children.size();
    return children.get(RandomNumbers.getRandomIndex(possibleMoveCount));
  }
}
