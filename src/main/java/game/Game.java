package game;

import game.players.*;
import utility.InputReader;

public class Game {
  private final GameBoard board;
  private final boolean printBoard;

  public Game() {
    this.board = new GameBoard(initPlayers());
    this.printBoard = true;
  }

  public Game(Player[] players) {
    this.board = new GameBoard(players);
    this.printBoard = true;
  }

  public Game(Player[] players, boolean printBoard) {
    this.board = new GameBoard(players);
    this.printBoard = printBoard;
  }

  public static void main(String[] args) {
    // Normal game
    Game game = new Game();
    game.start();
  }

  public GameBoard getBoard() {
    return board;
  }

  private Player[] initPlayers() {
    int totalPlayerCount = InputReader.getTotalPlayerCount();
    int aiPlayerCount = InputReader.getAiPlayerCount(totalPlayerCount);
    int humanPlayerCount = totalPlayerCount - aiPlayerCount;
    String aiDifficulty = "";
    if (aiPlayerCount > 0) {
      do {
        aiDifficulty = InputReader.getAiDifficulty();
      } while (!aiDifficulty.equals("easy")
          && !aiDifficulty.equals("medium")
          && !aiDifficulty.equals("hard"));
    }

    int penguinCount = 0;
    switch (totalPlayerCount) {
      case 2 -> penguinCount = 4;
      case 3 -> penguinCount = 3;
      case 4 -> penguinCount = 2;
      default -> {}
    }

    Player[] players = new Player[totalPlayerCount];
    for (int i = 0; i < humanPlayerCount; i++) {
      players[i] =
          new HumanPlayer(
              InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i));
    }

    for (int i = humanPlayerCount; i < totalPlayerCount; i++) {
      switch (aiDifficulty) {
        case "easy" -> players[i] =
            new RandomPlayer(
                "Random Baseline AI (easy)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
        case "medium" -> players[i] =
            new GreedyPlayer(
                "Greedy Baseline AI (medium)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
        case "hard" -> players[i] =
            new MctsPlayer("MCTS AI (hard)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
        default -> System.out.println("Invalid difficulty");
      }
      InputReader.AVAILABLE_COLORS.remove(0);
    }

    return players;
  }

  public void start() {
    startPlacementPhase();
    startMovementPhase();
    printScores();
    printWinner();
  }

  private void startPlacementPhase() {
    if (printBoard) {
      board.printBoard();
      System.out.println("Start placement...\n");
    }

    while (!this.board.isPlacementPhaseOver()) {
      int[] placementPosition = new int[2];
      switch (board.getCurrentPlayer().getType()) {
        case HUMAN -> {
          while (true) {
            placementPosition = InputReader.getPlacementPosition(board.getCurrentPlayer());
            if (!GameBoard.isValidPosition(placementPosition)) {
              continue;
            }
            if ((board.isLegalPlacementPosition(placementPosition))) {
              break;
            }
          }
        }
        case RANDOM -> {
          RandomPlayer randomPlayer = (RandomPlayer) board.getCurrentPlayer();
          placementPosition = randomPlayer.getRandomPlacementPosition(board);
        }
        case GREEDY -> {
          GreedyPlayer greedyPlayer = (GreedyPlayer) board.getCurrentPlayer();
          placementPosition = greedyPlayer.getRandomPlacementPosition(board);
        }
        case MCTS -> {
          MctsPlayer mctsPlayer = (MctsPlayer) board.getCurrentPlayer();
          placementPosition = mctsPlayer.getBestPlacementPosition(this.board);
        }
      }
      this.board.placePenguin(placementPosition[0], placementPosition[1]);
      if (printBoard) {
        board.printBoard();
      }
    }
  }

  private void startMovementPhase() {
    if (printBoard) {
      System.out.println("Start movement...\n");
    }

    while (!this.board.isMovementPhaseOver()) {
      int[] oldPosition;
      int[] newPosition;
      Move move = null;
      switch (board.getCurrentPlayer().getType()) {
        case HUMAN -> {
          while (true) {
            oldPosition = InputReader.getPenguinPosition(board.getCurrentPlayer());
            if (!GameBoard.isValidPosition(oldPosition) || !board.hasPenguinLegalMoves(oldPosition)) {
              continue;
            }
            if (board.isValidPenguin(oldPosition)) {
              break;
            }
          }
          while (true) {
            newPosition = InputReader.getMovementPosition(board.getCurrentPlayer());
            if (!GameBoard.isValidPosition(newPosition)) {
              continue;
            }
            if (board.isLegalMove(oldPosition, newPosition)) {
              break;
            }
          }
          move = new Move(oldPosition, newPosition);
        }
        case RANDOM -> {
          RandomPlayer randomPlayer = (RandomPlayer) board.getCurrentPlayer();
          oldPosition = randomPlayer.getRandomPenguinPosition(this.board);
          newPosition = randomPlayer.getRandomMovementPositionForPenguin(this.board, oldPosition);
          move = new Move(oldPosition, newPosition);
        }
        case GREEDY -> {
          GreedyPlayer greedyPlayer = (GreedyPlayer) board.getCurrentPlayer();
          oldPosition = greedyPlayer.getBestMove(this.board);
          newPosition = greedyPlayer.getBestMovementPosition(this.board, oldPosition);
          move = new Move(oldPosition, newPosition);
        }
        case MCTS -> {
          MctsPlayer mctsPlayer = (MctsPlayer) board.getCurrentPlayer();
          move = mctsPlayer.getBestMove(board);
        }
      }
      board.movePenguin(move);
      if (printBoard) {
        board.printBoard();
      }
    }
  }

  private void printScores() {
    for (Player player : board.getPlayers()) {
      System.out.println(player.getScore());
    }
  }

  private void printWinner() {
    System.out.println((board.getWinner() == null) ? "Tie" : "Winner: " + board.getWinner());
  }
}
