package game;

public class Player {
    private int collectedTilesCount;
    private int collectedFishCount;
    private Penguin[] penguins;

    public Player() {
        this.collectedTilesCount = 0;
        this.collectedFishCount = 0;
    }

    public void updateCollectedTilesCount() {
        this.collectedTilesCount++;
    }

    public void updateCollectedFishCount(int fishCount) {
        this.collectedFishCount += fishCount;
    }
}