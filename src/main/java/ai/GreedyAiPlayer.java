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
    // check for direct tile with 3 fish first
    for (Penguin penguin : this.getPenguins()) {
      for (IceFloeTile neighbor : board.getNeighborsTiles(penguin.getPosition()[0], penguin.getPosition()[1])) {
        if (neighbor!= null && neighbor.getFishCount() == 3) {
          if (board.movePenguin(penguin, neighbor.getCoordinates()[0], neighbor.getCoordinates()[1])) {
            return;
          } else {
            continue;
          }
        }
      }
    }

    // then check for other tile with 3 fish
    for (Penguin penguin : this.getPenguins()) {
      for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
        IceFloeTile tile = board.getTile(coordinates[0], coordinates[1]);
        if (tile != null && tile.getFishCount() == 3) {
          board.movePenguin(penguin, tile.getCoordinates()[0], tile.getCoordinates()[1]);
          return;
        }
      }
    }

    // else get penguin with highest sum of fish in reachable tiles and move to max
    Penguin bestPenguin = getBestPenguin(board);
    IceFloeTile newTile = getBestTile(board, bestPenguin);
    board.movePenguin(bestPenguin, newTile.getCoordinates()[0], newTile.getCoordinates()[1]);
  }

  public IceFloeTile getBestTile(GameBoard board, Penguin penguin) {
    List<IceFloeTile> tiles = new ArrayList<>();
    for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
      tiles.add(board.getTile(coordinates[0], coordinates[1]));
    }

    IceFloeTile bestTile = tiles.get(0);
    int max = bestTile.getFishCount();
    for (IceFloeTile tile : tiles) {
      if (tile.getFishCount() > max) {
        max = tile.getFishCount();
        bestTile = tile;
      }
    }

    return bestTile;
  }

  private Penguin getBestPenguin(GameBoard board) {
    Penguin bestPenguin = this.getPenguin(0);
    int max = getTotalFishCount(board, this.getPenguin(0));

    for (int i = 1; i < this.getPenguins().length; i++) {
      if (getTotalFishCount(board, this.getPenguin(i)) > max) {
        max = getTotalFishCount(board, this.getPenguin(i));
        bestPenguin = this.getPenguin(i);
      }
    }

    return bestPenguin;
  }

  private int getTotalFishCount(GameBoard board, Penguin penguin) {
    int count = 0;

    for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
      count += board.getTile(coordinates[0], coordinates[1]).getFishCount();
    }

    return count;
  }
}
