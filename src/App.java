import game.Game;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = Game.getInstance();
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
}
