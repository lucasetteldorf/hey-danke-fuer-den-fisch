package game;

import java.util.Scanner;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private GameBoard board;
    private int numberOfPlayers;
    private Player[] players;
    private Player currentPlayer;

    private Game(int numberOfPlayers) {
        this.board = new GameBoard();
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players[i] = new Player((i + 1), numberOfPlayers);
        }
        this.currentPlayer = this.players[0];
    }

    public static Game initializeGame() {
        int numberOfPlayers = 0;
        do {
            System.out.print("Enter the number of players (2-4): ");
            numberOfPlayers = scanner.nextInt();
        } while (numberOfPlayers < 2 || numberOfPlayers > 4);

        return new Game(numberOfPlayers);
    }

    public static void main(String[] args) {
        Game game = Game.initializeGame();
        game.getBoard().printGameBoard();

        while (game.canPenguinsBePlaced()) {
            boolean wasPlacementSuccessful = false;
            do {
                System.out.print("Player " + game.getCurrentPlayer().getPenguinColor() + ", which tile to place your penguin on: ");
                int tileIndex = scanner.nextInt();
                wasPlacementSuccessful = game.getBoard().placePenguin(tileIndex, game.getCurrentPlayer().getPenguinColor());
                if (!wasPlacementSuccessful) {
                    System.out.println("Invalid move, try again...");
                }
            } while (!wasPlacementSuccessful);

            game.getBoard().printGameBoard();
            game.getCurrentPlayer().setNumberOfPenguins(game.getCurrentPlayer().getNumberOfPenguins() - 1);
            game.nextPlayer();
        }

        System.out.println("All penguins have been placed, start playing!");
    }

    public boolean canPenguinsBePlaced() {
        for (Player player : this.players) {
            if (player.getNumberOfPenguins() != 0) {
                return true;
            }
        }

        return false;
    }

    public void nextPlayer() {
        this.currentPlayer = this.players[this.currentPlayer.getPenguinColor() % this.numberOfPlayers];
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
