package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
  private static Game game;
  private final Scanner scanner = new Scanner(System.in);
  private final ArrayList<String> AVAILABLE_COLORS =
      new ArrayList<>(Arrays.asList("B", "R", "G", "Y"));
  private final GameBoard board;
  private final Player[] players;
  private int currentPlayerIndex;
  private Player currentPlayer;

  private Game() {
    this.board = new GameBoard();
    this.players = initializePlayers();
  }

  public static Game getInstance() {
    if (game == null) {
      game = new Game();
    }

    return game;
  }

  public static void main(String[] args) {
    Game game = Game.getInstance();
    game.startPlacementPhase();
  }

  private int getPlayerCount() {
    int playerCount;
    do {
      System.out.print("Number of players (2-4): ");
      playerCount = scanner.nextInt();
    } while (playerCount < 2 || playerCount > 4);

    return playerCount;
  }

  private String getPlayerName(int playerIndex) {
    System.out.print("Player " + (playerIndex + 1) + " name: ");
    return scanner.next().trim();
  }

  private String getPenguinColor(int playerIndex) {
    String penguinColor;
    do {
      System.out.print(
          "Player "
              + (playerIndex + 1)
              + " penguin color (B = blue, R = red, G = green, Y = yellow): ");
      penguinColor = scanner.next().trim();
    } while (!AVAILABLE_COLORS.contains(penguinColor));
    AVAILABLE_COLORS.remove(penguinColor);

    return penguinColor;
  }

  private Player[] initializePlayers() {
    int playerCount = getPlayerCount();
    Player[] players = new Player[playerCount];
    for (int i = 0; i < players.length; i++) {
      players[i] =
          new Player(getPlayerName(i), initializePenguins(getPenguinColor(i), playerCount));
    }
    this.currentPlayer = players[currentPlayerIndex];

    return players;
  }

  private Penguin[] initializePenguins(String color, int playerCount) {
    int penguinCount = 0;
    switch (playerCount) {
      case 2:
        penguinCount = 4;
        break;
      case 3:
        penguinCount = 3;
        break;
      case 4:
        penguinCount = 2;
        break;
      default:
        break;
    }

    Penguin[] penguins = new Penguin[penguinCount];
    for (int i = 0; i < penguins.length; i++) {
      penguins[i] = new Penguin(color);
    }

    return penguins;
  }

  private void updateCurrentPlayer() {
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
    this.currentPlayer = this.players[currentPlayerIndex];
  }

  private int[] getPlacementCoordinates() {
    System.out.print(
        this.currentPlayer + ": Coordinates to place penguin (separated by a space): ");
    String input = scanner.nextLine();
    String[] coordinates = input.split(" ");
    return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
  }

  private boolean canPenguinsBePlaced() {
    for (Player player : this.players) {
      if (player.hasUnplacedPenguins()) {
        return true;
      }
    }

    return false;
  }

  public void startPlacementPhase() {
    System.out.println(this.board);
    scanner.nextLine();

    do {
      int[] placementCoordinates;
      do {
        placementCoordinates = getPlacementCoordinates();
      } while (!this.board.placePenguin(
          currentPlayer.getCurrentPenguin(), placementCoordinates[0], placementCoordinates[1]));
      currentPlayer.updateCurrentPenguinIndex();
      updateCurrentPlayer();

      System.out.println(this.board);
    } while (canPenguinsBePlaced());
  }

  public void startMovementPhase() {}
}
