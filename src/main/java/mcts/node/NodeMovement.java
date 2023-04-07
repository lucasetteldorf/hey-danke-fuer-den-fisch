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

  // TODO working as intended?
  // TODO maybe also look at max fish with new position (for all penguins)
  public void playMaxTotalFishMove() {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    int maxFishCount = Integer.MIN_VALUE;
    Move bestMove = null;
    for (Move move : possibleMoves) {
      int fishCount = board.getFishCountByPosition(move.getNewPosition());
      if (fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void playMaxFishPerTileMove() {}

  // TODO working as intended???
  public void playIsolateOpponentMove() {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    // score = reachable fish counts / reachable tiles (or sum?!)
    double minScore = Double.MAX_VALUE;
    Move bestMove = null;
    for (Move move : possibleMoves) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      double score = 0;
      // TODO working as intended?
      for (int[] opponentPenguinPosition :
          newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer())) {
        score += newBoard.getReachableFishCountPerTileForPenguin(opponentPenguinPosition);
      }
      if (score < minScore) {
        minScore = score;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  // TODO working as intended???
  public void playSecureAreaMove() {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    double maxScore = Double.MIN_VALUE;
    Move bestMove = null;
    for (Move move : possibleMoves) {
      GameBoard newBoard = new GameBoard(board);
      // TODO working as intended?
      double score = newBoard.getReachableFishCountPerTileForPlayer(board.getCurrentPlayer());
      if (score > maxScore) {
        maxScore = score;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }
}
