package utility;

import game.players.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {
  public static final ArrayList<String> AVAILABLE_COLORS =
      new ArrayList<>(Arrays.asList("B", "R", "G", "Y"));
  private static final Scanner scanner = new Scanner(System.in);

  private static double readDouble(String prompt) {
    System.out.print(prompt);
    double input = -2;
    try {
      input = scanner.nextDouble();
    } catch (InputMismatchException e) {
      System.out.println("Please enter a valid number (decimal)...");
    }
    scanner.nextLine();
    return input;
  }

  private static int readInt(String prompt) {
    System.out.print(prompt);
    int input = -2;
    try {
      input = scanner.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Please enter a valid number (integer)...");
    }
    scanner.nextLine();
    return input;
  }

  private static String readLine(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  public static int getTotalPlayerCount() {
    int playerCount;
    do {
      playerCount = readInt("Number of total players (2-4): ");
    } while (playerCount < 2 || playerCount > 4);
    return playerCount;
  }

  public static String getPlayerName(int playerIndex) {
    return readLine("Player " + (playerIndex + 1) + " name: ");
  }

  public static int getPlayerType(int playerIndex) {
    int playerType;
    do {
      playerType =
          readInt(
              "Player " + playerIndex + " type (1 = Human, 2 = Random, 3 = Greedy, 4 = MCTS): ");
    } while (playerType < 1 || playerType > 4);
    return playerType;
  }

  public static String getPenguinColor(int playerIndex) {
    String penguinColor;
    do {
      penguinColor =
          readLine(
              "Player "
                  + (playerIndex + 1)
                  + " penguin color ("
                  + "B"
                  + " = blue, "
                  + "R"
                  + " = red, "
                  + "G"
                  + " = green, "
                  + "Y"
                  + " = yellow"
                  + "): ");
      penguinColor = penguinColor.toUpperCase();
    } while (penguinColor.length() != 1 || !AVAILABLE_COLORS.contains(penguinColor));
    AVAILABLE_COLORS.remove(penguinColor);

    return penguinColor;
  }

  public static int getMctsSimulationTime() {
    int simulationTime;
    do {
      simulationTime =
          readInt("MCTS simulation time limit in ms (enter -1 for the default value of 100 ms): ");
      if (simulationTime == -1) {
        simulationTime = 100;
        break;
      }
    } while (simulationTime < 0);
    return simulationTime;
  }

  public static double getMctsCValue() {
    double cValue;
    do {
      cValue = readDouble("MCTS C value (enter -1 for the default value of 0,5): ");
      if (cValue == -1) {
        cValue = 0.5;
        break;
      }
    } while (cValue < 0);
    return cValue;
  }

  private static int[] readPosition(String prompt) {
    String input;
    String[] coordinates;
    boolean typeError = false;
    do {
      System.out.print(prompt);
      input = scanner.nextLine();
      coordinates = input.split(" ");
      if (coordinates.length != 2) {
        System.out.println("Please enter two valid coordinates separated by a space...");
        continue;
      }
      typeError = false;
      try {
        Integer.parseInt(coordinates[0]);
        Integer.parseInt(coordinates[1]);
      } catch (NumberFormatException e) {
        typeError = true;
        System.out.println(
            "Please use two integers separated by a space to specify the coordinates...");
      }
    } while (coordinates.length != 2 || typeError);

    return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
  }

  public static int[] getPlacementPosition(Player currentPlayer) {
    return readPosition(currentPlayer + ": Coordinates to place penguin (separated by a space): ");
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
