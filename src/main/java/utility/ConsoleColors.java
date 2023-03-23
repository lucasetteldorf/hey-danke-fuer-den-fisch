package utility;

public class ConsoleColors {
  private static final String RESET = "\u001B[0m";
  private static final String BLUE = "\u001B[34m";
  public static final String BLUE_PLAYER = BLUE + "B" + RESET;
  private static final String RED = "\u001B[31m";
  public static final String RED_PLAYER = RED + "R" + RESET;
  private static final String GREEN = "\u001B[32m";
  public static final String GREEN_PLAYER = GREEN + "G" + RESET;
  private static final String YELLOW = "\u001B[33m";
  public static final String YELLOW_PLAYER = YELLOW + "Y" + RESET;

  public static String getColorString(String color) {
    return switch (color) {
      case "B" -> ConsoleColors.BLUE_PLAYER;
      case "R" -> ConsoleColors.RED_PLAYER;
      case "G" -> ConsoleColors.GREEN_PLAYER;
      case "Y" -> ConsoleColors.YELLOW_PLAYER;
      default -> ConsoleColors.RESET;
    };
  }
}
