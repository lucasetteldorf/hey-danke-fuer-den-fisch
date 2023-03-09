package game.players;

import game.Penguin;

public class BasePlayer implements Player {
    private final PlayerType type;
    private final String name;
    private final String penguinColor;
    private final Penguin[] penguins;
    private int penguinToPlaceIndex;
    private int collectedTileCount;
    private int collectedFishCount;
    private boolean penguinsRemovedFromBoard;

    public BasePlayer(PlayerType type, String name, int penguinCount, String penguinColor) {
        this.type = type;
        this.name = name;
        this.penguinColor = penguinColor;
        this.penguins = initPenguins(penguinCount, penguinColor);
    }

    // copy constructor
    public BasePlayer(BasePlayer player) {
        this.type = player.type;
        this.name = player.name;
        this.penguinColor = player.penguinColor;
        this.penguins = initPenguins(player.penguins.length, player.penguinColor);
        this.penguinToPlaceIndex = player.penguinToPlaceIndex;
        this.collectedTileCount = player.collectedTileCount;
        this.collectedFishCount = player.collectedFishCount;
        this.penguinsRemovedFromBoard = player.penguinsRemovedFromBoard;

    }

    private Penguin[] initPenguins(int penguinCount, String color) {
        Penguin[] penguins = new Penguin[penguinCount];
        for (int i = 0; i < penguins.length; i++) {
            penguins[i] = new Penguin(color);
        }
        return penguins;
    }

    @Override
    public PlayerType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPenguinColor() {
        return penguinColor;
    }

    @Override
    public Penguin[] getPenguins() {
        return penguins;
    }

    @Override
    public Penguin getPenguinToPlace() {
        return this.penguins[this.penguinToPlaceIndex];
    }

    @Override
    public void updatePenguinToPlace() {
        this.penguinToPlaceIndex++;
    }

    @Override
    public boolean canPlacePenguin() {
        return this.penguinToPlaceIndex < this.penguins.length;
    }

    @Override
    public int getCollectedTileCount() {
        return collectedTileCount;
    }

    @Override
    public void updateCollectedTileCount() {
        this.collectedTileCount++;
    }

    @Override
    public int getCollectedFishCount() {
        return collectedFishCount;
    }

    @Override
    public void updateCollectedFishCount(int fishCount) {
        this.collectedFishCount += fishCount;
    }

    @Override
    public boolean arePenguinsRemovedFromBoard() {
        return penguinsRemovedFromBoard;
    }

    @Override
    public void setPenguinsRemovedFromBoard(boolean penguinsRemovedFromBoard) {
        this.penguinsRemovedFromBoard = penguinsRemovedFromBoard;
    }

    @Override
    public String getScore() {
        return this
                + ": "
                + this.collectedTileCount
                + " tiles and "
                + this.collectedFishCount
                + " fish collected";
    }

    @Override
    public String toString() {
        return this.name + " (" + penguinColor + ")";
    }
}
