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
    String name = scanner.next().trim();
    scanner.nextLine();
    return name;
  }

  public static String getPenguinColor(int playerIndex) {
    String penguinColor;
    do {
      System.out.print(
          "Player "
              + (playerIndex + 1)
              + " penguin color ("
              + ConsoleColors.BLUE_PLAYER
              + " = blue, "
              + ConsoleColors.RED_PLAYER
              + " = red, "
              + ConsoleColors.GREEN_PLAYER
              + " = green, "
              + ConsoleColors.YELLOW_PLAYER
              + " = yellow"
              + "): ");

      penguinColor = scanner.next().trim();
      scanner.nextLine();
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