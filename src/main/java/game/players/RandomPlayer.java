package game.players;

import game.logic.GameBoard;
import game.logic.Move;
import java.util.ArrayList;
import java.util.List;
import utility.RandomUtility;

public class RandomPlayer extends Player {
  public RandomPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.RANDOM, name, penguinCount, penguinColor);
  }

  public int[] getRandomPenguinPosition(GameBoard board) {
    List<int[]> penguinPositions = new ArrayList<>();
    for (int[] position : board.getAllPenguinPositionsForPlayer(this)) {
      if (board.hasPenguinLegalMoves(position)) {
        penguinPositions.add(position);
      }
    }
    return penguinPositions.get(RandomUtility.getRandomIndex(penguinPositions.size()));
  }

  public int[] getRandomMovementPositionForPenguin(GameBoard board, int[] position) {
    List<Move> legalMoves = board.getAllLegalMovesForPenguin(position);
    return legalMoves.get(RandomUtility.getRandomIndex(legalMoves.size())).getNewPosition();
  }
}
