package utility;

import game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputReader {
  private static final Scanner scanner = new Scanner(System.in);
  private static final ArrayList<String> AVAILABLE_COLORS =
      new ArrayList<>(Arrays.asList("B", "R", "G", "Y"));

  public static int getPlayerCount() {
    int playerCount;
    do {
      System.out.print("Number of players (2-4): ");
      playerCount = scanner.nextInt();
      scanner.nextLine();
    } while (playerCount < 2 || playerCount > 4);

    return playerCount;
  }

  public static String getPlayerName(int playerIndex) {
    System.out.print("Player " + (playerIndex + 1) + " name: ");
    return scanner.nextLine().trim();
  }

  public static String getPenguinColor(int playerIndex) {
    String penguinColor;
    do {
      System.out.print(
          "Player "
              + (playerIndex + 1)
              + " penguin color ("
              + ConsoleColors.BLUE
              + "B = blue"
              + ConsoleColors.RESET
              + ", "
              + ConsoleColors.RED
              + "R = red"
              + ConsoleColors.RESET
              + ", "
              + ConsoleColors.GREEN
              + "G = green"
              + ConsoleColors.RESET
              + ", "
              + ConsoleColors.YELLOW
              + "Y = yellow"
              + ConsoleColors.RESET
              + "): ");
      penguinColor = scanner.nextLine().trim();
    } while (!AVAILABLE_COLORS.contains(penguinColor));
    AVAILABLE_COLORS.remove(penguinColor);

    return penguinColor;
  }

  public static int[] getPlacementCoordinates(Player currentPlayer) {
    System.out.print(currentPlayer + ": Coordinates to place penguin (separated by a space): ");
    String input = scanner.nextLine();
    String[] coordinates = input.split(" ");
    return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
  }

  public static int[] getPenguinCoordinates(Player currentPlayer) {
    System.out.print(
        currentPlayer + ": Coordinates of the penguin to move (separated by a space): ");
    String input = scanner.nextLine();
    String[] coordinates = input.split(" ");
    return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
  }

  public static int[] getMovementCoordinates(Player currentPlayer) {
    System.out.print(
        currentPlayer + ": Coordinates to move the selected penguin to (separated by a space): ");
    String input = scanner.nextLine();
    String[] coordinates = input.split(" ");
    return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
  }
}
