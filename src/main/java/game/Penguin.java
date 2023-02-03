package game;

public class Penguin {
    private PenguinColor color;
    private Player player;
    private int positionIndex;

    public Penguin(PenguinColor color, Player player) {
        this.color = color;
        this.player = player;
        this.positionIndex = -1;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public Player getPlayer() {
        return player;
    }
}
