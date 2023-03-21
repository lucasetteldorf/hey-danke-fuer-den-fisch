package game.players;

import static utility.RandomNumbers.getRandomIndex;

import game.GameBoard;
import game.IceFloeTile;
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

    for (Penguin penguin : this.getPenguins()) {
      if (penguin.isOnBoard()) {
        for (Move move : board.getAllLegalMovesForPenguin(penguin)) {
          switch (board.getTile(move.getNewRow(), move.getNewCol()).getFishCount()) {
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
    List<IceFloeTile> threeFishTiles = new ArrayList<>();
    List<IceFloeTile> twoFishTiles = new ArrayList<>();
    List<IceFloeTile> oneFishTiles = new ArrayList<>();
    for (Move move : board.getAllLegalMovesForPenguin(penguin)) {
      IceFloeTile tile = board.getTile(move.getNewRow(), move.getNewCol());
      if (tile != null) {
        switch (tile.getFishCount()) {
          case 3:
            threeFishTiles.add(tile);
            break;
          case 2:
            twoFishTiles.add(tile);
            break;
          case 1:
            oneFishTiles.add(tile);
            break;
        }
      }
    }

    int[] bestPosition =
        board
            .getAllLegalMovesForPenguin(penguin)
            .get(RandomNumbers.getRandomIndex(board.getAllLegalMovesForPenguin(penguin).size()))
            .getNewPosition();
    if (!threeFishTiles.isEmpty()) {
      bestPosition = threeFishTiles.get(getRandomIndex(threeFishTiles.size())).getPosition();
    } else if (!twoFishTiles.isEmpty()) {
      bestPosition = twoFishTiles.get(getRandomIndex(twoFishTiles.size())).getPosition();
    } else if (!oneFishTiles.isEmpty()) {
      bestPosition = oneFishTiles.get(getRandomIndex(oneFishTiles.size())).getPosition();
    }

    return bestPosition;
  }
}
