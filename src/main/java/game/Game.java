package game;

import game.players.*;
import utility.DataWriter;
import utility.InputReader;

public class Game {
    private final GameBoard board;
    private final Player[] players;
    private final String filePath;
    private int currentPlayer;

    public Game() {
        this.filePath = "";
        this.board = new GameBoard();
        this.players = initPlayers();
    }

    public Game(String filePath, Player[] players) {
        this.filePath = filePath;
        this.board = new GameBoard();
        this.players = players;
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
            players[i] = new HumanPlayer(InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i));
        }

        for (int i = humanPlayerCount; i < totalPlayerCount; i++) {
            switch (aiDifficulty) {
                case "easy":
                    players[i] = new RandomPlayer("Random Baseline AI (easy)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
                    break;
                case "medium":
                    players[i] = new GreedyPlayer("Greedy Baseline AI (medium)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
                    break;
                case "hard":
                    players[i] = new MctsPlayer("MCTS AI (hard)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
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
    }

    private Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    private void updateCurrentPlayer() {
        currentPlayer = (currentPlayer + 1) % players.length;
    }

    private void startPlacementPhase() {
        board.printBoard();
        System.out.println("Start placement...\n");

        while (!this.board.isPlacementPhaseOver(players)) {
            int[] placementPosition = new int[2];
            switch (getCurrentPlayer().getType()) {
                case HUMAN:
                    do {
                        placementPosition = InputReader.getPlacementPosition(getCurrentPlayer());
                    } while (!board.isLegalPlacementPosition(placementPosition));
                    break;
                case RANDOM:
                    RandomPlayer randomPlayer = (RandomPlayer) getCurrentPlayer();
                    placementPosition = randomPlayer.getRandomPlacementPosition(board);
                    break;
                case GREEDY:
                    GreedyPlayer greedyPlayer = (GreedyPlayer) getCurrentPlayer();
                    placementPosition = greedyPlayer.getRandomPlacementPosition(board);
                    break;
                case MCTS:
                    MctsPlayer mctsPlayer = (MctsPlayer) getCurrentPlayer();
                    placementPosition = mctsPlayer.getBestPlacementPosition(this.board);
            }
            this.board.placePenguin(getCurrentPlayer(), placementPosition[0], placementPosition[1]);
            updateCurrentPlayer();
            board.printBoard();
        }
    }

    private void startMovementPhase() {
        System.out.println("Start movement...\n");

        while (!this.board.isMovementPhaseOver(players)) {
            if (!board.hasPlayerLegalMoves(getCurrentPlayer())) {
                if (!getCurrentPlayer().arePenguinsRemovedFromBoard()) {
                    board.removeAllPenguinsAndTiles(getCurrentPlayer());
                    getCurrentPlayer().setPenguinsRemovedFromBoard(true);
                    board.printBoard();
                }
                updateCurrentPlayer();
                continue;
            }

            int[] oldPosition;
            int[] newPosition;
            Move move = null;
            switch (getCurrentPlayer().getType()) {
                case HUMAN:
                    do {
                        oldPosition = InputReader.getPenguinPosition(getCurrentPlayer());
                    } while (!this.board.hasPenguinLegalMoves(oldPosition[0], oldPosition[1]));
                    do {
                        newPosition = InputReader.getMovementPosition(getCurrentPlayer());
                    } while (!board.isLegalMove(oldPosition, newPosition));
                    move = new Move(oldPosition, newPosition);
                    break;
                case RANDOM:
                    RandomPlayer randomPlayer = (RandomPlayer) getCurrentPlayer();
                    oldPosition = randomPlayer.getRandomPenguinPosition(this.board);
                    newPosition = randomPlayer.getRandomMovementPositionForPenguin(this.board, this.board.getPenguin(oldPosition[0], oldPosition[1]));
                    move = new Move(oldPosition, newPosition);
                    break;
                case GREEDY:
                    GreedyPlayer greedyPlayer = (GreedyPlayer) getCurrentPlayer();
                    oldPosition = greedyPlayer.getBestPenguinPosition(this.board);
                    newPosition = greedyPlayer.getBestMovementPosition(this.board, this.board.getPenguin(oldPosition[0], oldPosition[1]));
                    move = new Move(oldPosition, newPosition);
                    break;
                case MCTS:
                    MctsPlayer mctsPlayer = (MctsPlayer) getCurrentPlayer();
                    move = mctsPlayer.getBestMove(board);
                    break;
            }
            board.movePenguin(getCurrentPlayer(), move);
            updateCurrentPlayer();
            board.printBoard();
        }


        writeResults();
    }

    private void printScores() {
        for (Player player : players) {
            System.out.println(player.getScore());
        }
    }

    private void printWinner() {
        System.out.println("Winner: " + board.getWinner(players));
    }

    private void writeResults() {
        if (!this.filePath.equals("")) {
            DataWriter.writeDataLine(this.filePath, players);
        }
    }
}
