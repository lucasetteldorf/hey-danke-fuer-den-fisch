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
  }

  public static void start(int playersCount) {
    initialize(playersCount);

    System.out.print(board);
  }
}
