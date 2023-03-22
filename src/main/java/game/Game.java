package game;

import game.players.*;
import utility.InputReader;

public class Game {
  private final GameBoard board;
  private boolean printBoard;

  public Game() {
    this.board = new GameBoard(initPlayers());
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
      players[i] =
          new HumanPlayer(
              InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i));
    }

    for (int i = humanPlayerCount; i < totalPlayerCount; i++) {
      switch (aiDifficulty) {
        case "easy":
          players[i] =
              new RandomPlayer(
                  "Random Baseline AI (easy)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
          break;
        case "medium":
          players[i] =
              new GreedyPlayer(
                  "Greedy Baseline AI (medium)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
          break;
        case "hard":
          players[i] =
              new MctsPlayer("MCTS AI (hard)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
          break;
        default:
          System.out.println("Invalid difficulty");
          break;
      }
      InputReader.AVAILABLE_COLORS.remove(0);
    }

    return players;
  }

  public void start() {
    startPlacementPhase();
    startMovementPhase();
    board.printScores();
    board.printWinner();
  }

  private void startPlacementPhase() {
    if (printBoard) {
      board.printBoard();
      System.out.println("Start placement...\n");
    }

    while (!this.board.isPlacementPhaseOver()) {
      int[] placementPosition = new int[2];
      switch (board.getCurrentPlayer().getType()) {
        case HUMAN:
          do {
            placementPosition = InputReader.getPlacementPosition(board.getCurrentPlayer());
          } while (!board.isLegalPlacementPosition(placementPosition));
          break;
        case RANDOM:
          RandomPlayer randomPlayer = (RandomPlayer) board.getCurrentPlayer();
          placementPosition = randomPlayer.getRandomPlacementPosition(board);
          break;
        case GREEDY:
          GreedyPlayer greedyPlayer = (GreedyPlayer) board.getCurrentPlayer();
          placementPosition = greedyPlayer.getRandomPlacementPosition(board);
          break;
        case MCTS:
          MctsPlayer mctsPlayer = (MctsPlayer) board.getCurrentPlayer();
          placementPosition = mctsPlayer.getBestPlacementPosition(this.board);
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
        case HUMAN:
          do {
            oldPosition = InputReader.getPenguinPosition(board.getCurrentPlayer());
          } while (!this.board.hasPenguinLegalMoves(oldPosition[0], oldPosition[1]));
          do {
            newPosition = InputReader.getMovementPosition(board.getCurrentPlayer());
          } while (!board.isLegalMove(oldPosition, newPosition));
          move = new Move(oldPosition, newPosition);
          break;
        case RANDOM:
          RandomPlayer randomPlayer = (RandomPlayer) board.getCurrentPlayer();
          oldPosition = randomPlayer.getRandomPenguinPosition(this.board);
          newPosition = randomPlayer.getRandomMovementPositionForPenguin(this.board, oldPosition);
          move = new Move(oldPosition, newPosition);
          break;
        case GREEDY:
          GreedyPlayer greedyPlayer = (GreedyPlayer) board.getCurrentPlayer();
          oldPosition = greedyPlayer.getBestPenguinPosition(this.board);
          newPosition =
              greedyPlayer.getBestMovementPosition(
                  this.board, this.board.getPenguin(oldPosition[0], oldPosition[1]));
          move = new Move(oldPosition, newPosition);
          break;
        case MCTS:
          MctsPlayer mctsPlayer = (MctsPlayer) board.getCurrentPlayer();
          move = mctsPlayer.getBestMove(board);
          break;
      }
      board.movePenguin(move);
      if (printBoard) {
        board.printBoard();
      }
    }
  }
}
