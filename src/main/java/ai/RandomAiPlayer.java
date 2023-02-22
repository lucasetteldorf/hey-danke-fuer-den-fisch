package ai;

import game.GameBoard;
import game.Penguin;
import game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAiPlayer extends Player {
  public RandomAiPlayer() {
    super("Random AI");
  }

  public void placePenguinRandomly(GameBoard board) {
    int randomIndex = getRandomIndex(board.getLegalPlacementCoordinates().size());
    int[] placementCoordinates = board.getLegalPlacementCoordinates().get(randomIndex);
    board.placePenguin(getCurrentPenguin(), placementCoordinates[0], placementCoordinates[1]);
    updateCurrentPenguinIndex();
  }

  public void moveRandomPenguinRandomly(GameBoard board) {
    Penguin randomPenguin = getRandomPenguinToMove(board);

    int randomIndex = getRandomIndex(board.getAllLegalMovesForPenguin(randomPenguin).size());
    int[] movementCoordinates = board.getAllLegalMovesForPenguin(randomPenguin).get(randomIndex);
    board.movePenguin(randomPenguin, movementCoordinates[0], movementCoordinates[1]);
  }

  private int getRandomIndex(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  private Penguin getRandomPenguinToMove(GameBoard board) {
    List<Penguin> movablePenguins = new ArrayList<>();
    for (Penguin penguin : getPenguins()) {
      if (penguin.isOnGameBoard() && board.hasPenguinLegalMoves(penguin)) {
        movablePenguins.add(penguin);
      }
    }
    int randomIndex = getRandomIndex(movablePenguins.size());
    return getPenguin(randomIndex);
  }
}
