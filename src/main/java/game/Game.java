package game;

import game.players.GreedyAiPlayer;
import game.players.HumanPlayer;
import game.players.Player;
import game.players.RandomAiPlayer;
import utility.DataWriter;
import utility.InputReader;

public class Game {
    private final GameBoard board;
    private final String option;

    public Game() {
        this.option = "";
        this.board = new GameBoard(initializePlayers());
    }

    public Game(String option) {
        Player[] players;
        switch (option) {
            case "random-test":
                this.option = option;
                players = new Player[2];
                players[0] = new RandomAiPlayer("Random AI 1", 4, "B");
                players[1] = new RandomAiPlayer("Random AI 2", 4, "R");
                this.board = new GameBoard(players);
                break;
            case "random-greedy-test":
                this.option = option;
                players = new Player[2];
                players[0] = new RandomAiPlayer("Random AI", 4, "B");
                players[1] = new GreedyAiPlayer("Greedy AI", 4, "R");
                this.board = new GameBoard(players);
                break;
            default:
                this.option = "";
                this.board = new GameBoard(initializePlayers());
        }
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
            players[i] = new HumanPlayer(InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i));
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

        while (this.board.canPenguinsBePlaced()) {
            int[] placementPosition;
            switch (board.getCurrentPlayer().getType()) {
                case HUMAN:
                    do {
                        placementPosition = InputReader.getPlacementPosition(this.board.getCurrentPlayer());
                    } while (!this.board.placeCurrentPlayerPenguin(placementPosition[0], placementPosition[1]));
                    break;
                case RANDOM_AI:
                    RandomAiPlayer randomAiPlayer = (RandomAiPlayer) board.getCurrentPlayer();
                    placementPosition = randomAiPlayer.getRandomPlacementPosition(this.board);
                    this.board.placeCurrentPlayerPenguin(placementPosition[0], placementPosition[1]);
                    break;
                case GREEDY_AI:
                    GreedyAiPlayer greedyAiPlayer = (GreedyAiPlayer) board.getCurrentPlayer();
                    placementPosition = greedyAiPlayer.getRandomPlacementPosition(this.board);
                    this.board.placeCurrentPlayerPenguin(placementPosition[0], placementPosition[1]);
                    break;
            }

            System.out.println(this.board);
        }
    }

    private void startMovementPhase() {
        System.out.println("Start movement...\n");

        while (this.board.canPenguinsBeMoved()) {
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
                    } while (!this.board.canPenguinAtPositionBeMoved(penguinPosition[0], penguinPosition[1]));
                    do {
                        movementPosition = InputReader.getMovementPosition(this.board.getCurrentPlayer());
                    } while (!this.board.moveCurrentPlayerPenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]));

                    break;
                case RANDOM_AI:
                    RandomAiPlayer randomAiPlayer = (RandomAiPlayer) board.getCurrentPlayer();
                    penguinPosition = randomAiPlayer.getRandomPenguinPosition(this.board);
                    movementPosition = randomAiPlayer.getRandomMovementPositionForPenguin(this.board, this.board.getPenguinByPosition(penguinPosition[0], penguinPosition[1]));
                    this.board.moveCurrentPlayerPenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]);
                    break;
                case GREEDY_AI:
                    GreedyAiPlayer greedyAiPlayer = (GreedyAiPlayer) this.board.getCurrentPlayer();
                    penguinPosition = greedyAiPlayer.getBestPenguinPosition(this.board);
                    movementPosition = greedyAiPlayer.getBestMovementPosition(this.board, this.board.getPenguinByPosition(penguinPosition[0], penguinPosition[1]));
                    this.board.moveCurrentPlayerPenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]);
                    break;
            }

            System.out.println(this.board);
        }

        System.out.println();
        this.board.printPlayerScores();
        System.out.println();
        this.board.printWinner();

        if (!this.option.equals("")) {
            writeResults();
        }

    }

    private void writeResults() {
        switch (this.option) {
            case "random-test":
                Player ai1 = this.board.getPlayerByIndex(0);
                Player ai2 = this.board.getPlayerByIndex(1);
                DataWriter.writeDataLine("/Users/Lucas/thesis-data/random-test.csv", ai1.getName(), ai1.getCollectedTileCount(), ai1.getCollectedFishCount(), ai2.getName(), ai2.getCollectedTileCount(), ai2.getCollectedFishCount());
                break;
            case "random-greedy-test":
                ai1 = this.board.getPlayerByIndex(0);
                ai2 = this.board.getPlayerByIndex(1);
                DataWriter.writeDataLine("/Users/Lucas/thesis-data/random-greedy-test.csv", ai1.getName(), ai1.getCollectedTileCount(), ai1.getCollectedFishCount(), ai2.getName(), ai2.getCollectedTileCount(), ai2.getCollectedFishCount());
                break;
            default:
                break;
        }
    }
}
