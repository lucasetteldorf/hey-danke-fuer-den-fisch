package game.players;

import game.GameBoard;
import game.Penguin;
import utility.RandomNumbers;

import java.util.List;

public class Player {
    private final PlayerType type;
    private final int index;
    private final String name;
    private final Penguin[] penguins;
    private int penguinToPlaceIndex;
    private int collectedTileCount;
    private int collectedFishCount;
    private boolean arePenguinsRemovedFromBoard;

    public Player(PlayerType type, int index, String name, int penguinCount, String penguinColor) {
        this.type = type;
        this.index = index;
        this.name = name;
        this.penguins = initializePenguins(penguinCount, penguinColor);
    }

    // copy constructor
    public Player(Player player) {
        this.type = player.type;
        this.index = player.index;
        this.name = player.name;
        this.penguins = initializePenguins(player.penguins.length, player.penguins[0].getColor());
        this.penguinToPlaceIndex = player.penguinToPlaceIndex;
        this.collectedTileCount = player.collectedTileCount;
        this.collectedFishCount = player.collectedFishCount;
    }

    private Penguin[] initializePenguins(int penguinCount, String color) {
        Penguin[] penguins = new Penguin[penguinCount];

        for (int i = 0; i < penguins.length; i++) {
            penguins[i] = new Penguin(color, this);
        }

        return penguins;
    }

    public PlayerType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public Penguin[] getPenguins() {
        return penguins;
    }

    public Penguin getPenguinByIndex(int index) {
        return (index >= 0 && index < this.penguins.length) ? this.penguins[index] : null;
    }

    public Penguin getPenguinToPlace() {
        return this.penguins[this.penguinToPlaceIndex];
    }

    public void updatePenguinToPlace() {
        this.penguinToPlaceIndex++;
    }

    public boolean canPlacePenguin() {
        return this.penguinToPlaceIndex < this.penguins.length;
    }

    public int getCollectedTileCount() {
        return collectedTileCount;
    }

    public void updateTileCount() {
        this.collectedTileCount++;
    }

    public int getCollectedFishCount() {
        return collectedFishCount;
    }

    public void updateFishCount(int fishCount) {
        this.collectedFishCount += fishCount;
    }

    public boolean arePenguinsRemovedFromBoard() {
        return this.arePenguinsRemovedFromBoard;
    }

    public void setArePenguinsRemovedFromBoard(boolean arePenguinsRemovedFromBoard) {
        this.arePenguinsRemovedFromBoard = arePenguinsRemovedFromBoard;
    }

    public String getScore() {
        return this
                + ": "
                + this.collectedTileCount
                + " tiles and "
                + this.collectedFishCount
                + " fish collected";
    }

    public int[] getRandomPlacementPosition(GameBoard board) {
        List<int[]> legalPlacementPositions = board.getAllLegalPlacementPositions();
        return legalPlacementPositions.get(RandomNumbers.getRandomIndex(legalPlacementPositions.size()));
    }

    @Override
    public String toString() {
        return this.name + " (" + getPenguinByIndex(0) + ")";
    }
}
