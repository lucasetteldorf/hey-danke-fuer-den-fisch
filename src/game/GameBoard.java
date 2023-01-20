package game;

import java.util.Random;

public class GameBoard {
    private IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = createRandomGameBoard();
    }

    private IceFloeTile[] createRandomGameBoard() {
        IceFloeTile[] randomTiles = new IceFloeTile[60];
        int oneFishTilesCount = 30;
        int twoFishTilesCount = 20;
        int threeFishTilesCount = 10;
        int randomNumberUpperBound = 3;

        for (int i = 0; i < randomTiles.length; i++) {
            int randomNumber = getRandomNumber(1, randomNumberUpperBound);
            IceFloeTile tile = new IceFloeTile(randomNumber);
        }

        return randomTiles;
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public static void main(String[] args) {
        GameBoard gm = new GameBoard();
    }
}
