package mcts;

import game.Move;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utility.RandomNumbers;

public class Node {
  private Node parent;
  private List<Node> children;
  private List<Move> untriedMoves;
  private State state;
  private int visits;
  // TODO maybe change to wins (int)
  private double score;

  public Node() {
    this.state = new State();
    this.children = new ArrayList<>();
    this.untriedMoves = new ArrayList<>();
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
    this.untriedMoves = new ArrayList<>();
    for (Move move : node.getUntriedMoves()) {
      this.untriedMoves.add(move);
    }
    this.state = new State(node.state);
    this.visits = node.visits;
    this.score = node.score;
  }

  public Node(State state) {
    this.state = new State(state);
    this.children = new ArrayList<>();
    this.untriedMoves = new ArrayList<>();
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

  public List<Move> getUntriedMoves() {
    return untriedMoves;
  }

  public void setUntriedMoves(List<Move> untriedMoves) {
    this.untriedMoves = untriedMoves;
  }

  public void initUntriedMoves() {
    for (Move move : state.getBoard().getAllLegalMovesForCurrentPlayer()) {
      untriedMoves.add(move);
    }
  }

  public boolean hasUntriedMoves() {
    return this.untriedMoves.size() > 0;
  }

  public Move getRandomUntriedMove() {
    return untriedMoves.get(RandomNumbers.getRandomIndex(untriedMoves.size()));
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

  public Node getRandomChild() {
    return children.get(RandomNumbers.getRandomIndex(children.size()));
  }

  public Node getChildWithMaxVisits() {
    return Collections.max(children, Comparator.comparing(child -> child.getVisits()));
  }
}
