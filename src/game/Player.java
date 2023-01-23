package game;

public class Player {
    private int penguinColor;
    private int numberOfPenguins;
    private int collectedFishCount;
    private int collectedTilesCount;

    public Player(int penguinColor, int numberOfPlayers) {
        this.penguinColor = penguinColor;
        switch (numberOfPlayers) {
            case 2:
                this.numberOfPenguins = 4;
                break;
            case 3:
                this.numberOfPenguins = 3;
                break;
            case 4:
                this.numberOfPenguins = 2;
                break;
        }
        this.collectedFishCount = 0;
        this.collectedTilesCount = 0;
    }

    public int getPenguinColor() {
        return penguinColor;
    }

    public void setPenguinColor(int penguinColor) {
        this.penguinColor = penguinColor;
    }

    public int getNumberOfPenguins() {
        return numberOfPenguins;
    }

    public void setNumberOfPenguins(int numberOfPenguins) {
        this.numberOfPenguins = numberOfPenguins;
    }

    public int getCollectedFishCount() {
        return collectedFishCount;
    }

    public void setCollectedFishCount(int collectedFishCount) {
        this.collectedFishCount = collectedFishCount;
    }

    public int getCollectedTilesCount() {
        return collectedTilesCount;
    }

    public void setCollectedTilesCount(int collectedTilesCount) {
        this.collectedTilesCount = collectedTilesCount;
    }
}
