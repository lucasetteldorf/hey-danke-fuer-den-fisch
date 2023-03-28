package mcts;

import game.GameBoard;
import game.Move;
import java.util.*;
import utility.RandomNumbers;

public class Node {
  protected GameBoard board;
  private Node parent;
  private final List<Node> children;
  private int visits;
  private double score;

  public Node() {
    this.children = new ArrayList<>();
  }

  public Node(GameBoard board) {
    this.board = new GameBoard(board);
    this.children = new ArrayList<>();
  }

  // copy constructor
  public Node(Node node) {
    if (node.parent != null) {
      this.parent = node.parent;
    }
    this.children = new ArrayList<>();
    this.children.addAll(node.getChildren());
    this.board = new GameBoard(node.board);
    this.visits = node.visits;
    this.score = node.score;
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

  public GameBoard getBoard() {
    return board;
  }

  public void setBoard(GameBoard board) {
    this.board = board;
  }

  public int getVisits() {
    return visits;
  }

  public void updateVisits() {
    this.visits++;
  }

  public double getScore() {
    return score;
  }

  public void updateScore(double score) {
    this.score += score;
  }

  public void addChild(Node node) {
    children.add(node);
  }

  public Node getChildWithMaxVisits() {
    return Collections.max(children, Comparator.comparing(Node::getVisits));
  }

  public void playRandomMove() {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    Move randomMove = possibleMoves.get(RandomNumbers.getRandomIndex(possibleMoves.size()));
    board.movePenguin(randomMove);
  }

  public void playRandomPlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int[] randomPlacement =
        possiblePlacements.get(RandomNumbers.getRandomIndex(possiblePlacements.size()));
    board.placePenguin(randomPlacement[0], randomPlacement[1]);
  }
}
