package mcts.node;

import game.logic.GameBoard;
import game.logic.Move;
import java.util.ArrayList;
import java.util.List;
import utility.RandomUtility;

public class NodeMovement extends Node {
  private final List<Move> untriedMoves;
  private Move previousMove;

  public NodeMovement() {
    super();
    this.untriedMoves = new ArrayList<>();
  }

  public NodeMovement(GameBoard board) {
    super(board);
    this.untriedMoves = new ArrayList<>();
  }

  // copy constructor
  public NodeMovement(NodeMovement node) {
    super(node);
    this.untriedMoves = new ArrayList<>();
    this.untriedMoves.addAll(node.getUntriedMoves());
    if (node.previousMove != null) {
      this.previousMove = new Move(node.previousMove);
    }
  }

  public List<Move> getUntriedMoves() {
    return untriedMoves;
  }

  public Move getPreviousMove() {
    return previousMove;
  }

  public void setPreviousMove(Move previousMove) {
    this.previousMove = previousMove;
  }

  public void initUntriedMoves() {
    untriedMoves.addAll(board.getAllLegalMovesForCurrentPlayer());
  }

  public boolean hasUntriedMoves() {
    return this.untriedMoves.size() > 0;
  }

  public Move getRandomUntriedMove() {
    return untriedMoves.get(RandomUtility.getRandomIndex(untriedMoves.size()));
  }

  public void expandChildrenMovement() {
    for (Move move : untriedMoves) {
      NodeMovement node = new NodeMovement(this.board);
      node.getBoard().movePenguin(move);
      node.setParent(this);
      node.initUntriedMoves();
      node.setPreviousMove(move);
      addChild(node);
    }
    untriedMoves.clear();
  }
}
