package mcts;

public class Tree {
    private Node root;

    public Tree() {
        this.root = new Node();
    }

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addChild(Node parent, Node child) {
        parent.getChildren().add(child);
    }
}
