package mcts.heavyplayout;

import game.GameBoard;
import game.Move;
import game.players.Player;
import java.util.List;

public class MovementHeuristics {
  // TODO working as intended?
  // TODO maybe also look at max fish with new position (for all penguins)
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

  // TODO working as intended???
  public static void playIsolateOpponent(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
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
          newBoard.getAllPenguinPositionsForOpponents(currentPlayer)) {
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
  public static void playSecureArea(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    double maxScore = Double.MIN_VALUE;
    Move bestMove = null;
    for (Move move : possibleMoves) {
      GameBoard newBoard = new GameBoard(board);
      // TODO working as intended?
      double score = newBoard.getReachableFishCountPerTileForPlayer(currentPlayer);
      if (score > maxScore) {
        maxScore = score;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }
}
