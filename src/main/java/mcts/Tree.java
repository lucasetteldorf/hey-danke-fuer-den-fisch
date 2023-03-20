package mcts;

public class Tree {
  private Node root;

  public Tree() {
    this.root = new Node();
  }

  public Node getRoot() {
    return root;
  }

  public void setRoot(Node root) {
    this.root = root;
  }
}
