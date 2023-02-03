package game;

import java.util.Random;

public class GameBoard {
    private final int ICE_FLOE_TILE_COUNT = 60;

    private IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = initialize();
    }


    private IceFloeTile[] initialize() {
        IceFloeTile[] randomTiles = new IceFloeTile[ICE_FLOE_TILE_COUNT];

        int fishCount = 1;
        for (int i = 0; i < ICE_FLOE_TILE_COUNT; i++) {
            if (i == 30 || i == 50) {
                fishCount++;
            }

            randomTiles[i] = new IceFloeTile(fishCount);
        }

        Random random = new Random();
        random.nextInt(3);

        return randomTiles;
    }

    public void print() {
    }

    public boolean placePenguin() {
        return false;
    }

    public boolean movePenguin() {
        return false;
    }

    public boolean isValidMove() {
        return false;
    }
}