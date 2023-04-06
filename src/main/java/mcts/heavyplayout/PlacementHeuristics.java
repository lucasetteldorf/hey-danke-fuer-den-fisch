package mcts.heavyplayout;

import game.GameBoard;
import game.players.Player;
import java.util.List;
import utility.RandomUtility;

// TODO tiles that can be reached by multiple penguins can be counted multiple times !!!
// TODO (also) try sum of fish counts instead of average per tile
public class PlacementHeuristics {
  // TODO working as intended?
  public static void playMaxFish(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    double maxFishCountPerTile = Double.MIN_VALUE;
    int[] bestPosition =
        possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      double fishCountPerTile = 0;
      // calculate the value for ALL penguins (tiles might be counted multiple times)
      for (int[] penguinPosition : newBoard.getAllPenguinPositionsForPlayer(currentPlayer)) {
        fishCountPerTile += newBoard.getReachableFishCountPerTileForPenguin(penguinPosition);
      }
      if (fishCountPerTile > maxFishCountPerTile) {
        maxFishCountPerTile = fishCountPerTile;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMaxDirectFish(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    double maxFishCountPerNeighborTile = Double.MIN_VALUE;
    int[] bestPosition =
        possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      double fishCountPerNeighborTile = 0;
      for (int[] penguinPosition : newBoard.getAllPenguinPositionsForPlayer(currentPlayer)) {
        fishCountPerNeighborTile += newBoard.getReachableFishCountPerNeighborTile(penguinPosition);
      }
      if (fishCountPerNeighborTile > maxFishCountPerNeighborTile) {
        maxFishCountPerNeighborTile = fishCountPerNeighborTile;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMaxOwnPossibilities(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int maxOwnPossibilities = Integer.MIN_VALUE;
    int[] bestPosition =
        possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      int ownPossibilities = 0;
      for (int[] penguinPosition : newBoard.getAllPenguinPositionsForPlayer(currentPlayer)) {
        ownPossibilities += newBoard.getAllLegalMovesForPenguin(penguinPosition).size();
      }
      if (ownPossibilities > maxOwnPossibilities) {
        maxOwnPossibilities = ownPossibilities;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }

  // TODO working as intended?
  public static void playMinOpponentPossibilities(GameBoard board) {
    Player currentPlayer = board.getCurrentPlayer();
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int minOpponentPossibilities = Integer.MAX_VALUE;
    int[] bestPosition =
        possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    for (int[] position : possiblePlacements) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(position[0], position[1]);
      int opponentPossibilities = 0;
      for (int[] opponentPenguinPosition :
          newBoard.getAllPenguinPositionsForOpponents(currentPlayer)) {
        opponentPossibilities += board.getAllLegalMovesForPenguin(opponentPenguinPosition).size();
      }
      if (opponentPossibilities < minOpponentPossibilities) {
        minOpponentPossibilities = opponentPossibilities;
        bestPosition = position;
      }
    }
    // TODO always randomly chosen by accident?
    board.placePenguin(bestPosition[0], bestPosition[1]);
  }
}
