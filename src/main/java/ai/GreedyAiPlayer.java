package ai;

import game.GameBoard;
import game.IceFloeTile;
import game.Penguin;

import java.util.ArrayList;
import java.util.List;

public class GreedyAiPlayer extends RandomAiPlayer {
  public GreedyAiPlayer(String name) {
    super(name);
  }

  @Override
  public void movePenguin(GameBoard board) {
    Penguin bestPenguin;
    IceFloeTile bestTile;
    do {
      bestPenguin = getBestPenguin(board);
      bestTile = getBestTile(board, bestPenguin);
    } while (bestTile == null);

    board.movePenguin(bestPenguin, bestTile.getCoordinates()[0], bestTile.getCoordinates()[1]);
  }

  private IceFloeTile getBestTile(GameBoard board, Penguin penguin) {
    List<IceFloeTile> threeFishTiles = new ArrayList<>();
    List<IceFloeTile> twoFishTiles = new ArrayList<>();
    List<IceFloeTile> oneFishTiles = new ArrayList<>();
    for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
      IceFloeTile tile = board.getTile(coordinates[0], coordinates[1]);
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

    if (!threeFishTiles.isEmpty()) {
      return threeFishTiles.get(getRandomIndex(threeFishTiles.size()));
    } else if (!twoFishTiles.isEmpty()) {
      return twoFishTiles.get(getRandomIndex(twoFishTiles.size()));
    } else if (!oneFishTiles.isEmpty()) {
      return oneFishTiles.get(getRandomIndex(oneFishTiles.size()));
    } else {
      return null;
    }
  }

  private Penguin getBestPenguin(GameBoard board) {
    int maxThreeFishCount = 0;
    int maxTwoFishCount = 0;
    Penguin bestPenguin = this.getPenguin(getRandomIndex(this.getPenguins().length));

    for (Penguin penguin : this.getPenguins()) {
      List<IceFloeTile> threeFishTiles = new ArrayList<>();
      List<IceFloeTile> twoFishTiles = new ArrayList<>();
      for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
        IceFloeTile tile = board.getTile(coordinates[0], coordinates[1]);
        if (tile != null) {
          switch (tile.getFishCount()) {
            case 3:
              threeFishTiles.add(tile);
              break;
            case 2:
              twoFishTiles.add(tile);
              break;
          }
        }
      }

      if (threeFishTiles.size() > maxThreeFishCount) {
        maxThreeFishCount = threeFishTiles.size();
        bestPenguin = penguin;
      } else if (threeFishTiles.size() <= maxThreeFishCount
          && twoFishTiles.size() > maxTwoFishCount) {
        maxTwoFishCount = twoFishTiles.size();
        bestPenguin = penguin;
      }
    }

    return bestPenguin;
  }
}
