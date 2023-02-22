package game;

import utility.InputReader;

public class Game {
  private static Game game;

  private final GameBoard board;
  private final HumanPlayer[] humanPlayers;
  private int currentPlayerIndex;
  private HumanPlayer currentHumanPlayer;

  private Game() {
    this.board = new GameBoard();
    this.humanPlayers = initializePlayers();
  }

  public static Game getInstance() {
    if (game == null) {
      game = new Game();
    }

    return game;
  }

  private HumanPlayer[] initializePlayers() {
    int playerCount = InputReader.getPlayerCount();
    HumanPlayer[] humanPlayers = new HumanPlayer[playerCount];
    for (int i = 0; i < humanPlayers.length; i++) {
      humanPlayers[i] = new HumanPlayer(InputReader.getPlayerName(i));
      humanPlayers[i].setPenguins(
          initializePenguins(InputReader.getPenguinColor(i), playerCount, humanPlayers[i]));
    }
    this.currentHumanPlayer = humanPlayers[currentPlayerIndex];

    return humanPlayers;
  }

  private Penguin[] initializePenguins(String color, int playerCount, HumanPlayer humanPlayer) {
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
      penguins[i] = new Penguin(color, humanPlayer);
    }

    return penguins;
  }

  private void updateCurrentPlayerPlacementPhase() {

    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.humanPlayers.length;
    this.currentHumanPlayer = this.humanPlayers[currentPlayerIndex];
  }

  private void updateCurrentPlayerMovementPhase() {
    do {
      this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.humanPlayers.length;
      this.currentHumanPlayer = this.humanPlayers[currentPlayerIndex];

      // TODO seems to be working
      if (!areLegalMovesPossible()) {
        break;
      }
    } while (!this.currentHumanPlayer.hasPenguinsToMove(this.board));
  }

  private boolean canPenguinsBePlaced() {
    for (HumanPlayer humanPlayer : this.humanPlayers) {
      if (humanPlayer.hasUnplacedPenguins()) {
        return true;
      }
    }

    return false;
  }

  public void startPlacementPhase() {
    System.out.println("\nStart placement...");
    System.out.println(this.board);

    do {
      int[] placementCoordinates;
      do {
        placementCoordinates = InputReader.getPlacementCoordinates(this.currentHumanPlayer);
      } while (!this.board.placePenguin(
          currentHumanPlayer.getCurrentPenguin(), placementCoordinates[0], placementCoordinates[1]));
      currentHumanPlayer.updateCurrentPenguinIndex();
      updateCurrentPlayerPlacementPhase();

      System.out.println(this.board);
    } while (canPenguinsBePlaced());
  }

  private boolean areLegalMovesPossible() {
    for (HumanPlayer humanPlayer : this.humanPlayers) {
      if (humanPlayer.hasPenguinsToMove(this.board)) {
        return true;
      }
    }

    return false;
  }

  public void startMovementPhase() {
    System.out.println("Start movement...");
    System.out.println(this.board);

    do {
      int[] penguinCoordinates;
      int penguinIndex;
      do {
        penguinCoordinates = InputReader.getPenguinCoordinates(this.currentHumanPlayer);
        penguinIndex =
            this.currentHumanPlayer.penguinIndexAtPosition(penguinCoordinates[0], penguinCoordinates[1]);
      } while (penguinIndex == -1);

      Penguin penguinToMove = this.currentHumanPlayer.getPenguin(penguinIndex);
      int[] movementCoordinates;
      do {
        movementCoordinates = InputReader.getMovementCoordinates(this.currentHumanPlayer);
      } while (!this.board.movePenguin(
          penguinToMove, movementCoordinates[0], movementCoordinates[1]));
      updateCurrentPlayerMovementPhase();

      System.out.println(board);
    } while (areLegalMovesPossible());

    System.out.println("Game finished...");

    printAllScores();

    printWinner();
  }

  private void printAllScores() {
    for (HumanPlayer humanPlayer : this.humanPlayers) {
      System.out.println(humanPlayer.getScore());
    }
  }

  // TODO
  private void printWinner() {
    int maxFishCount = this.humanPlayers[0].getCollectedFishCount();
    int winnerIndex = 0;
    for (int i = 1; i < this.humanPlayers.length; i++) {
      if (this.humanPlayers[i].getCollectedFishCount() > maxFishCount) {
        maxFishCount = this.humanPlayers[i].getCollectedFishCount();
        winnerIndex = i;
      }
    }
    System.out.println("Winner: " + this.humanPlayers[winnerIndex].getName());
  }
}
