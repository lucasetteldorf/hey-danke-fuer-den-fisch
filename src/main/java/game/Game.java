package game;

import ai.GreedyAiPlayer;
import ai.RandomAiPlayer;
import files.DataWriter;
import utility.InputReader;

public class Game {
  private final GameBoard board;
  private final Player[] players;
  private int currentPlayerIndex;
  private Player currentPlayer;

  public Game() {
    this.board = new GameBoard();
    this.players = initializePlayers();
  }

  public Game(String option) {
    this.board = new GameBoard();
    this.players = new Player[2];
    if (option.equals("baseline-test")) {
      Player player1 = new RandomAiPlayer("Random AI (easy)", 4, "B");
      Player player2 = new GreedyAiPlayer("Greedy AI1 (medium)", 4, "R");
      this.players[0] = player1;
      this.players[1] = player2;
      this.currentPlayer = this.players[this.currentPlayerIndex];
    }
  }

  public static void main(String[] args) {
    // Start game with human players
    // Game game = new Game();

    // Start game with two baseline AIs (easy vs medium)
    long start = System.currentTimeMillis();
    Game game;
    int numberOfRounds = 1000;
    for (int i = 0; i < numberOfRounds; i++) {
      game = new Game("baseline-test");
      game.startPlacementPhase();
      game.startMovementPhase();
    }
    System.out.println(System.currentTimeMillis() - start + "ms");
  }

  private Player[] initializePlayers() {
    int totalPlayerCount = InputReader.getPlayerCount();
    int aiPlayerCount = InputReader.getAiPlayerCount(totalPlayerCount);
    String aiDifficulty;
    do {
      aiDifficulty = InputReader.getAiDifficulty();
    } while (!aiDifficulty.equals("easy")
        && !aiDifficulty.equals("medium")
        && !aiDifficulty.equals("hard"));
    int humanPlayerCount = totalPlayerCount - aiPlayerCount;

    int penguinCount = 0;
    switch (totalPlayerCount) {
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

    Player[] players = new Player[totalPlayerCount];
    for (int i = 0; i < humanPlayerCount; i++) {
      players[i] = new Player(InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i));
    }

    for (int i = humanPlayerCount; i < totalPlayerCount; i++) {
      switch (aiDifficulty) {
        case "easy":
          players[i] = new RandomAiPlayer("Random Baseline AI (easy)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));

          break;
        case "medium":
          players[i] = new GreedyAiPlayer("Greedy Baseline AI (medium)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));

          break;
        default:
          // TODO try again
          System.out.println("Invalid difficulty");
          break;
      }
      InputReader.AVAILABLE_COLORS.remove(0);
    }

    this.currentPlayer = players[currentPlayerIndex];

    return players;
  }

  private void updateCurrentPlayerPlacementPhase() {
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
    this.currentPlayer = this.players[currentPlayerIndex];
  }

  private void updateCurrentPlayerMovementPhase() {
    do {
      this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
      this.currentPlayer = this.players[currentPlayerIndex];

      if (!areLegalMovesPossible()) {
        break;
      }
    } while (!this.currentPlayer.hasPenguinsToMove(this.board));
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
    System.out.println("Start placement...");

    do {
      if (this.currentPlayer.getClass().getSimpleName().equals("Player")) {
        int[] placementCoordinates;
        do {
          placementCoordinates = InputReader.getPlacementCoordinates(this.currentPlayer);
        } while (!this.board.placePenguin(
            currentPlayer.getCurrentPenguin(), placementCoordinates[0], placementCoordinates[1]));
        currentPlayer.updateCurrentPenguinIndex();
      } else if (this.currentPlayer.getClass().getSimpleName().equals("RandomAiPlayer")) {
        ((RandomAiPlayer) this.currentPlayer).placePenguin(this.board);
      } else if (this.currentPlayer.getClass().getSimpleName().equals("GreedyAiPlayer")) {
        ((GreedyAiPlayer) this.currentPlayer).placePenguin(this.board);
      }
      updateCurrentPlayerPlacementPhase();
      System.out.println(this.board);
    } while (canPenguinsBePlaced());
  }

  private boolean areLegalMovesPossible() {
    for (Player player : this.players) {
      if (player.hasPenguinsToMove(this.board)) {
        return true;
      }
    }

    return false;
  }

  public void startMovementPhase() {
    System.out.println("Start movement...");

    // TODO only let penguins be selected that can be moved
    do {
      if (this.currentPlayer.getClass().getSimpleName().equals("Player")) {
        int[] penguinCoordinates;
        int penguinIndex;
        do {
          penguinCoordinates = InputReader.getPenguinCoordinates(this.currentPlayer);
          penguinIndex =
              this.currentPlayer.penguinIndexAtPosition(
                  penguinCoordinates[0], penguinCoordinates[1]);
        } while (penguinIndex == -1);

        Penguin penguinToMove = this.currentPlayer.getPenguin(penguinIndex);
        int[] movementCoordinates;
        do {
          movementCoordinates = InputReader.getMovementCoordinates(this.currentPlayer);
        } while (!this.board.movePenguin(
            penguinToMove, movementCoordinates[0], movementCoordinates[1]));
      } else if (this.currentPlayer.getClass().getSimpleName().equals("RandomAiPlayer")) {
        ((RandomAiPlayer) this.currentPlayer).movePenguin(this.board);
      } else if (this.currentPlayer.getClass().getSimpleName().equals("GreedyAiPlayer")) {
        ((GreedyAiPlayer) this.currentPlayer).movePenguin(this.board);
      }

      updateCurrentPlayerMovementPhase();
      System.out.println(board);
    } while (areLegalMovesPossible());

    System.out.println("Game finished...");

    printAllScores();

    printWinner();

    DataWriter.writeDataLine(
        "/Users/Lucas/thesis-data/test.csv",
        players[0].getName(),
        players[0].getCollectedTileCount(),
        players[0].getCollectedFishCount(),
        players[1].getName(),
        players[1].getCollectedTileCount(),
        players[1].getCollectedFishCount());
  }

  private void printAllScores() {
    for (Player player : this.players) {
      System.out.println(player.getScore());
    }
  }

  // TODO adjust winning rules
  private void printWinner() {
    int maxFishCount = this.players[0].getCollectedFishCount();
    int winnerIndex = 0;
    for (int i = 1; i < this.players.length; i++) {
      if (this.players[i].getCollectedFishCount() > maxFishCount) {
        maxFishCount = this.players[i].getCollectedFishCount();
        winnerIndex = i;
      }
    }
    System.out.println("Winner: " + this.players[winnerIndex].getName());
  }
}
