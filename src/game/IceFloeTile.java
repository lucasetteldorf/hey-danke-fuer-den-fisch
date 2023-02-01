package game;

public class IceFloeTile {
    private int fishCount;
    private boolean isOnBoard;
    private PenguinColor penguinColor;
    private boolean isUnoccupied;

    public IceFloeTile(int fishCount) {
        this.fishCount = fishCount;
        this.isOnBoard = true;
        this.isUnoccupied = true;
    }

    public int getFishCount() {
        return fishCount;
    }

    public void setFishCount(int fishCount) {
        this.fishCount = fishCount;
    }

    public boolean isOnBoard() {
        return isOnBoard;
    }

    public void setOnBoard(boolean onBoard) {
        isOnBoard = onBoard;
    }

    public PenguinColor getPenguinColor() {
        return penguinColor;
    }

    public void setPenguinColor(PenguinColor penguinColor) {
        this.penguinColor = penguinColor;
    }

    public boolean isUnoccupied() {
        return isUnoccupied;
    }

    public void setUnoccupied(boolean unoccupied) {
        isUnoccupied = unoccupied;
    }
}
