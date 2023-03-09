package game;

import utility.ConsoleColors;

import java.util.Arrays;

public class Penguin {
    // values: "B" = blue, "R" = red, "G" = green, "Y" = yellow
    private final String color;
    private final int[] position;
    private boolean isOnBoard;

    public Penguin(String color) {
        switch (color) {
            case "B":
                this.color = ConsoleColors.BLUE_PLAYER;
                break;
            case "R":
                this.color = ConsoleColors.RED_PLAYER;
                break;
            case "G":
                this.color = ConsoleColors.GREEN_PLAYER;
                break;
            case "Y":
                this.color = ConsoleColors.YELLOW_PLAYER;
                break;
            default:
                this.color = ConsoleColors.RESET;
                break;
        }
        this.position = new int[2];
    }

    // copy constructor
    public Penguin(Penguin penguin) {
        this.color = penguin.color;
        this.position = new int[]{penguin.getRow(), penguin.getCol()};
    }

    public void setPosition(int rowIndex, int colIndex) {
        this.position[0] = rowIndex;
        this.position[1] = colIndex;
    }

    public int[] getPosition() {
        return this.position;
    }

    public int getRow() {
        return position[0];
    }

    public int getCol() {
        return position[1];
    }

    public String getColor() {
        return color;
    }

    public boolean isOnBoard() {
        return isOnBoard
    }

    public void setOnBoard(boolean onBoard) {
        isOnBoard = onBoard;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Penguin penguin) {
            return this.position[0] == penguin.position[0] && this.position[1] == penguin.position[1];
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(position);
    }

    @Override
    public String toString() {
        return color;
    }
}
