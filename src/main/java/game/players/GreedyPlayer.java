package game.players;

import game.GameBoard;
import game.Move;
import java.util.ArrayList;
import java.util.List;
import utility.RandomNumbers;

public class GreedyPlayer extends Player {
  public GreedyPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.GREEDY, name, penguinCount, penguinColor);
  }

  public int[] getBestMove(GameBoard board) {
    List<Integer> threeFishIndices = new ArrayList<>();
    List<Integer> twoFishIndices = new ArrayList<>();
    List<Integer> oneFishIndices = new ArrayList<>();

    for (int[] position : board.getAllPenguinPositionsForPlayer(this)) {
      // TODO correct condition?
      if (board.isValidPenguin(position)) {
        for (Move move : board.getAllLegalMovesForPenguin(position)) {
          int tileIndex = GameBoard.getTileIndexFromPosition(move.getNewPosition());
          switch (board.getFishCountByPosition(move.getNewPosition())) {
            case 3 -> {
              if (!threeFishIndices.contains(tileIndex)) {
                threeFishIndices.add(GameBoard.getTileIndexFromPosition(position));
              }
            }
            case 2 -> {
              if (!twoFishIndices.contains(tileIndex)) {
                twoFishIndices.add(GameBoard.getTileIndexFromPosition(position));
              }
            }
            case 1 -> {
              if (!oneFishIndices.contains(tileIndex)) {
                oneFishIndices.add(GameBoard.getTileIndexFromPosition(position));
              }
            }
          }
        }
      }
    }

    int[] bestPosition;
    if (threeFishIndices.size() > 0) {
      bestPosition =
          GameBoard.getPositionFromTileIndex(
              threeFishIndices.get(RandomNumbers.getRandomIndex(threeFishIndices.size())));
    } else if (twoFishIndices.size() > 0) {
      bestPosition =
          GameBoard.getPositionFromTileIndex(
              twoFishIndices.get(RandomNumbers.getRandomIndex(twoFishIndices.size())));
    } else {
      bestPosition =
          GameBoard.getPositionFromTileIndex(
              oneFishIndices.get(RandomNumbers.getRandomIndex(oneFishIndices.size())));
    }
    return bestPosition;
  }

  public int[] getBestMovementPosition(GameBoard board, int[] position) {
    List<Integer> threeFishIndices = new ArrayList<>();
    List<Integer> twoFishIndices = new ArrayList<>();
    List<Integer> oneFishIndices = new ArrayList<>();

    for (Move move : board.getAllLegalMovesForPenguin(position)) {
      int tileIndex = GameBoard.getTileIndexFromPosition(move.getNewPosition());
      switch (board.getFishCountByPosition(move.getNewPosition())) {
        case 3 -> threeFishIndices.add(tileIndex);
        case 2 -> twoFishIndices.add(tileIndex);
        case 1 -> oneFishIndices.add(tileIndex);
      }
    }

    int[] bestPosition =
        board
            .getAllLegalMovesForPenguin(position)
            .get(RandomNumbers.getRandomIndex(board.getAllLegalMovesForPenguin(position).size()))
            .getNewPosition();
    if (threeFishIndices.size() > 0) {
      bestPosition =
          GameBoard.getPositionFromTileIndex(
              threeFishIndices.get(RandomNumbers.getRandomIndex(threeFishIndices.size())));
    } else if (twoFishIndices.size() > 0) {
      bestPosition =
          GameBoard.getPositionFromTileIndex(
              twoFishIndices.get(RandomNumbers.getRandomIndex(twoFishIndices.size())));
    } else if (oneFishIndices.size() > 0) {
      bestPosition =
          GameBoard.getPositionFromTileIndex(
              oneFishIndices.get(RandomNumbers.getRandomIndex(oneFishIndices.size())));
    }

    return bestPosition;
  }
}
