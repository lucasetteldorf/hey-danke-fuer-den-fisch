package game.players;

import game.GameBoard;
import game.Move;
import game.Penguin;
import java.util.ArrayList;
import java.util.List;
import utility.RandomNumbers;

public class RandomPlayer extends Player {
  public RandomPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.RANDOM, name, penguinCount, penguinColor);
  }

  public int[] getRandomPenguinPosition(GameBoard board) {
    List<Penguin> movablePenguins = new ArrayList<>();
    for (Penguin penguin : board.getAllPenguinsForPlayer(this)) {
      if (board.hasPenguinLegalMoves(penguin)) {
        movablePenguins.add(penguin);
      }
    }
    return movablePenguins.get(RandomNumbers.getRandomIndex(movablePenguins.size())).getPosition();
  }

  public int[] getRandomMovementPositionForPenguin(GameBoard board, int[] position) {
    List<Move> legalMoves =
        board.getAllLegalMovesForPenguin(board.getPenguin(position[0], position[1]));
    return legalMoves.get(RandomNumbers.getRandomIndex(legalMoves.size())).getNewPosition();
  }
}
