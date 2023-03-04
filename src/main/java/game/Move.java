package game;

public class Move {
    private final Penguin penguin;
    private final int rowIndex;
    private final int colIndex;

    public Move(Penguin penguin, int rowIndex, int colIndex) {
        this.penguin = penguin;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public Penguin getPenguin() {
        return penguin;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }
}
