package ai;

import game.GameBoard;
import game.Penguin;
import game.Player;
import game.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAiPlayer extends Player {
    public RandomAiPlayer(String name, int penguinCount, String penguinColor, PlayerType type) {
        super(name, penguinCount, penguinColor, type);
    }

    public void placePenguin(GameBoard board) {
        int randomIndex = getRandomIndex(board.getLegalPlacementPositions().size());
        int[] placementCoordinates = board.getLegalPlacementPositions().get(randomIndex);
        board.placeCurrentPlayerPenguin(placementCoordinates[0], placementCoordinates[1]);
        updatePenguinToPlace();
    }

    public void movePenguin(GameBoard board) {
        Penguin randomPenguin = getRandomPenguinToMove(board);

        int randomIndex = getRandomIndex(board.getLegalMovesForPenguin(randomPenguin).size());
        int[] movementCoordinates = board.getLegalMovesForPenguin(randomPenguin).get(randomIndex);
        board.moveCurrentPlayerPenguin(randomPenguin.getPosition()[0], randomPenguin.getPosition()[1], movementCoordinates[0], movementCoordinates[1]);
    }

    int getRandomIndex(int max) {
        Random random = new Random();
        return (max > 0) ? random.nextInt(max) : 0;
    }

    Penguin getRandomPenguinToMove(GameBoard board) {
        List<Penguin> movablePenguins = new ArrayList<>();
        for (Penguin penguin : this.getPenguins()) {
            if (penguin.isOnGameBoard() && board.hasPenguinLegalMoves(penguin)) {
                movablePenguins.add(penguin);
            }
        }
        int randomIndex = getRandomIndex(movablePenguins.size());
        return movablePenguins.get(randomIndex);
    }
}
