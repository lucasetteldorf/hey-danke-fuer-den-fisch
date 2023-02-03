package game;

import java.util.Random;

public class GameBoard {
    private final int ICE_FLOE_TILE_COUNT = 60;

    private final IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = initialize();
    }

    public static void main(String[] args) {
        GameBoard gm = new GameBoard();
        System.out.println(gm);
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

    public boolean placePenguin(Penguin penguin, int tileIndex) {
        IceFloeTile selectedTile = this.tiles[tileIndex];

        if (selectedTile.isUnoccupied() && selectedTile.getFishCount() == 1) {
            selectedTile.setUnoccupied(false);
            penguin.setPositionIndex(tileIndex);

            return true;
        }

        return false;
    }

    public boolean movePenguin(Penguin penguin, int tileIndex) {
        IceFloeTile srcTile = this.tiles[penguin.getPositionIndex()];
        IceFloeTile destTile = this.tiles[tileIndex];

        if (isValidMove(srcTile, destTile)) {
            srcTile.setUnoccupied(true);
            destTile.setUnoccupied(false);

            penguin.getPlayer().updateCollectedTilesCount();
            penguin.getPlayer().updateCollectedFishCount(srcTile.getFishCount());
            penguin.setPositionIndex(tileIndex);

            return true;
        }

        return false;
    }

    private boolean isValidMove(IceFloeTile srcTile, IceFloeTile destTile) {
        return false;
    }

    @Override
    public String toString() {
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

        return str.toString();
    }
}