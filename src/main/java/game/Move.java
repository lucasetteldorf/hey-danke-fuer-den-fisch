package game;

public class Move {
    private final int[] oldPosition;
    private final int[] newPosition;

    public Move(int[] oldPosition, int[] newPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public int[] getOldPosition() {
        return oldPosition;
    }

    public int getOldRow() {
        return oldPosition[0];
    }

    public int getOldCol() {
        return oldPosition[1];
    }

    public int[] getNewPosition() {
        return newPosition;
    }

    public int getNewRow() {
        return newPosition[0];
    }

    public int getNewCol() {
        return newPosition[1];
    }
}
