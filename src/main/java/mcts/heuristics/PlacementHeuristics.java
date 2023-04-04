package mcts.heuristics;

import game.GameBoard;
import game.Move;
import java.util.List;

// TODO copy board with new position before calculating the rest?
public class PlacementHeuristics {
  // TODO working as intended?
  public static void playMaxFish(GameBoard board) {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxFishCounts = Integer.MIN_VALUE;
    int[] bestPosition = null;
    for (int[] position : possiblePlacements) {
      List<Move> possibleMoves = board.getAllLegalMovesForPenguin(position);
      int fishCounts = 0;
      for (Move move : possibleMoves) {
        fishCounts += board.getFishCountByPosition(move.getNewPosition());
      }
      if (fishCounts > maxFishCounts) {
        maxFishCounts = fishCounts;
        bestPosition = position;
      }
    }
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMaxDirectFish(GameBoard board) {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxFishCounts = Integer.MIN_VALUE;
    int[] bestPosition = null;
    for (int[] position : possiblePlacements) {
      int fishCounts = 0;
      for (int[] neighborPosition : GameBoard.getNeighborPositions(position)) {
        fishCounts += board.getFishCountByPosition(neighborPosition);
      }
      if (fishCounts > maxFishCounts) {
        maxFishCounts = fishCounts;
        bestPosition = position;
      }
    }
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMaxOwnPossibilities(GameBoard board) {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxOwnPossibilities = Integer.MIN_VALUE;
    int[] bestPosition = null;
    for (int[] position : possiblePlacements) {
      int ownPossibilities = 0;
      List<Move> possibleMoves = board.getAllLegalMovesForPenguin(position);
      for (int[] penguinPosition :
          board.getAllPenguinPositionsForPlayer(board.getCurrentPlayer())) {
        ownPossibilities += board.getAllLegalMovesForPenguin(penguinPosition).size();
      }
      if (ownPossibilities > maxOwnPossibilities) {
        maxOwnPossibilities = ownPossibilities;
        bestPosition = position;
      }
    }
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMinOpponentPossibilities(GameBoard board) {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int minOpponentPossibilities = Integer.MAX_VALUE;
    int[] bestPosition = null;
    for (int[] position : possiblePlacements) {
      int opponentPossibilities = 0;
      for (int[] opponentPenguinPosition :
          board.getAllPenguinPositionsForOpponents(board.getCurrentPlayer())) {
        opponentPossibilities += board.getAllLegalMovesForPenguin(opponentPenguinPosition).size();
      }
      if (opponentPossibilities < minOpponentPossibilities) {
        minOpponentPossibilities = opponentPossibilities;
        bestPosition = position;
      }
    }
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }
}
