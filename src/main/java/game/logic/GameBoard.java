package game.logic;

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
        {0, 1, 2, 3, 4, 5, 6},
        {7, 8, 9, 10, 11, 12, 13, 14},
        {15, 16, 17, 18, 19, 20, 21},
        {22, 23, 24, 25, 26, 27, 28, 29},
        {30, 31, 32, 33, 34, 35, 36},
        {37, 38, 39, 40, 41, 42, 43, 44},
        {45, 46, 47, 48, 49, 50, 51},
        {52, 53, 54, 55, 56, 57, 58, 59}
      };
  // order: top right, right, bottom right, bottom left, left, top left
  private static final int[][] TILE_NEIGHBOR_DISTANCES =
      new int[][] {{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};

  // 0 = tile removed from board
  private final int[] fishCounts;
  private final boolean[] unoccupiedPositions;
  private final int[] penguinPositionIndices;
  private final boolean[] penguinMovementPossible;
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
    this.penguinMovementPossible = null;
  }

  public GameBoard(Player[] players) {
    this.fishCounts = initGameBoard();
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    Arrays.fill(unoccupiedPositions, true);
    this.totalPenguinCount = (players.length == 3) ? 9 : 8;
    this.penguinPositionIndices = new int[totalPenguinCount];
    Arrays.fill(penguinPositionIndices, -1);
    this.penguinMovementPossible = new boolean[totalPenguinCount];
    Arrays.fill(penguinMovementPossible, true);
    this.players = players;
  }

  public GameBoard(Player[] players, int[] fishCounts) {
    this.fishCounts = Arrays.copyOf(fishCounts, fishCounts.length);
    this.unoccupiedPositions = new boolean[TILE_COUNT];
    Arrays.fill(unoccupiedPositions, true);
    this.totalPenguinCount = (players.length == 3) ? 9 : 8;
    this.penguinPositionIndices = new int[totalPenguinCount];
    Arrays.fill(penguinPositionIndices, -1);
    this.penguinMovementPossible = new boolean[totalPenguinCount];
    Arrays.fill(penguinMovementPossible, true);
    this.players = players;
  }

  // copy constructor
  public GameBoard(GameBoard board) {
    this.fishCounts = Arrays.copyOf(board.fishCounts, board.fishCounts.length);
    this.unoccupiedPositions =
        Arrays.copyOf(board.unoccupiedPositions, board.unoccupiedPositions.length);
    this.penguinPositionIndices =
        Arrays.copyOf(board.penguinPositionIndices, board.penguinPositionIndices.length);
    this.penguinMovementPossible =
        Arrays.copyOf(board.penguinMovementPossible, board.penguinMovementPossible.length);
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
    return TILE_INDICES[row][col / 2];
  }

  public static int getTileIndexFromPosition(int[] position) {
    return getTileIndexFromPosition(position[0], position[1]);
  }

  public static int[] getPositionFromTileIndex(int tileIndex) {
    return TILE_POSITIONS[tileIndex];
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
      Player currentPlayer;
      do {
        currentPlayer = getCurrentPlayer();

        if (currentPlayer.arePenguinsRemovedFromBoard()) {
          currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
          continue;
        }

        if (!hasPlayerLegalMoves(currentPlayer) && !currentPlayer.arePenguinsRemovedFromBoard()) {
          removeAllPenguinsAndTiles();
          currentPlayer.setPenguinsRemovedFromBoard(true);
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
      } while (!hasPlayerLegalMoves(getCurrentPlayer()) && !isMovementPhaseOver());
    }
  }

  public int getPenguinIndexFromPosition(int[] position) {
    return getPenguinIndexFromPosition(position[0], position[1]);
  }

  public int getPenguinIndexFromPosition(int row, int col) {
    for (int i = 0; i < penguinPositionIndices.length; i++) {
      if (getTileIndexFromPosition(row, col) == penguinPositionIndices[i]) {
        return i;
      }
    }
    return -1;
  }

  public List<int[]> getAllPenguinPositionsForPlayer(Player player) {
    List<int[]> playerPenguinPositions = new ArrayList<>();
    for (int i = 0; i < penguinPositionIndices.length; i++) {
      if (penguinPositionIndices[i] != -1 && player.ownsPenguinAtIndex(i)) {
        playerPenguinPositions.add(getPositionFromTileIndex(penguinPositionIndices[i]));
      }
    }
    return playerPenguinPositions;
  }

  public List<int[]> getAllPenguinPositionsForOpponents(Player player) {
    List<int[]> opponentPenguinPositions = new ArrayList<>();
    for (int i = 0; i < penguinPositionIndices.length; i++) {
      if (penguinPositionIndices[i] != -1 && !player.ownsPenguinAtIndex(i)) {
        opponentPenguinPositions.add(getPositionFromTileIndex(penguinPositionIndices[i]));
      }
    }
    return opponentPenguinPositions;
  }

  public int getThreeFishTilesForAllPenguins(List<int[]> penguinPositions) {
    int threeFishTilesCount = 0;
    HashSet<Integer> uniqueTiles = new HashSet<>();
    for (int[] penguinPosition : penguinPositions) {
      for (Move move : getAllLegalMovesForPenguin(penguinPosition)) {
        int[] newPosition = move.getNewPosition();
        int newIndex = GameBoard.getTileIndexFromPosition(newPosition);
        if (uniqueTiles.contains(newIndex)) {
          continue;
        }
        if (getFishCountByPosition(newPosition) == 3) {
          threeFishTilesCount++;
          uniqueTiles.add(newIndex);
        }
      }
    }
    return threeFishTilesCount;
  }

  public int getReachableFishCountForAllPenguins(List<int[]> penguinPositions) {
    int reachableFish = 0;
    HashSet<Integer> uniqueTiles = new HashSet<>();
    for (int[] penguinPosition : penguinPositions) {
      for (Move move : getAllLegalMovesForPenguin(penguinPosition)) {
        int[] newPosition = move.getNewPosition();
        int newIndex = GameBoard.getTileIndexFromPosition(newPosition);
        if (uniqueTiles.contains(newIndex)) {
          continue;
        }
        uniqueTiles.add(newIndex);
        reachableFish += getFishCountByPosition(newPosition);
      }
    }
    return reachableFish;
  }

  public int getReachableTilesForAllPenguins(List<int[]> penguinPositions) {
    int reachableTiles = 0;
    HashSet<Integer> uniqueTiles = new HashSet<>();
    for (int[] penguinPosition : penguinPositions) {
      for (Move move : getAllLegalMovesForPenguin(penguinPosition)) {
        int newIndex = GameBoard.getTileIndexFromPosition(move.getNewPosition());
        if (uniqueTiles.contains(newIndex)) {
          continue;
        }
        uniqueTiles.add(newIndex);
        reachableTiles++;
      }
    }
    return reachableTiles;
  }

  public int getFishCountByPosition(int row, int col) {
    return fishCounts[getTileIndexFromPosition(row, col)];
  }

  public int getFishCountByPosition(int[] position) {
    return getFishCountByPosition(position[0], position[1]);
  }

  public boolean getUnoccupiedPositionByPosition(int[] position) {
    return unoccupiedPositions[getTileIndexFromPosition(position)];
  }

  public boolean isPlacementPhaseOver() {
    return currentPenguinIndex == totalPenguinCount;
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
      penguinPositionIndices[currentPenguinIndex] = placementIndex;
      player.addPenguinIndex(currentPenguinIndex);
      currentPenguinIndex++;
      player.updatePlacedPenguinCount();
      getCurrentPlayer().updateTotalTurnCount();
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
    int[] oldPosition = move.getOldPosition();
    int[] newPosition = move.getNewPosition();

    if (hasPenguinLegalMoves(oldPosition)
        && isLegalMove(oldPosition, newPosition)
        && player.ownsPenguinAtIndex(getPenguinIndexFromPosition(oldPosition))) {
      player.updateCollectedFishCount(fishCounts[getTileIndexFromPosition(oldPosition)]);
      unoccupiedPositions[getTileIndexFromPosition(oldPosition)] = false;
      unoccupiedPositions[getTileIndexFromPosition(newPosition)] = false;
      fishCounts[getTileIndexFromPosition(oldPosition)] = 0;
      penguinPositionIndices[getPenguinIndexFromPosition(oldPosition)] =
          getTileIndexFromPosition(newPosition);
      getCurrentPlayer().updateTotalTurnCount();
      updateCurrentPlayer();
    }
  }

  public boolean isLegalMove(int[] oldPosition, int[] newPosition) {
    if (getTileIndexFromPosition(oldPosition) != getTileIndexFromPosition(newPosition)
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

      if (i == colDiff - 1
          && getTileIndexFromPosition(neighborPosition) != getTileIndexFromPosition(newPosition)) {
        return false;
      }
    }

    return true;
  }

  public void removeAllPenguinsAndTiles() {
    Player currentPlayer = getCurrentPlayer();

    for (int[] position : getAllPenguinPositionsForPlayer(currentPlayer)) {
      int tileIndex = getTileIndexFromPosition(position);
      unoccupiedPositions[tileIndex] = false;
      currentPlayer.updateCollectedFishCount(fishCounts[getTileIndexFromPosition(position)]);
      fishCounts[tileIndex] = 0;
    }
  }

  public List<Move> getAllLegalMovesForPenguin(int index) {
    return getAllLegalMovesForPenguin(getPositionFromTileIndex(penguinPositionIndices[index]));
  }

  public List<Move> getAllLegalMovesForPenguin(int[] position) {
    List<Move> possibleMoves = new ArrayList<>();

    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {

      int[] neighborPosition = calculateNeighborPosition(i, position[0], position[1]);
      if (neighborPosition == null) {
        continue;
      }

      while (getUnoccupiedPositionByPosition(neighborPosition)) {
        possibleMoves.add(new Move(position, neighborPosition));
        int[] newNeighborPosition =
            calculateNeighborPosition(i, neighborPosition[0], neighborPosition[1]);
        if (newNeighborPosition == null) {
          break;
        }
        neighborPosition = newNeighborPosition;
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

  public boolean hasPenguinLegalMoves(int row, int col) {
    if (getPenguinIndexFromPosition(row, col) == -1) {
      return false;
    }

    if (!penguinMovementPossible[getPenguinIndexFromPosition(row, col)]) {
      return false;
    }

    for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
      int[] neighborPosition = calculateNeighborPosition(i, row, col);
      if (neighborPosition != null && getUnoccupiedPositionByPosition(neighborPosition)) {
        return true;
      }
    }

    penguinMovementPossible[getPenguinIndexFromPosition(row, col)] = false;

    return false;
  }

  public boolean hasPenguinLegalMoves(int penguinIndex) {
    return hasPenguinLegalMoves(getPositionFromTileIndex(penguinPositionIndices[penguinIndex]));
  }

  public boolean hasPenguinLegalMoves(int[] position) {
    return hasPenguinLegalMoves(position[0], position[1]);
  }

  public List<Move> getAllLegalMovesForPlayer(Player player) {
    List<Move> possibleMoves = new ArrayList<>();
    for (int index : player.getPenguinIndices()) {
      if (index != -1 && penguinPositionIndices[index] != -1) {
        possibleMoves.addAll(getAllLegalMovesForPenguin(index));
      }
    }
    return possibleMoves;
  }

  public List<Move> getAllLegalMovesForCurrentPlayer() {
    return getAllLegalMovesForPlayer(getCurrentPlayer());
  }

  public boolean hasPlayerLegalMoves(Player player) {
    if (player.arePenguinsRemovedFromBoard()) {
      return false;
    }
    for (int penguinIndex : player.getPenguinIndices()) {
      if (hasPenguinLegalMoves(penguinIndex)) {
        return true;
      }
    }
    return false;
  }

  // returns index of the winning player (-1 if tied)
  public int getWinnerIndex() {
    int maxFishCount = Integer.MIN_VALUE;
    int winnerIndex = -2;
    int tiedPlayerCount = 1;
    for (int i = 0; i < players.length; i++) {
      if (players[i].getCollectedFishCount() > maxFishCount) {
        maxFishCount = players[i].getCollectedFishCount();
        winnerIndex = i;
        tiedPlayerCount = 1;
      } else if (players[i].getCollectedFishCount() == maxFishCount) {
        tiedPlayerCount++;
      }
    }

    if (tiedPlayerCount > 1) {
      winnerIndex = -1;
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
