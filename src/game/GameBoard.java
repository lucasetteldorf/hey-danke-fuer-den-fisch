package game;

import java.util.Random;

public class GameBoard {
    private IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = createRandomGameBoard();
    }

    private IceFloeTile[] createRandomGameBoard() {
        IceFloeTile[] randomTiles = new IceFloeTile[60];
        int[] tilesFishCount = new int[] {30, 20, 10};
        int randomNumberUpperBound = 3;

        for (int i = 0; i < randomTiles.length; i++) {
            int randomNumber = getRandomNumber(1, randomNumberUpperBound);
            IceFloeTile tile = new IceFloeTile(randomNumber);
            randomTiles[i] = tile;

            // Problem: bei Änderung der Array-Definition passt die Anzahl der Fische nicht mehr
            tilesFishCount[randomNumber - 1]--;
            if (tilesFishCount[randomNumber - 1] == 0) {
                if (randomNumberUpperBound == 3) {
                    if (randomNumber == 1) {
                        tilesFishCount = new int[] {tilesFishCount[1], tilesFishCount[2]};
                    } else if (randomNumber == 2) {
                        tilesFishCount = new int[] {tilesFishCount[0], tilesFishCount[2]};
                    } else if (randomNumber == 3) {
                        tilesFishCount = new int[] {tilesFishCount[0], tilesFishCount[1]};
                    }
                } else if (randomNumberUpperBound == 2) {
                    if (randomNumber == 1) {
                        tilesFishCount = new int[] {tilesFishCount[1]};
                    } else if (randomNumber == 2) {
                        tilesFishCount = new int[] {tilesFishCount[0]};
                    }
                }

                randomNumberUpperBound--;
            }
        }

        return randomTiles;
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public void printGameBoard() {
        String str = "";

        for (int i = 0; i < this.tiles.length; i++) {
            str += " " + this.tiles[i].getFishCount() + " ";
            if (i == 6 || i == 14 || i == 21 || i == 29 || i == 36 || i == 44 || i == 51) {
                str += "\n";
            }
        }

        System.out.println(str);
    }

    public static void main(String[] args) {
        GameBoard gm = new GameBoard();
        gm.printGameBoard();
    }
}
