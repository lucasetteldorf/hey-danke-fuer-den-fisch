package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private final int ICE_FLOE_TILE_COUNT = 60;
    private final List<Integer> END_OF_ROW_INDICES = Arrays.asList(new Integer[] {6, 14, 21, 29, 36, 44, 51, 59});

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

//        Random random = new Random();
//        random.nextInt(3);

        return randomTiles;
    }

    public void print() {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < ICE_FLOE_TILE_COUNT; i++) {
            str.append(" ").append(this.tiles[i]).append(" ");

            if (END_OF_ROW_INDICES.contains(i)) {
                str.append("\n");
            }
        }

        System.out.println(str);
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