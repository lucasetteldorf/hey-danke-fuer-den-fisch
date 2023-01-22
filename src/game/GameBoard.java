package game;

import java.util.Random;

public class GameBoard {
    private final int NUMBER_OF_TILES = 60;
    private final int NUMBER_OF_ONE_FISH_TILES = 30;
    private final int NUMBER_OF_TWO_FISH_TILES = 20;
    private final int NUMBER_OF_THREE_FISH_TILES = 10;

    private IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = initializeGameBoard();
    }

    private IceFloeTile[] initializeGameBoard() {
        IceFloeTile[] iceFloeTiles = new IceFloeTile[NUMBER_OF_TILES];
        for (int i = 0; i < NUMBER_OF_TILES; i++) {
            if (i < NUMBER_OF_ONE_FISH_TILES) {
                iceFloeTiles[i] = new IceFloeTile(1);
            } else if (i < NUMBER_OF_ONE_FISH_TILES + NUMBER_OF_TWO_FISH_TILES) {
                iceFloeTiles[i] = new IceFloeTile(2);
            } else {
                iceFloeTiles[i] = new IceFloeTile(3);
            }
        }

        IceFloeTile[] randomTiles = new IceFloeTile[NUMBER_OF_TILES];
        for (int i = 0; i < NUMBER_OF_TILES; i++) {
            int randomNumber = getRandomNumber(0, iceFloeTiles.length - 1);
            randomTiles[i] = iceFloeTiles[randomNumber];

            IceFloeTile[] newTiles = new IceFloeTile[iceFloeTiles.length - 1];
            for (int j = 0, k = 0; j < iceFloeTiles.length; j++) {
                if (j == randomNumber) {
                    continue;
                }

                newTiles[k++] = iceFloeTiles[j];
            }
            iceFloeTiles = newTiles;
        }

        return randomTiles;
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public void printGameBoard() {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < this.tiles.length; i++) {
            if (i >= 0 && i <= 6 || i >= 15 && i <= 21 || i >= 30 && i <= 36 || i >= 45 && i <= 51) {
                str.append("  " + this.tiles[i].getFishCount());
            } else {
                str.append(" " + this.tiles[i].getFishCount() + " ");
            }

            if (i == 6 || i == 14 || i == 21 || i == 29 || i == 36 || i == 44 || i == 51) {
                str.append("\n");
            }
        }

        System.out.println(str);
    }

    /**
     * Places a penguin on the specified tile.
     * @param tileIndex The index of the tile to place the penguin on.
     * @param penguinColor The color of the penguin to place on the given tile.
     * @return True if the penguin could be placed on the specified tile successfully.
     */
    public boolean placePenguin(int tileIndex, int penguinColor) {
        IceFloeTile selectedTile = this.tiles[tileIndex];

        if (selectedTile.isOnBoard() && selectedTile.isUnoccupied()) {
            selectedTile.setPenguinColor(penguinColor);

            return true;
        }

        return false;
    }

    // TODO move penguin from source to destination, need methods for neighbor relationships (maybe coordinate system) and
    public void movePenguin(int sourceIndex, int destinationIndex) {

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        GameBoard gm = new GameBoard();
        gm.printGameBoard();

        long end = System.currentTimeMillis();
        System.out.println("\nTime: " + (end - start) + "ms");
    }
}
