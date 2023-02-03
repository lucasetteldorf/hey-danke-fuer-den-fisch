package game;

public class IceFloeTile {
    private final int fishCount;
    private boolean isUnoccupied;

    public IceFloeTile(int fishCount) {
        this.fishCount = fishCount;
        this.isUnoccupied = true;
    }

    public int getFishCount() {
        return fishCount;
    }

    public boolean isUnoccupied() {
        return isUnoccupied;
    }

    public void setUnoccupied(boolean unoccupied) {
        isUnoccupied = unoccupied;
    }

    @Override
    public String toString() {
        return String.valueOf(getFishCount());
    }
}