package game;

import java.util.Scanner;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private static Game game;
    private GameBoard board;
    private int numberOfPlayers;
    private Player[] players;
    private int currentPlayerIndex;

    private Game(int numberOfPlayers) {
        this.board = new GameBoard();
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players[i] = new Player((i + 1), numberOfPlayers);
        }
        this.currentPlayerIndex = 0;
    }

    public static Game getInstance() {
        if (game == null) {
            int numberOfPlayers = 0;
            do {
                System.out.print("Enter the number of players (2-4): ");
                numberOfPlayers = scanner.nextInt();
            } while (numberOfPlayers < 2 || numberOfPlayers > 4);

            game = new Game(numberOfPlayers);
        }

        return game;
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
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.numberOfPlayers;
    }

    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
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

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}
