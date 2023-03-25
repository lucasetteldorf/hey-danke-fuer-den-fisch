package game.players;

import static utility.RandomNumbers.getRandomIndex;

import game.GameBoard;
import game.Move;
import game.Penguin;
import java.util.ArrayList;
import java.util.List;
import utility.RandomNumbers;

public class GreedyPlayer extends Player {
  public GreedyPlayer(String name, int penguinCount, String penguinColor) {
    super(PlayerType.GREEDY, name, penguinCount, penguinColor);
  }

  public int[] getBestPenguinPosition(GameBoard board) {
    List<Penguin> threeFishPenguins = new ArrayList<>();
    List<Penguin> twoFishPenguins = new ArrayList<>();
    List<Penguin> oneFishPenguins = new ArrayList<>();

    for (Penguin penguin : board.getAllPenguinsForPlayer(this)) {
      if (penguin.isOnBoard()) {
        for (Move move : board.getAllLegalMovesForPenguin(penguin)) {
          switch (board.getFishCountByPosition(move.getNewPosition())) {
            case 3:
              if (!threeFishPenguins.contains(penguin)) {
                threeFishPenguins.add(penguin);
              }
              break;
            case 2:
              if (!twoFishPenguins.contains(penguin)) {
                twoFishPenguins.add(penguin);
              }
              break;
            case 1:
              if (!oneFishPenguins.contains(penguin)) {
                oneFishPenguins.add(penguin);
              }
              break;
          }
        }
      }
    }

    Penguin bestPenguin;
    if (threeFishPenguins.size() > 0) {
      bestPenguin = threeFishPenguins.get(RandomNumbers.getRandomIndex(threeFishPenguins.size()));
    } else if (twoFishPenguins.size() > 0) {
      bestPenguin = twoFishPenguins.get(RandomNumbers.getRandomIndex(twoFishPenguins.size()));
    } else {
      bestPenguin = oneFishPenguins.get(RandomNumbers.getRandomIndex(oneFishPenguins.size()));
    }

    return bestPenguin.getPosition();
  }

  public int[] getBestMovementPosition(GameBoard board, Penguin penguin) {
    List<int[]> threeFishPositions = new ArrayList<>();
    List<int[]> twoFishPositions = new ArrayList<>();
    List<int[]> oneFishPositions = new ArrayList<>();
    for (Move move : board.getAllLegalMovesForPenguin(penguin)) {
      switch (board.getFishCountByPosition(move.getNewPosition())) {
        case 3:
          threeFishPositions.add(move.getNewPosition());
          break;
        case 2:
          twoFishPositions.add(move.getNewPosition());
          break;
        case 1:
          oneFishPositions.add(move.getNewPosition());
          break;
      }
    }

    int[] bestPosition =
        board
            .getAllLegalMovesForPenguin(penguin)
            .get(RandomNumbers.getRandomIndex(board.getAllLegalMovesForPenguin(penguin).size()))
            .getNewPosition();
    if (!threeFishPositions.isEmpty()) {
      bestPosition = threeFishPositions.get(getRandomIndex(threeFishPositions.size()));
    } else if (!twoFishPositions.isEmpty()) {
      bestPosition = twoFishPositions.get(getRandomIndex(twoFishPositions.size()));
    } else if (!oneFishPositions.isEmpty()) {
      bestPosition = oneFishPositions.get(getRandomIndex(oneFishPositions.size()));
    }

    return bestPosition;
  }
}
