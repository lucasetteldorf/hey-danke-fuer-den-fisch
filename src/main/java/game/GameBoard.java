package game;

import game.players.Player;
import java.util.*;
import utility.RandomNumbers;

public class GameBoard {
  private static final int TILE_COUNT = 60;
  private static final int ROW_COUNT = 8;
  private static final int COL_COUNT = 15;
  private static final int[][] NEIGHBOR_DISTANCES =
      new int[][] {{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};
  private static final int[][] POSITIONS =
      new int[][] {
        {0, 1}, {0, 3}, {0, 5}, {0, 7}, {0, 9}, {0, 11}, {0, 13}, {1, 0}, {1, 2}, {1, 4}, {1, 6},
        {1, 8}, {1, 10}, {1, 12}, {1, 14}, {2, 1}, {2, 3}, {2, 5}, {2, 7}, {2, 9}, {2, 11}, {2, 13},
        {3, 0}, {3, 2}, {3, 4}, {3, 6}, {3, 8}, {3, 10}, {3, 12}, {3, 14}, {4, 1}, {4, 3}, {4, 5},
        {4, 7}, {4, 9}, {4, 11}, {4, 13}, {5, 0}, {5, 2}, {5, 4}, {5, 6}, {5, 8}, {5, 10}, {5, 12},
        {5, 14}, {6, 1}, {6, 3}, {6, 5}, {6, 7}, {6, 9}, {6, 11}, {6, 13}, {7, 0}, {7, 2}, {7, 4},
        {7, 6}, {7, 8}, {7, 10}, {7, 12}, {7, 14}
      };

  // 0 = tile removed from board
  private final int[] fishCounts;
  private final Penguin[] penguins;
  // true if tile is on board and no penguin is at the corresponding position
  private final boolean[] unoccupiedPositions;
  private final Player[] players;
  private final int penguinCount;
  private int currentPenguinIndex;
  private int currentPlayerIndex;

  public GameBoard() {
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    this.fishCounts = initGameBoard();
    this.penguins = null;
    this.penguinCount = 0;
    this.players = null;
  }

  public GameBoard(Player[] players) {
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    this.fishCounts = initGameBoard();
    this.penguinCount = calculatePenguinCount(players.length);
    this.penguins = new Penguin[penguinCount];
    this.players = players;
  }

  public GameBoard(int[] fishCounts) {
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    this.fishCounts = Arrays.copyOf(fishCounts, TILE_COUNT);
    this.penguins = null;
    this.penguinCount = 0;
    this.players = null;
  }

  // copy constructor
  public GameBoard(GameBoard board) {
    this.fishCounts = Arrays.copyOf(board.fishCounts, TILE_COUNT);
    this.penguinCount = board.penguinCount;
    this.penguins = new Penguin[board.penguins.length];
    for (int i = 0; i < this.penguins.length; i++) {
      if (board.penguins[i] != null) {
        this.penguins[i] = new Penguin(board.penguins[i]);
      }
    }
    this.currentPenguinIndex = board.currentPenguinIndex;
    this.unoccupiedPositions = Arrays.copyOf(board.unoccupiedPositions, TILE_COUNT);
    this.players = new Player[board.players.length];
    for (int i = 0; i < this.players.length; i++) {
      this.players[i] = new Player(board.players[i]);
    }
    this.currentPlayerIndex = board.currentPlayerIndex;
  }

  private static int calculatePenguinCount(int playerCount) {
    return (playerCount == 3) ? 9 : 8;
  }

  public static boolean isValidPosition(int row, int col) {
    return row >= 0
        && row <= 7
        && col >= 0
        && (row % 2 != 0 || col <= 13)
        && (row % 2 != 1 || col <= 14)
        && row % 2 != col % 2;
  }

  public static boolean isValidPosition(int[] position) {
    if (position == null) {
      return false;
    }
    return isValidPosition(position[0], position[1]);
  }

  public static int getIndexFromPosition(int row, int col) {
    if (row % 2 == 0) {
      return ((row / 2) * 15) + (col / 2);
    } else {
      return ((row / 2) + row * 7) + (col / 2);
    }
  }

  private static int getIndexFromPosition(int[] position) {
    return getIndexFromPosition(position[0], position[1]);
  }

  private static int[] getPositionFromIndex(int index) {
    return POSITIONS[index];
  }

  private static int[] calculateNeighborPosition(int direction, int row, int col) {
    int neighborRow = row + NEIGHBOR_DISTANCES[direction][0];
    int neighborCol = col + NEIGHBOR_DISTANCES[direction][1];
    if (neighborRow >= 0 && neighborRow <= 7 && neighborCol >= 0 && neighborCol <= 14) {
      return new int[] {neighborRow, neighborCol};
    }
    return null;
  }

  private int[] initGameBoard() {
    List<Integer> fishCounts = new ArrayList<>();
    int fishCount = 1;
    for (int i = 0; i < TILE_COUNT; i++) {
      fishCounts.add(fishCount);

      if (i == 29 || i == 49) {
        fishCount++;
      }
    }

    int[] randomFishCounts = new int[TILE_COUNT];
    for (int i = 0; i < TILE_COUNT; i++) {
      unoccupiedPositions[i] = true;
      int randomIndex = RandomNumbers.getRandomIndex(fishCounts.size());
      randomFishCounts[i] = fishCounts.get(randomIndex);
      fishCounts.remove(randomIndex);
    }
    return randomFishCounts;
  }

  public Player[] getPlayers() {
    return players;
  }

  public Player getCurrentPlayer() {
    return players[currentPlayerIndex];
  }

  public void updateCurrentPlayer() {
    if (!isPlacementPhaseOver()) {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    } else {
      do {
        if (!hasPlayerLegalMoves(getCurrentPlayer())
            && !getCurrentPlayer().arePenguinsRemovedFromBoard()) {
          removeAllPenguinsAndTiles();
          getCurrentPlayer().setPenguinsRemovedFromBoard(true);
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
      } while (!hasPlayerLegalMoves(getCurrentPlayer()) && !isMovementPhaseOver());
    }
  }

  public Penguin getPenguinByPosition(int[] position) {
    for (Penguin penguin : penguins) {
      if (penguin != null && Arrays.equals(position, penguin.getPosition())) {
        return penguin;
      }
    }
    return null;
  }

  public Penguin getPenguinByPosition(int row, int col) {
    return getPenguinByPosition(new int[] {row, col});
  }

  public Penguin[] getAllPenguinsForPlayer(Player player) {
    Penguin[] playerPenguins = new Penguin[penguinCount / players.length];
    for (int i = 0, k = 0; i < penguins.length; i++) {
      if (penguins[i].getColor().equals(player.getPenguinColor())) {
        playerPenguins[k++] = penguins[i];
      }
    }
    return playerPenguins;
  }

  public int getFishCountByPosition(int[] position) {
    return fishCounts[getIndexFromPosition(position)];
  }

  public int getFishCountByPosition(int row, int col) {
    return getFishCountByPosition(new int[] {row, col});
  }

  public boolean getUnoccupiedPositionByPosition(int[] position) {
    return unoccupiedPositions[getIndexFromPosition(position)];
  }

  public boolean isPlacementPhaseOver() {
    for (Player player : players) {
      if (player.canPlacePenguin()) {
        return false;
      }
    }
    return true;
  }

  public boolean isLegalPlacementPosition(int[] position) {
    return unoccupiedPositions[getIndexFromPosition(position)]
        && fishCounts[getIndexFromPosition(position)] == 1;
  }

  public boolean isLegalPlacementIndex(int index) {
    return unoccupiedPositions[index] && fishCounts[index] == 1;
  }

  public List<int[]> getAllLegalPlacementPositions() {
    List<int[]> legalPlacementPositions = new ArrayList<>();
    for (int i = 0; i < TILE_COUNT; i++) {
      if (isLegalPlacementPosition(POSITIONS[i])) {
        legalPlacementPositions.add(POSITIONS[i]);
      }
    }
    return legalPlacementPositions;
  }

  public void placePenguin(int row, int col) {
    placePenguin(getCurrentPlayer(), row, col);
  }

  public void placePenguin(Player player, int row, int col) {
    int placementIndex = getIndexFromPosition(row, col);
    if (player.canPlacePenguin() && isLegalPlacementIndex(placementIndex)) {
      unoccupiedPositions[placementIndex] = false;
      Penguin penguinToPlace = new Penguin(player.getPenguinColor(), currentPenguinIndex);
      currentPenguinIndex++;
      penguinToPlace.setPosition(row, col);
      penguins[penguinToPlace.getIndex()] = penguinToPlace;
      penguinToPlace.setOnBoard(true);
      player.updatePlacedPenguinCount();
      updateCurrentPlayer();
    }
  }

  public boolean isMovementPhaseOver() {
    for (Player player : players) {
      if (hasPlayerLegalMoves(player)) {
        return false;
      }
    }
    return true;
  }

  public void movePenguin(Move move) {
    movePenguin(getCurrentPlayer(), move);
  }

  public void movePenguin(Player player, Move move) {
    Penguin penguinToMove = getPenguinByPosition(move.getOldPosition());

    if (hasPenguinLegalMoves(penguinToMove)
        && isLegalMove(move.getOldPosition(), move.getNewPosition())
        && penguinToMove.getColor().equals(player.getPenguinColor())) {
      penguinToMove.setPosition(move.getNewRow(), move.getNewCol());
      player.updateCollectedTileCount();
      player.updateCollectedFishCount(fishCounts[getIndexFromPosition(move.getOldPosition())]);
      unoccupiedPositions[getIndexFromPosition(move.getOldPosition())] = false;
      unoccupiedPositions[getIndexFromPosition(move.getNewPosition())] = false;
      fishCounts[getIndexFromPosition(move.getOldPosition())] = 0;
      updateCurrentPlayer();
    }
  }

  public boolean isLegalMove(int[] oldPosition, int[] newPosition) {
    if (!Arrays.equals(oldPosition, newPosition)
        && unoccupiedPositions[getIndexFromPosition(newPosition)]) {
      int rowDiff = newPosition[0] - oldPosition[0];
      int colDiff = newPosition[1] - oldPosition[1];
      int totalDiff = Math.abs(rowDiff) - Math.abs(colDiff);

      // top right (direction 0)
      if (rowDiff <= -1 && colDiff >= 1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllPositionsValid(oldPosition, newPosition, 0);
      }

      // right (direction 1)
      if (rowDiff == 0 && colDiff >= 2) {
        return areAllPositionsValid(oldPosition, newPosition, 1);
      }

      // bottom right (direction 2)
      if (rowDiff >= 1 && colDiff >= 1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllPositionsValid(oldPosition, newPosition, 2);
      }

      // bottom left (direction 3)
      if (rowDiff >= 1 && colDiff <= -1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllPositionsValid(oldPosition, newPosition, 3);
      }

      // left (direction 4)
      if (rowDiff == 0 && colDiff <= -2) {
        return areAllPositionsValid(oldPosition, newPosition, 4);
      }

      // top left (direction 5)
      if (rowDiff <= -1 && colDiff <= -1) {
        if (totalDiff != 0) {
          return false;
        }
        return areAllPositionsValid(oldPosition, newPosition, 5);
      }
    }

    return false;
  }

  private boolean areAllPositionsValid(int[] oldPosition, int[] newPosition, int direction) {
    int[] neighborPosition = oldPosition;

    int colDiff = Math.abs(newPosition[1] - oldPosition[1]);
    if (direction == 1 || direction == 4) {
      colDiff /= 2;
    }

    for (int i = 0; i < colDiff; i++) {
      neighborPosition =
          calculateNeighborPosition(direction, neighborPosition[0], neighborPosition[1]);

      if (neighborPosition == null || !getUnoccupiedPositionByPosition(neighborPosition)) {
        return false;
      }

      if (i == colDiff - 1 && !Arrays.equals(neighborPosition, newPosition)) {
        return false;
      }
    }

    return true;
  }

  public void removeAllPenguinsAndTiles() {
    for (Penguin penguin : getAllPenguinsForPlayer(getCurrentPlayer())) {
      unoccupiedPositions[getIndexFromPosition(penguin.getPosition())] = false;
      getCurrentPlayer().updateCollectedTileCount();
      getCurrentPlayer()
          .updateCollectedFishCount(fishCounts[getIndexFromPosition(penguin.getPosition())]);
      fishCounts[getIndexFromPosition(penguin.getPosition())] = 0;
      penguin.setOnBoard(false);
    }
  }

  // TODO optimize!
  public List<Move> getAllLegalMovesForPenguin(Penguin penguin) {
    List<Move> possibleMoves = new ArrayList<>();

    for (int i = 0; i < NEIGHBOR_DISTANCES.length; i++) {
      int[] neighborPosition = calculateNeighborPosition(i, penguin.getRow(), penguin.getCol());
      if (neighborPosition == null) {
        continue;
      }

      while (neighborPosition != null && getUnoccupiedPositionByPosition(neighborPosition)) {
        possibleMoves.add(new Move(penguin.getPosition(), neighborPosition));
        if (calculateNeighborPosition(i, neighborPosition[0], neighborPosition[1]) == null) {
          break;
        }
        neighborPosition = calculateNeighborPosition(i, neighborPosition[0], neighborPosition[1]);
      }
    }
    return possibleMoves;
  }

  public boolean isValidPenguin(int[] position) {
    return getPenguinByPosition(position) != null
        && getCurrentPlayer().getPenguinColor().equals(getPenguinByPosition(position).getColor());
  }

  public boolean hasPenguinLegalMoves(Penguin penguin) {
    return getAllLegalMovesForPenguin(penguin).size() > 0;
  }

  public boolean hasPenguinLegalMoves(int row, int col) {
    return getAllLegalMovesForPenguin(getPenguinByPosition(row, col)).size() > 0;
  }

  // TODO optimize?
  public List<Move> getAllLegalMovesForPlayer(Player player) {
    List<Move> possibleMoves = new ArrayList<>();
    for (Penguin penguin : getAllPenguinsForPlayer(player)) {
      if (penguin.isOnBoard()) {
        // TODO (better way to achieve same result?)
        possibleMoves.addAll(getAllLegalMovesForPenguin(penguin));
      }
    }
    return possibleMoves;
  }

  public List<Move> getAllLegalMovesForCurrentPlayer() {
    return getAllLegalMovesForPlayer(getCurrentPlayer());
  }

  public boolean hasPlayerLegalMoves(Player player) {
    return getAllLegalMovesForPlayer(player).size() > 0;
  }

  // return index of the winning player (-1 if tied)
  public int getWinnerIndex() {
    int maxFishCount = players[0].getCollectedFishCount();
    int winnerIndex = 0;
    for (int i = 1; i < players.length; i++) {
      if (players[i].getCollectedFishCount() > maxFishCount) {
        maxFishCount = players[i].getCollectedFishCount();
        winnerIndex = i;
      }
    }

    if (winnerIndex == 0) {
      List<Player> tiedPlayers = new ArrayList<>();
      tiedPlayers.add(players[0]);
      for (int i = 1; i < players.length; i++) {
        if (players[i].getCollectedFishCount() == players[0].getCollectedFishCount()) {
          tiedPlayers.add(players[i]);
        }
      }
      if (tiedPlayers.size() > 1) {
        winnerIndex = -1;
      }
    }

    return winnerIndex;
  }

  // return null if there is a tie
  public Player getWinner() {
    return (getWinnerIndex() != -1) ? players[getWinnerIndex()] : null;
  }

  public void printBoard() {
    StringBuilder str =
        new StringBuilder(
            "\n  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n");

    for (int i = 0; i < ROW_COUNT; i++) {
      str.append(i).append(" | ");
      for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
        String spacingLeft;
        String spacingRight;
        if (i % 2 == 0) {
          spacingLeft = (j < 10) ? "  " : "   ";
          spacingRight = (j < 9) ? " " : "  ";
        } else {
          spacingLeft = (j < 10) ? "" : " ";
          spacingRight = (j < 9) ? "   " : "    ";
        }
        String tileStr = "";
        if (getFishCountByPosition(i, j) == 0) {
          tileStr = "X";
        } else if (unoccupiedPositions[getIndexFromPosition(i, j)]) {
          tileStr = String.valueOf(getFishCountByPosition(i, j));
        } else {
          tileStr = getPenguinByPosition(i, j).toString();
        }

        str.append(spacingLeft).append(tileStr).append(spacingRight);
      }
      str.append("\n");
    }

    System.out.println(str);
  }
}
