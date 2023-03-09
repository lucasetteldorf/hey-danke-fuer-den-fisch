package utility;

public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    public static final String BLUE_PLAYER = BLUE + "B" + RESET;
    public static final String RED_PLAYER = RED + "R" + RESET;
    public static final String GREEN_PLAYER = GREEN + "G" + RESET;
    public static final String YELLOW_PLAYER = YELLOW + "Y" + RESET;

    public static String getColorString(String color) {
        switch (color) {
            case "B":
                return ConsoleColors.BLUE_PLAYER;
            case "R":
                return ConsoleColors.RED_PLAYER;
            case "G":
                return ConsoleColors.GREEN_PLAYER;
            case "Y":
                return ConsoleColors.YELLOW_PLAYER;
            default:
                return ConsoleColors.RESET;
        }
    }
}
