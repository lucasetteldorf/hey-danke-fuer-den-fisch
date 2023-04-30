package game;

import game.logic.GameBoard;
import game.logic.Move;
import game.players.*;
import utility.InputReader;

public class Game {
  private final GameBoard board;
  private final boolean printBoard;
  private final boolean printResults;

  public Game() {
    this.board = new GameBoard(initPlayers());
    this.printBoard = true;
    this.printResults = true;
  }

  public Game(Player[] players, boolean printBoard, boolean printResult) {
    this.board = new GameBoard(players);
    this.printBoard = printBoard;
    this.printResults = printResult;
  }

  public static void main(String[] args) {
    // Normal game
    Game game = new Game();
    game.start();
  }

  public GameBoard getBoard() {
    return board;
  }

  private int getPenguinCountFromPlayerCount(int playerCount) {
    int penguinCount = 0;
    switch (playerCount) {
      case 2 -> penguinCount = 4;
      case 3 -> penguinCount = 3;
      case 4 -> penguinCount = 2;
      default -> {}
    }
    return penguinCount;
  }

  private Player initPlayer(PlayerType type, int totalPlayerCount, int index) {
    int penguinCount = getPenguinCountFromPlayerCount(totalPlayerCount);
    Player player = null;
    switch (type) {
      case HUMAN -> player =
          new HumanPlayer(
              InputReader.getPlayerName(index), penguinCount, InputReader.getPenguinColor(index));
      case RANDOM -> {
        player = new RandomPlayer("Random AI", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
        InputReader.AVAILABLE_COLORS.remove(0);
      }
      case GREEDY -> {
        player = new GreedyPlayer("Greedy AI", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
        InputReader.AVAILABLE_COLORS.remove(0);
      }
      case MCTS -> {
        player = new MctsPlayer("Random AI", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
        InputReader.AVAILABLE_COLORS.remove(0);
      }
    }
    return player;
  }

  private Player[] initPlayers() {
    int totalPlayerCount = InputReader.getTotalPlayerCount();
    Player[] players = new Player[totalPlayerCount];
    int index = 1;
    do {
      int playerType;
      playerType = InputReader.getPlayerType(index);
      if (playerType >= 1 && playerType <= 4) {
        players[index - 1] =
            initPlayer(PlayerType.getTypeFromNumber(playerType), totalPlayerCount, index - 1);
        index++;
      }
    } while (index <= totalPlayerCount);
    return players;
  }

  public void start() {
    startPlacementPhase();
    startMovementPhase();
    if (printResults) {
      printScores();
      printWinner();
    }
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
          placementPosition = mctsPlayer.getBestPlacementPosition(board);
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
            if (!GameBoard.isValidPosition(oldPosition)
                || !board.hasPenguinLegalMoves(oldPosition)) {
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
