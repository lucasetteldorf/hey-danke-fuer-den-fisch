package utility;

import game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputReader {
  private static final Scanner scanner = new Scanner(System.in);
  private static final ArrayList<String> AVAILABLE_COLORS =
      new ArrayList<>(Arrays.asList("B", "R", "G", "Y"));

  public static void nextLine() {
    scanner.nextLine();
  }

  public static int getPlayerCount() {
    int playerCount;
    do {
      System.out.print("Number of players (2-4): ");
      playerCount = scanner.nextInt();
    } while (playerCount < 2 || playerCount > 4);

    return playerCount;
  }

  public static String getPlayerName(int playerIndex) {
    System.out.print("Player " + (playerIndex + 1) + " name: ");
    return scanner.next().trim();
  }

  public static String getPenguinColor(int playerIndex) {
    String penguinColor;
    do {
      System.out.print(
          "Player "
              + (playerIndex + 1)
              + " penguin color (B = blue, R = red, G = green, Y = yellow): ");
      penguinColor = scanner.next().trim();
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
}
