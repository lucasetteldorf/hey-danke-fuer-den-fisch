package game;

import java.util.Random;

public class GameBoard {
    private final int ICE_FLOE_TILE_COUNT = 60;

    private IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = initialize();
    }

    private IceFloeTile[] initialize() {
        IceFloeTile[] basicTiles = new IceFloeTile[ICE_FLOE_TILE_COUNT];
        int fishCount = 1;

        for (int i = 0; i < ICE_FLOE_TILE_COUNT; i++) {
            if (i == 30 || i == 50) {
                fishCount++;
            }

            basicTiles[i] = new IceFloeTile(fishCount);
        }

        IceFloeTile[] randomTiles = new IceFloeTile[ICE_FLOE_TILE_COUNT];
        Random random = new Random();

        for (int i = 0; i < ICE_FLOE_TILE_COUNT; i++) {
           int randomIndex = random.nextInt(basicTiles.length);
           randomTiles[i] = basicTiles[randomIndex];

           IceFloeTile[] tmp = new IceFloeTile[basicTiles.length - 1];
           for (int j = 0, k = 0; j < basicTiles.length; j++) {
               if (j != randomIndex) {
                   tmp[k++] = basicTiles[j];
               }
           }

           basicTiles = tmp;
        }

        return randomTiles;
    }

    public void print() {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < ICE_FLOE_TILE_COUNT; i++) {
            if (i <= 6 || i >= 15 && i <= 21 || i >= 30 && i <= 36 || i >= 45 && i <= 51) {
                str.append("  ").append(this.tiles[i]).append(" ");
            } else {
                str.append(this.tiles[i]).append("   ");
            }

            if (i == 6 || i == 14 || i == 21 || i == 29 || i == 36 || i == 44 || i == 51) {
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

    public static void main(String[] args) {
        GameBoard gm = new GameBoard();
        gm.print();
    }
}