package game;

public class Game {
  private static Game game;
  private static GameBoard board;
  private static Player[] players;
  private static int currentPlayerIndex;

  public static void initialize(int playersCount) {
    board = new GameBoard();
    players = new Player[playersCount];
    // TODO init players with names
    for (int i = 0; i < players.length; i++) {
      players[i] = new Player(String.valueOf(i + 1), playersCount, PenguinColor.values()[i]);
    }
  }

  public static void start(int playersCount) {
    initialize(playersCount);

    System.out.print(board);
  }
}
