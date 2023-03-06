package game.players;

import game.GameBoard;
import game.Penguin;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class RandomAiPlayer extends Player {
    public RandomAiPlayer(String name, int penguinCount, String penguinColor) {
        super(PlayerType.RANDOM_AI, name, penguinCount, penguinColor);
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

    public int[] getRandomMovementPositionForPenguin(GameBoard board, Penguin penguin) {
        List<int[]> legalMoves = board.getLegalMovesForPenguin(penguin);
        return legalMoves.get(RandomNumbers.getRandomIndex(legalMoves.size()));
    }
}
