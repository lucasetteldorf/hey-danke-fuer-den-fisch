package game;

import java.util.Scanner;

public class Game {
  private static final Scanner scanner = new Scanner(System.in);

  private static GameBoard board;
  private static Player[] players;
  private static int currentPlayerIndex;

  private static void initializeGame(int playersCount) {
    board = new GameBoard();
    initializePlayers(playersCount);
    currentPlayerIndex = 0;
  }

  private static void initializePlayers(int playersCount) {
    players = new Player[playersCount];
    for (int i = 0; i < players.length; i++) {
      System.out.print("Player " + (i + 1) + " name: ");
      String playerName = scanner.next();
      players[i] = new Player(playerName, playersCount, PenguinColor.values()[i]);
    }
  }

  private static boolean playersHavePenguins() {
    for (Player p : players) {
      if (p.hasPenguins()) {
        return true;
      }
    }

    return false;
  }

  private static void setNextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
  }

  private static void startPlacementPhase() {
    System.out.println("Begin placement...");
    System.out.println(board);

    while (playersHavePenguins()) {
      System.out.print(players[currentPlayerIndex].getName() + ", enter index to place penguin: ");
      int index = scanner.nextInt();
      board.placePenguin(
          players[currentPlayerIndex].getCurrentPenguin(),
          indexToTileIndices(index)[0],
          indexToTileIndices(index)[1]);
      System.out.println(board);
      setNextPlayer();
    }
  }

  private static int[] indexToTileIndices(int index) {
    int currentIndex = 1;

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 7 + (i % 2); j++) {
        if (index == currentIndex) {
          return new int[] {i, j};
        }

        currentIndex++;
      }
    }

    return new int[] {-1, -1};
  }

  private static void startMovementPhase() {
    System.out.println("Begin movement...");
    System.out.println(board);
  }

  public static void start(int playersCount) {
    initializeGame(playersCount);
    startPlacementPhase();
    startMovementPhase();
  }
}
