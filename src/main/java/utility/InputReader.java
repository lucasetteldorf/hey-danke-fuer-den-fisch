package utility;

import game.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputReader {
  public static final ArrayList<String> AVAILABLE_COLORS =
      new ArrayList<>(Arrays.asList("B", "R", "G", "Y"));
  private static final Scanner scanner = new Scanner(System.in);

  private static int readInt(String prompt) {
    System.out.print(prompt);
    // TODO add error handling
    int input = scanner.nextInt();
    scanner.nextLine();
    return input;
  }

  private static String readLine(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  public static int getPlayerCount() {
    int playerCount;
    do {
      playerCount = readInt("Number of total players (2-4): ");
    } while (playerCount < 2 || playerCount > 4);

    return playerCount;
  }

  public static int getAiPlayerCount(int totalPlayerCount) {
    int aiPlayerCount;
    do {
      aiPlayerCount = readInt("Number of AI players (0-" + (totalPlayerCount - 1) + "): ");
    } while (aiPlayerCount < 0 || aiPlayerCount >= totalPlayerCount);

    return aiPlayerCount;
  }

  public static String getAiDifficulty() {
    return readLine("AI difficulty (only easy for now): ");
  }

  public static String getPlayerName(int playerIndex) {
    return readLine("Player " + (playerIndex + 1) + " name: ");
  }

  // TODO same color can be chosen twice
  public static String getPenguinColor(int playerIndex) {
    String penguinColor;
    do {
      penguinColor =
          readLine(
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
    } while (penguinColor.length() != 1 && !AVAILABLE_COLORS.contains(penguinColor));
    AVAILABLE_COLORS.remove(penguinColor);

    return penguinColor;
  }

  private static int[] readPosition(String prompt) {
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

  public static int[] getPlacementPosition(Player currentPlayer) {
    return readPosition(
        currentPlayer + ": Coordinates to place penguin (separated by a space): ");
  }

  public static int[] getPenguinPosition(Player currentPlayer) {
    return readPosition(
        currentPlayer + ": Coordinates of the penguin to move (separated by a space): ");
  }

  public static int[] getMovementPosition(Player currentPlayer) {
    return readPosition(
        currentPlayer + ": Coordinates to move the selected penguin to (separated by a space): ");
  }
}
