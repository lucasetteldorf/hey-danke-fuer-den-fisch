package game;

import java.util.Arrays;

public class IceFloeTile {
    // order: top right, right, bottom right, bottom left, left, top left
    private static final int[][] TILE_NEIGHBOR_DISTANCES =
            new int[][]{{-1, 1}, {0, 2}, {1, 1}, {1, -1}, {0, -2}, {-1, -1}};

    private final int fishCount;
    private final int[] position;
    private final int[][] neighborPositions;
    private Penguin placedPenguin;

    public IceFloeTile(int fishCount, int rowIndex, int colIndex) {
        this.fishCount = fishCount;
        this.position = new int[]{rowIndex, colIndex};
        this.neighborPositions = initializeNeighborPositions(rowIndex, colIndex);
    }

    // copy constructor
    public IceFloeTile(IceFloeTile tile) {
        this.fishCount = tile.fishCount;
        this.position = new int[]{tile.position[0], tile.position[1]};
        this.neighborPositions = initializeNeighborPositions(tile.position[0], tile.position[1]);
        this.placedPenguin = new Penguin(tile.placedPenguin);
    }

    private int[][] initializeNeighborPositions(int rowIndex, int colIndex) {
        int[][] neighborCoordinates = new int[6][2];

        for (int i = 0; i < TILE_NEIGHBOR_DISTANCES.length; i++) {
            int neighborRowIndex = rowIndex + TILE_NEIGHBOR_DISTANCES[i][0];
            int neighborColIndex = colIndex + TILE_NEIGHBOR_DISTANCES[i][1];

            if (neighborRowIndex >= 0
                    && neighborRowIndex <= 7
                    && neighborColIndex >= 0
                    && neighborColIndex <= 14) {
                neighborCoordinates[i][0] = neighborRowIndex;
                neighborCoordinates[i][1] = neighborColIndex;
            } else {
                neighborCoordinates[i] = null;
            }
        }

        return neighborCoordinates;
    }

    public int getFishCount() {
        return fishCount;
    }

    public int[] getPosition() {
        return position;
    }

    public int[][] getNeighborPositions() {
        return neighborPositions;
    }

    public void placePenguin(Penguin placedPenguin) {
        this.placedPenguin = placedPenguin;
    }

    public boolean isUnoccupied() {
        return this.placedPenguin == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IceFloeTile tile) {
            return this.position[0] == tile.position[0]
                    && this.position[1] == tile.position[1];
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.position);
    }

    @Override
    public String toString() {
        return isUnoccupied() ? String.valueOf(this.fishCount) : String.valueOf(this.placedPenguin);
    }
}
