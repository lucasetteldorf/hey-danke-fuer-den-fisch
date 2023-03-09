package game;

import game.players.*;
import utility.DataWriter;
import utility.InputReader;

public class Game {
    private final GameBoard board;
    private final String filePath;

    public Game() {
        this.filePath = "";
        this.board = new GameBoard();
    }

    public Game(String filePath, Player[] players) {
        this.filePath = filePath;
        this.board = new GameBoard();
    }

    public static void main(String[] args) {
        // Normal game
        Game game = new Game();
        game.start();

    }

    private Player[] initializePlayers() {
        int totalPlayerCount = InputReader.getPlayerCount();
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
            players[i] = new Player(PlayerType.HUMAN, i, InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i));
        }

        for (int i = humanPlayerCount; i < totalPlayerCount; i++) {
            switch (aiDifficulty) {
                case "easy":
                    players[i] = new RandomAiPlayer(i, "Random Baseline AI (easy)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
                    break;
                case "medium":
                    players[i] = new GreedyAiPlayer(i, "Greedy Baseline AI (medium)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
                    break;
                case "hard":
                    players[i] = new MctsAiPlayer(i, "MCTS AI (hard)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
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

    private void startPlacementPhase() {
        System.out.println(this.board);
        System.out.println("Start placement...\n");

        while (this.board.isPlacementPhaseOver()) {
            int[] placementPosition;
            switch (board.getCurrentPlayer().getType()) {
                case HUMAN:
                    do {
                        placementPosition = InputReader.getPlacementPosition(this.board.getCurrentPlayer());
                    } while (!this.board.placePenguin(placementPosition[0], placementPosition[1]));
                    break;
                case RANDOM_AI:
                    RandomAiPlayer randomAiPlayer = (RandomAiPlayer) board.getCurrentPlayer();
                    placementPosition = randomAiPlayer.getRandomPlacementPosition(this.board);
                    this.board.placePenguin(placementPosition[0], placementPosition[1]);
                    break;
                case GREEDY_AI:
                    GreedyAiPlayer greedyAiPlayer = (GreedyAiPlayer) board.getCurrentPlayer();
                    placementPosition = greedyAiPlayer.getRandomPlacementPosition(this.board);
                    this.board.placePenguin(placementPosition[0], placementPosition[1]);
                    break;
                case MCTS_AI:
                    MctsAiPlayer mctsAiPlayer = (MctsAiPlayer) board.getCurrentPlayer();
                    placementPosition = mctsAiPlayer.getBestPlacementPosition(this.board);
                    this.board.placePenguin(placementPosition[0], placementPosition[1]);
            }

            System.out.println(this.board);
        }
    }

    private void startMovementPhase() {
        System.out.println("Start movement...\n");

        while (this.board.isMovementPhaseOver()) {
            if (!board.hasCurrentPlayerLegalMoves()) {
                this.board.skipCurrentPlayer();
                System.out.println(this.board);
                continue;
            }

            int[] penguinPosition;
            int[] movementPosition;
            switch (board.getCurrentPlayer().getType()) {
                case HUMAN:
                    do {
                        penguinPosition = InputReader.getPenguinPosition(this.board.getCurrentPlayer());
                    } while (!this.board.canPenguinMove(penguinPosition[0], penguinPosition[1]));
                    do {
                        movementPosition = InputReader.getMovementPosition(this.board.getCurrentPlayer());
                    } while (!this.board.movePenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]));

                    break;
                case RANDOM_AI:
                    RandomAiPlayer randomAiPlayer = (RandomAiPlayer) board.getCurrentPlayer();
                    penguinPosition = randomAiPlayer.getRandomPenguinPosition(this.board);
                    movementPosition = randomAiPlayer.getRandomMovementPositionForPenguin(this.board, this.board.getPenguinByPosition(penguinPosition[0], penguinPosition[1]));
                    this.board.movePenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]);
                    break;
                case GREEDY_AI:
                    GreedyAiPlayer greedyAiPlayer = (GreedyAiPlayer) this.board.getCurrentPlayer();
                    penguinPosition = greedyAiPlayer.getBestPenguinPosition(this.board);
                    movementPosition = greedyAiPlayer.getBestMovementPosition(this.board, this.board.getPenguinByPosition(penguinPosition[0], penguinPosition[1]));
                    this.board.movePenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]);
                    break;
                case MCTS_AI:
                    MctsAiPlayer mctsAiPlayer = (MctsAiPlayer) board.getCurrentPlayer();
                    this.board.movePenguin(mctsAiPlayer.getBestMove(this.board));
            }

            System.out.println(this.board);
        }

        System.out.println();
        this.board.printPlayerScores();
        System.out.println();
        this.board.printWinner();

        writeResults();
    }

    private void writeResults() {
        if (!this.filePath.equals("")) {
            DataWriter.writeDataLine(this.filePath, this.board.getPlayers());
        }
    }
}
