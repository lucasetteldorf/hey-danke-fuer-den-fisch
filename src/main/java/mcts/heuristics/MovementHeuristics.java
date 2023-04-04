package mcts.heuristics;

import game.GameBoard;
import game.Move;
import game.players.Player;
import java.util.List;

// TODO copy board with new position before calculating the rest?
public class MovementHeuristics {
  // TODO working as intended?
  public static void playMaxFish(GameBoard board) {
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

  // TODO working as intended?
  public static void playIsolateOpponent(GameBoard board) {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    // score = reachable fish counts / reachable tiles (or sum?!)
    double minScore = Double.MAX_VALUE;
    Move bestMove = null;
    for (Move move : possibleMoves) {
      int fishCounts = 0;
      int reachableTiles = 0;
      for (int[] opponentPenguinPosition :
          board.getAllPenguinPositionsForOpponents(board.getCurrentPlayer())) {
        List<Move> possibleOpponentMoves =
            board.getAllLegalMovesForPenguin(opponentPenguinPosition);
        for (Move opponentMove : possibleOpponentMoves) {
          fishCounts += board.getFishCountByPosition(opponentMove.getNewPosition());
          reachableTiles += possibleOpponentMoves.size();
        }
      }
      double score = (double) fishCounts / reachableTiles;
      if (score < minScore) {
        minScore = score;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  // TODO working as intended?
  public static void playSecureArea(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    double maxScore = Double.MIN_VALUE;
    Move bestMove = null;
    for (Move move : possibleMoves) {
      int fishCounts = 0;
      int reachableTiles = 0;
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      List<Move> newPossibleMoves = newBoard.getAllLegalMovesForPlayer(currentPlayer);
      for (Move newMove : newPossibleMoves) {
        fishCounts += board.getFishCountByPosition(newMove.getNewPosition());
        reachableTiles += newPossibleMoves.size();
      }
      double score = (double) fishCounts / reachableTiles;
      if (score > maxScore) {
        maxScore = score;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }
}
