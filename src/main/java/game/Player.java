package game;

public class Player {
    private PlayerType type;
    private final int index;
    private final String name;
    private final Penguin[] penguins;
    private int penguinToPlaceIndex;
    private int collectedTileCount;
    private int collectedFishCount;
    private boolean arePenguinsRemovedFromBoard;

    public Player(String name, int penguinCount, String penguinColor, PlayerType type) {
        this.type = type;
        // TODO maybe adjust if needed
        this.index = -1;
        this.name = name;
        this.penguins = initializePenguins(penguinCount, penguinColor);
    }

    public Player(int index, String name, int penguinCount, String penguinColor) {
        this.index = index;
        this.name = name;
        this.penguins = initializePenguins(penguinCount, penguinColor);
    }

    // copy constructor
    public Player(Player player) {
        this.index = player.index;
        this.name = player.name;
        this.penguins = initializePenguins(player.penguins.length, player.penguins[0].getColor());
        this.penguinToPlaceIndex = player.penguinToPlaceIndex;
        this.collectedTileCount = player.collectedTileCount;
        this.collectedFishCount = player.collectedFishCount;
    }

    public PlayerType getType() {
        return type;
    }

    public boolean arePenguinsRemovedFromBoard() {
        return this.arePenguinsRemovedFromBoard;
    }

    public void setArePenguinsRemovedFromBoard(boolean arePenguinsRemovedFromBoard) {
        this.arePenguinsRemovedFromBoard = arePenguinsRemovedFromBoard;
    }

    public int getIndex() {
        return index;
    }

    private Penguin[] initializePenguins(int penguinCount, String color) {
        Penguin[] penguins = new Penguin[penguinCount];

        for (int i = 0; i < penguins.length; i++) {
            penguins[i] = new Penguin(color, this);
        }

        return penguins;
    }

    public String getName() {
        return name;
    }

    public Penguin getPenguinByIndex(int index) {
        return (index >= 0 && index < this.penguins.length) ? this.penguins[index] : null;
    }

    public Penguin[] getPenguins() {
        return penguins;
    }

    public void updatePenguinToPlace() {
        this.penguinToPlaceIndex++;
    }

    public Penguin getPenguinToPlace() {
        return this.penguins[this.penguinToPlaceIndex];
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
        return this.name + " (" + getPenguinByIndex(0) + ")";
    }
}
