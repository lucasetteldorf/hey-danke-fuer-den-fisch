package game;

import ai.RandomAiPlayer;
import utility.InputReader;

public class Game {
    private final GameBoard board;

    public Game() {
        this.board = new GameBoard(initializePlayers());
    }

    public static void main(String[] args) {
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
            players[i] = new Player(InputReader.getPlayerName(i), penguinCount, InputReader.getPenguinColor(i), PlayerType.HUMAN);
        }

        for (int i = humanPlayerCount; i < totalPlayerCount; i++) {
            if (aiDifficulty.equals("easy")) {
                players[i] = new RandomAiPlayer("Random Baseline AI (easy)", penguinCount, InputReader.AVAILABLE_COLORS.get(0), PlayerType.RANDOM_AI);

                //                case "medium":
//                    players[i] = new GreedyAiPlayer("Greedy Baseline AI (medium)", penguinCount, InputReader.AVAILABLE_COLORS.get(0));
//
//                    break;
            } else {// TODO try again
                System.out.println("Invalid difficulty");
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
            switch (board.getCurrentPlayer().getType()) {
                case HUMAN:
                    int[] placementPosition;
                    do {
                        placementPosition = InputReader.getPlacementPosition(this.board.getCurrentPlayer());
                    } while (!this.board.placeCurrentPlayerPenguin(placementPosition[0], placementPosition[1]));
                    break;
                case RANDOM_AI:
                    // TODO placement not working as intended (board is infinitely printed)
                    ((RandomAiPlayer) board.getCurrentPlayer()).placePenguin(board);
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
                continue;
            }

            System.out.println(this.board);

            int[] penguinPosition;
            do {
                penguinPosition = InputReader.getPenguinPosition(this.board.getCurrentPlayer());
            } while (!this.board.canPenguinAtPositionBeMoved(penguinPosition[0], penguinPosition[1]));

            int[] movementPosition;
            do {
                movementPosition = InputReader.getMovementPosition(this.board.getCurrentPlayer());
            } while (!this.board.moveCurrentPlayerPenguin(penguinPosition[0], penguinPosition[1], movementPosition[0], movementPosition[1]));
        }

        System.out.println();
        this.board.printPlayerScores();
        System.out.println();
        this.board.printWinner();
    }
}
