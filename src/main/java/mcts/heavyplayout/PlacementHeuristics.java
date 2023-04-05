package mcts.heavyplayout;

import game.GameBoard;
import game.Move;
import java.util.List;
import utility.RandomUtility;

// TODO copy board with new position before calculating the rest?
public class PlacementHeuristics {
  // TODO working as intended?
  public static void playMaxFish(GameBoard board) {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    double maxFishPerTile = Double.MIN_VALUE;
    int[] bestPosition =
        possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      List<Move> possibleMoves = newBoard.getAllLegalMovesForPenguin(position);
      int fishCount = 0;
      for (Move move : possibleMoves) {
        fishCount += newBoard.getFishCountByPosition(move.getNewPosition());
      }
      double fishPerTile = (double) fishCount / possibleMoves.size();
      if (fishPerTile > maxFishPerTile) {
        maxFishPerTile = fishPerTile;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMaxDirectFish(GameBoard board) {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    double maxFishPerNeighborTile = Double.MIN_VALUE;
    int[] bestPosition = possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      List<int[]> neighborPositions = GameBoard.getNeighborPositions(position);
      int fishCount = 0;
      for (int[] neighborPosition : neighborPositions) {
        fishCount += newBoard.getFishCountByPosition(neighborPosition);
      }
      double fishPerTile = (double) fishCount / neighborPositions.size();
      if (fishPerTile > maxFishPerNeighborTile) {
        maxFishPerNeighborTile = fishPerTile;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
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
