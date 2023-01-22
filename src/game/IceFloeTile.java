package game;

public class IceFloeTile {
    /**
     * The number of fish on this tile: 0-3.
     * 0: This tile has been removed from the board.
     */
    private int fishCount;
    /**
     * Indicates the color of the penguin on this tile.
     * 0: No penguin has been placed on this tile yet.
     * 1: The penguin is blue.
     * 2: The penguin is red.
     * 3: The penguin is green.
     * 4: The penguin is yellow.
     */
    private int penguinColor;

    public IceFloeTile(int fishCount) {
        this.fishCount = fishCount;
        this.penguinColor = 0;
    }

    public boolean isOnBoard() {
        return this.fishCount != 0;
    }

    public boolean isUnoccupied() {
        return this.penguinColor == 0;
    }

    public int getFishCount() {
        return fishCount;
    }

    public void setFishCount(int fishCount) {
        this.fishCount = fishCount;
    }

    public int getPenguinColor() {
        return penguinColor;
    }

    public void setPenguinColor(int penguinColor) {
        this.penguinColor = penguinColor;
    }
}
