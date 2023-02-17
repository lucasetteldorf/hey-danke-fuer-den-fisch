package game;

import utility.InputReader;

public class Game {
  private static Game game;

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

  private Player[] initializePlayers() {
    int playerCount = InputReader.getPlayerCount();
    Player[] players = new Player[playerCount];
    for (int i = 0; i < players.length; i++) {
      players[i] = new Player(InputReader.getPlayerName(i));
      players[i].setPenguins(
          initializePenguins(InputReader.getPenguinColor(i), playerCount, players[i]));
    }
    this.currentPlayer = players[currentPlayerIndex];

    return players;
  }

  private Penguin[] initializePenguins(String color, int playerCount, Player player) {
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
      penguins[i] = new Penguin(color, player);
    }

    return penguins;
  }

  private void updateCurrentPlayer() {
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
    this.currentPlayer = this.players[currentPlayerIndex];
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
    InputReader.nextLine();

    do {
      int[] placementCoordinates;
      do {
        placementCoordinates = InputReader.getPlacementCoordinates(this.currentPlayer);
      } while (!this.board.placePenguin(
          currentPlayer.getCurrentPenguin(), placementCoordinates[0], placementCoordinates[1]));
      currentPlayer.updateCurrentPenguinIndex();
      updateCurrentPlayer();

      System.out.println(this.board);
    } while (canPenguinsBePlaced());
  }

  public void startMovementPhase() {}
}
