package ai;

import game.GameBoard;
import game.Penguin;
import game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAiPlayer extends Player {
  public RandomAiPlayer(String name, int penguinCount, String penguinColor) {
    super(name, penguinCount, penguinColor);
  }

  public void placePenguin(GameBoard board) {
    int randomIndex = getRandomIndex(board.getLegalPlacementPositions().size());
    int[] placementCoordinates = board.getLegalPlacementPositions().get(randomIndex);
    board.placePenguin(getPenguinToPlace(), placementCoordinates[0], placementCoordinates[1]);
    updatePenguinToPlace();
  }

  public void movePenguin(GameBoard board) {
    Penguin randomPenguin = getRandomPenguinToMove(board);

    int randomIndex = getRandomIndex(board.getAllLegalMovesForPenguin(randomPenguin).size());
    int[] movementCoordinates = board.getAllLegalMovesForPenguin(randomPenguin).get(randomIndex);
    board.movePenguin(randomPenguin, movementCoordinates[0], movementCoordinates[1]);
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
