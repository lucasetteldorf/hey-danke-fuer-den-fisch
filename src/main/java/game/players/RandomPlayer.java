package game.players;

import game.GameBoard;
import game.Move;
import game.Penguin;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class RandomPlayer extends BasePlayer {
    public RandomPlayer(String name, int penguinCount, String penguinColor) {
        super(PlayerType.RANDOM, name, penguinCount, penguinColor);
    }

    public int[] getRandomPenguinPosition(GameBoard board) {
        List<Penguin> movablePenguins = new ArrayList<>();
        for (Penguin penguin : this.getPenguins()) {
            if (board.hasPenguinLegalMoves(penguin)) {
                movablePenguins.add(penguin);
            }
        }
        return movablePenguins.get(RandomNumbers.getRandomIndex(movablePenguins.size())).getPosition();
    }

    public int[] getRandomMovementPositionForPenguin(GameBoard board, int[] position) {
        List<Move> legalMoves = board.getAllLegalMovesForPenguin(board.getPenguin(position[0], position[1]));
        return legalMoves.get(RandomNumbers.getRandomIndex(legalMoves.size())).getNewPosition();
    }
}
