package game;

import game.players.Player;
import java.util.*;
import utility.RandomUtility;

public class GameBoard {
  private static final int TILE_COUNT = 60;
  private static final int ROW_COUNT = 8;
  private static final int COL_COUNT = 15;
  private static final int[][] TILE_POSITIONS =
      new int[][] {
        {0, 1}, {0, 3}, {0, 5}, {0, 7}, {0, 9}, {0, 11}, {0, 13}, {1, 0}, {1, 2}, {1, 4}, {1, 6},
        {1, 8}, {1, 10}, {1, 12}, {1, 14}, {2, 1}, {2, 3}, {2, 5}, {2, 7}, {2, 9}, {2, 11}, {2, 13},
        {3, 0}, {3, 2}, {3, 4}, {3, 6}, {3, 8}, {3, 10}, {3, 12}, {3, 14}, {4, 1}, {4, 3}, {4, 5},
        {4, 7}, {4, 9}, {4, 11}, {4, 13}, {5, 0}, {5, 2}, {5, 4}, {5, 6}, {5, 8}, {5, 10}, {5, 12},
        {5, 14}, {6, 1}, {6, 3}, {6, 5}, {6, 7}, {6, 9}, {6, 11}, {6, 13}, {7, 0}, {7, 2}, {7, 4},
        {7, 6}, {7, 8}, {7, 10}, {7, 12}, {7, 14}
      };
  private static final int[][] TILE_INDICES =
      new int[][] {
        {-1, 0, -1, 1, -1, 2, -1, 3, -1, 4, -1, 5, -1, 6},
        {7, -1, 8, -1, 9, -1, 10, -1, 11, -1, 12, -1, 13, -1, 14},
        {-1, 15, -1, 16, -1, 17, -1, 18, -1, 19, -1, 20, -1, 21},
        {22, -1, 23, -1, 24, -1, 25, -1, 26, -1, 27, -1, 28, -1, 29},
        {-1, 30, -1, 31, -1, 32, -1, 33, -1, 34, -1, 35, -1, 36},
        {37, -1, 38, -1, 39, -1, 40, -1, 41, -1, 42, -1, 43, -1, 44},
        {-1, 45, -1, 46, -1, 47, -1, 48, -1, 49, -1, 50, -1, 51},
        {52, -1, 53, -1, 54, -1, 55, -1, 56, -1, 57, -1, 58, -1, 59}
      };
  // order: top right, right, bottom right, bottom left, left, top left
  private static final int[][] TILE_NEIGHBOR_DISTANCES =
      new int[][] {{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};

  // 0 = tile removed from board
  private final int[] fishCounts;
  private final boolean[] unoccupiedPositions;
  private final int[] penguinPositionIndices;
  private final Player[] players;
  private final int totalPenguinCount;
  private int currentPenguinIndex;
  private int currentPlayerIndex;

  public GameBoard() {
    this.fishCounts = initGameBoard();
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    Arrays.fill(unoccupiedPositions, true);
    this.penguinPositionIndices = null;
    this.totalPenguinCount = 0;
    this.players = null;
  }

  public GameBoard(Player[] players) {
    this.fishCounts = initGameBoard();
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    Arrays.fill(unoccupiedPositions, true);
    this.totalPenguinCount = (players.length == 3) ? 9 : 8;
    this.penguinPositionIndices = new int[totalPenguinCount];
    Arrays.fill(penguinPositionIndices, -1);
    this.players = players;
  }

  public GameBoard(int[] fishCounts) {
    this.fishCounts = Arrays.copyOf(fishCounts, fishCounts.length);
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    Arrays.fill(unoccupiedPositions, true);
    this.penguinPositionIndices = null;
    this.totalPenguinCount = 0;
    this.players = null;
  }

  // copy constructor
  public GameBoard(GameBoard board) {
    this.fishCounts = Arrays.copyOf(board.fishCounts, board.fishCounts.length);
    this.unoccupiedPositions =
        Arrays.copyOf(board.unoccupiedPositions, board.unoccupiedPositions.length);
    this.penguinPositionIndices =
        Arrays.copyOf(board.penguinPositionIndices, board.penguinPositionIndices.length);
    this.totalPenguinCount = board.totalPenguinCount;
    this.players = new Player[board.players.length];
    for (int i = 0; i < this.players.length; i++) {
      this.players[i] = new Player(board.players[i]);
    }
    this.currentPenguinIndex = board.currentPenguinIndex;
    this.currentPlayerIndex = board.currentPlayerIndex;
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

  public static int getTileIndexFromPosition(int row, int col) {
    return TILE_INDICES[row][col];
  }

  public static int getTileIndexFromPosition(int[] position) {
    return getTileIndexFromPosition(position[0], position[1]);
  }

  public static int[] getPositionFromTileIndex(int index) {
    return TILE_POSITIONS[index];
  }

  private static int[] calculateNeighborPosition(int direction, int row, int col) {
    int neighborRow = row + TILE_NEIGHBOR_DISTANCES[direction][0];
    int neighborCol = col + TILE_NEIGHBOR_DISTANCES[direction][1];
    if (neighborRow >= 0 && neighborRow <= 7 && neighborCol >= 0 && neighborCol <= 14) {
      return getPositionFromTileIndex(getTileIndexFromPosition(neighborRow, neighborCol));
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
      int randomIndex = RandomUtility.getRandomIndex(fishCounts.size());
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

  public int getPenguinIndexFromPosition(int[] position) {
    for (int i = 0; i < penguinPositionIndices.length; i++) {
      if (Arrays.equals(getPositionFromTileIndex(penguinPositionIndices[i]), position)) {
        return i;
      }
    }
    return -1;
  }

  public int getPenguinIndexFromPosition(int row, int col) {
    return getPenguinIndexFromPosition(new int[] {row, col});
  }

  public List<int[]> getAllPenguinPositionsForPlayer(Player player) {
    List<int[]> playerPenguinPositions = new ArrayList<>();
    for (int i = 0; i < penguinPositionIndices.length; i++) {
      if (player.ownsPenguinAtIndex(i)) {
        playerPenguinPositions.add(getPositionFromTileIndex(penguinPositionIndices[i]));
      }
    }
    return playerPenguinPositions;
  }

  public int getFishCountByPosition(int[] position) {
    return fishCounts[getTileIndexFromPosition(position)];
  }

  public int getFishCountByPosition(int row, int col) {
    return getFishCountByPosition(new int[] {row, col});
  }

  public boolean getUnoccupiedPositionByPosition(int[] position) {
    return unoccupiedPositions[getTileIndexFromPosition(position)];
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
    return unoccupiedPositions[getTileIndexFromPosition(position)]
        && fishCounts[getTileIndexFromPosition(position)] == 1;
  }

  public boolean isLegalPlacementIndex(int index) {
    return unoccupiedPositions[index] && fishCounts[index] == 1;
  }

  public List<int[]> getAllLegalPlacementPositions() {
    List<int[]> legalPlacementPositions = new ArrayList<>();
    for (int i = 0; i < TILE_COUNT; i++) {
      if (isLegalPlacementPosition(getPositionFromTileIndex(i))) {
        legalPlacementPositions.add(getPositionFromTileIndex(i));
      }
    }
    return legalPlacementPositions;
  }

  public void placePenguin(int row, int col) {
    placePenguin(getCurrentPlayer(), row, col);
  }

  public void placePenguin(Player player, int row, int col) {
    int placementIndex = getTileIndexFromPosition(row, col);
    if (player.canPlacePenguin() && isLegalPlacementIndex(placementIndex)) {
      unoccupiedPositions[placementIndex] = false;
      penguinPositionIndices[currentPenguinIndex] = getTileIndexFromPosition(row, col);
      player.addPenguinIndex(currentPenguinIndex);
      currentPenguinIndex++;
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
    if (hasPenguinLegalMoves(move.getOldPosition())
        && isLegalMove(move.getOldPosition(), move.getNewPosition())
        && player.ownsPenguinAtIndex(getPenguinIndexFromPosition(move.getOldPosition()))) {
      player.updateCollectedTileCount();
      player.updateCollectedFishCount(fishCounts[getTileIndexFromPosition(move.getOldPosition())]);
      unoccupiedPositions[getTileIndexFromPosition(move.getOldPosition())] = false;
      unoccupiedPositions[getTileIndexFromPosition(move.getNewPosition())] = false;
      fishCounts[getTileIndexFromPosition(move.getOldPosition())] = 0;
      penguinPositionIndices[getPenguinIndexFromPosition(move.getOldPosition())] =
          getTileIndexFromPosition(move.getNewPosition());

      // TODO working as intended?
      getCurrentPlayer().updateMoveCount();

      updateCurrentPlayer();
    }
  }

  public boolean isLegalMove(int[] oldPosition, int[] newPosition) {
    if (!Arrays.equals(oldPosition, newPosition)
        && unoccupiedPositions[getTileIndexFromPosition(newPosition)]) {
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
    for (int[] position : getAllPenguinPositionsForPlayer(getCurrentPlayer())) {
      unoccupiedPositions[getTileIndexFromPosition(position)] = false;
      getCurrentPlayer().updateCollectedTileCount();
      getCurrentPlayer().updateCollectedFishCount(fishCounts[getTileIndexFromPosition(position)]);
      fishCounts[getTileIndexFromPosition(position)] = 0;
    }
  }

  public List<Move> getAllLegalMovesForPenguin(int index) {
    return getAllLegalMovesForPenguin(getPositionFromTileIndex(penguinPositionIndices[index]));
  }

  public List<Move> getAllLegalMovesForPenguin(int[] position) {
    // TODO change to LinkedList?
    List<Move> possibleMoves = new ArrayList<>();

    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
      int[] neighborPosition = calculateNeighborPosition(i, position[0], position[1]);
      if (neighborPosition == null) {
        continue;
      }

      while (neighborPosition != null && getUnoccupiedPositionByPosition(neighborPosition)) {
        possibleMoves.add(new Move(position, neighborPosition));
        if (calculateNeighborPosition(i, neighborPosition[0], neighborPosition[1]) == null) {
          break;
        }
        neighborPosition = calculateNeighborPosition(i, neighborPosition[0], neighborPosition[1]);
      }
    }
    return possibleMoves;
  }

  public boolean isValidPenguin(int[] position) {
    int penguinIndex = getPenguinIndexFromPosition(position);
    if (penguinIndex == -1) {
      return false;
    }
    return penguinPositionIndices[penguinIndex] != -1
        && getCurrentPlayer().ownsPenguinAtIndex(penguinIndex);
  }

  public boolean hasPenguinLegalMoves(int index) {
    return hasPenguinLegalMoves(getPositionFromTileIndex(penguinPositionIndices[index]));
  }

  public boolean hasPenguinLegalMoves(int[] position) {
    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
      int[] neighborPosition = calculateNeighborPosition(i, position[0], position[1]);
      if (neighborPosition != null && getUnoccupiedPositionByPosition(neighborPosition)) {
        return true;
      }
    }
    return false;
  }

  public List<Move> getAllLegalMovesForPlayer(Player player) {
    // TODO change to LinkedList?
    List<Move> possibleMoves = new ArrayList<>();
    for (int index : player.getPenguinIndices()) {
      if (penguinPositionIndices[index] != -1) {
        possibleMoves.addAll(getAllLegalMovesForPenguin(index));
      }
    }
    return possibleMoves;
  }

  public List<Move> getAllLegalMovesForCurrentPlayer() {
    return getAllLegalMovesForPlayer(getCurrentPlayer());
  }

  public boolean hasPlayerLegalMoves(Player player) {
    for (int penguinIndex : player.getPenguinIndices()) {
      if (hasPenguinLegalMoves(penguinIndex)) {
        return true;
      }
    }
    return false;
  }

  // returns index of the winning player (-1 if tied)
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

  // returns null if there is a tie
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
        String tileStr;
        if (getFishCountByPosition(i, j) == 0) {
          tileStr = "X";
        } else if (unoccupiedPositions[getTileIndexFromPosition(i, j)]) {
          tileStr = String.valueOf(getFishCountByPosition(i, j));
        } else {
          tileStr = " ";
          int index = getPenguinIndexFromPosition(i, j);
          for (Player player : players) {
            if (player.ownsPenguinAtIndex(index)) {
              tileStr = player.getPenguinColor();
            }
          }
        }

        str.append(spacingLeft).append(tileStr).append(spacingRight);
      }
      str.append("\n");
    }

    System.out.println(str);
  }
}
