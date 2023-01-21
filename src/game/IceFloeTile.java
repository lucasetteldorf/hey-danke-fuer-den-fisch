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

    /**
     * Places a penguin on this tile.
     * @param penguinColor The color of the penguin to place on this tile.
     * @return True if the penguin could be placed on this tile successfully.
     */
    public boolean placePenguin(int penguinColor) {
        if (this.fishCount != 0 && this.penguinColor == 0) {
            this.penguinColor = penguinColor;
            return true;
        }

        return false;
    }

    public boolean isUnoccupied() {
        return this.penguinColor == 0;
    }

    public int getFishCount() {
        return fishCount;
    }

    public int getPenguinColor() {
        return penguinColor;
    }
}
