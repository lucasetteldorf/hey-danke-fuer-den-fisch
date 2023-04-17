package challenge;

import game.logic.GameBoard;
import game.logic.Move;
import java.util.ArrayList;
import java.util.List;

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

  // like greedy
  public void playMaxFishOnNewTile() {
    int maxFish = Integer.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      int fish = board.getFishCountByPosition(move.getNewPosition());
      if (fish > maxFish) {
        maxFish = fish;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMaxNewFishForPenguin() {
    int maxReachableFish = Integer.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int newReachableFish = newBoard.getReachableFishForPenguin(move.getNewPosition());
      if (newReachableFish > maxReachableFish) {
        maxReachableFish = newReachableFish;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMaxNewTilesForPenguin() {
    int maxReachableTiles = Integer.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int newReachableTiles = newBoard.getReachableTilesForPenguin(move.getNewPosition());
      if (newReachableTiles > maxReachableTiles) {
        maxReachableTiles = newReachableTiles;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMaxNewFishPerTileForPenguin() {
    double maxReachableFishPerTile = Integer.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      double newReachableFisPerTile = newBoard.getReachableFishPerTileForPenguin(move.getNewPosition());
      if (newReachableFisPerTile > maxReachableFishPerTile) {
        maxReachableFishPerTile = newReachableFisPerTile;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMaxNewFishForAllPenguins() {
    int maxReachableFish = Integer.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int newReachableFish = newBoard.getReachableFishForAllPenguins(newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (newReachableFish > maxReachableFish) {
        maxReachableFish = newReachableFish;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMaxNewTilesForAllPenguins() {
    int maxReachableTiles = Integer.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int newReachableTiles = newBoard.getReachableTilesForAllPenguins(newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (newReachableTiles > maxReachableTiles) {
        maxReachableTiles = newReachableTiles;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMaxNewFishPerTileForAllPenguins() {
    double maxReachableFishPerTile = Double.MIN_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      double newReachableFishPerTile = newBoard.getReachableFishPerTileForAllPenguins(newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (newReachableFishPerTile > maxReachableFishPerTile) {
        maxReachableFishPerTile = newReachableFishPerTile;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMinNewFishForOpponentPenguins() {
    int minReachableFish = Integer.MAX_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int newReachableFish = newBoard.getReachableFishForAllPenguins(newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (newReachableFish < minReachableFish) {
        minReachableFish = newReachableFish;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMinNewTilesForOpponentPenguins() {
    int minReachableTiles = Integer.MAX_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int newReachableTiles = newBoard.getReachableTilesForAllPenguins(newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (newReachableTiles < minReachableTiles) {
        minReachableTiles = newReachableTiles;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }

  public void playMinNewFishPerTileForOpponentPenguins() {
    double maxReachableFishPerTile = Double.MAX_VALUE;
    Move bestMove = null;

    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      double newReachableFishPerTile = newBoard.getReachableFishPerTileForAllPenguins(newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (newReachableFishPerTile < maxReachableFishPerTile) {
        maxReachableFishPerTile = newReachableFishPerTile;
        bestMove = move;
      }
    }

    board.movePenguin(bestMove);
  }
}
