package utility;

import game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputReader {
  private static final Scanner scanner = new Scanner(System.in);
  public static final ArrayList<String> AVAILABLE_COLORS =
      new ArrayList<>(Arrays.asList("B", "R", "G", "Y"));

  public static int getPlayerCount() {
    int playerCount;
    do {
      System.out.print("Number of total players (2-4): ");
      playerCount = scanner.nextInt();
      scanner.nextLine();
    } while (playerCount < 2 || playerCount > 4);

    return playerCount;
  }

  public static int getAiPlayerCount(int totalPlayerCount) {
    int aiPlayerCount;
    do {
      System.out.print("Number of AI players (0-" + (totalPlayerCount - 1) + "): ");
      aiPlayerCount = scanner.nextInt();
      scanner.nextLine();
    } while (aiPlayerCount < 0);

    //  || aiPlayerCount >= totalPlayerCount

    return aiPlayerCount;
  }

  public static String getAiDifficulty() {
    System.out.print("AI difficulty (only easy for now): ");
    return scanner.nextLine().trim();
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

  private static int[] getCoordinates(String prompt) {
    String input;
    String[] coordinates;
    do {
      System.out.print(prompt);
      input = scanner.nextLine();
      coordinates = input.split(" ");
      if (coordinates.length != 2) {
        System.out.println("Please enter two valid coordinates separated by a space...");
      }
      // TODO make sure only numbers are entered
    } while (coordinates.length != 2);

    return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
  }

  public static int[] getPlacementCoordinates(Player currentPlayer) {
    return getCoordinates(
        currentPlayer + ": Coordinates to place penguin (separated by a space): ");
  }

  public static int[] getPenguinCoordinates(Player currentPlayer) {
    return getCoordinates(
        currentPlayer + ": Coordinates of the penguin to move (separated by a space): ");
  }

  public static int[] getMovementCoordinates(Player currentPlayer) {
    return getCoordinates(
        currentPlayer + ": Coordinates to move the selected penguin to (separated by a space): ");
  }
}
