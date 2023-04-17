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

  public void playGreedy() {
    Move bestMove = null;
    int maxFishCount = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      int[] newPosition = move.getNewPosition();
      int fishCount = board.getFishCountByPosition(newPosition);
      if (fishCount != 0 && fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  // TODO just max. for placed penguin again instead of all?
  public void playMaxOwnReachableFishCount() {
    Move bestMove = null;
    int maxFishCount = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void playMinEnemyReachableFishCount() {
    Move bestMove = null;
    int minFishCount = Integer.MAX_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (fishCount < minFishCount) {
        minFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  // TODO just max. for placed penguin again instead of all?
  public void playMaxOwnReachableTiles() {
    Move bestMove = null;
    int maxTiles = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int tiles =
          newBoard.getReachableTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (tiles > maxTiles) {
        maxTiles = tiles;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void playMinEnemyReachableTiles() {
    Move bestMove = null;
    int minTiles = Integer.MAX_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int tiles =
          newBoard.getReachableTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (tiles < minTiles) {
        minTiles = tiles;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }
}
