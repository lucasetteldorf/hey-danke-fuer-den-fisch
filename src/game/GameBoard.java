package game;

import java.util.Random;

public class GameBoard {
    private final int NUMBER_OF_TILES = 60;
    private final int NUMBER_OF_ONE_FISH_TILES = 30;
    private final int NUMBER_OF_TWO_FISH_TILES = 20;
    private final int NUMBER_OF_THREE_FISH_TILES = 10;

    private IceFloeTile[] tiles;

    public GameBoard() {
        this.tiles = createRandomGameBoard();
    }

    private IceFloeTile[] createRandomGameBoard() {
        IceFloeTile[] randomTiles = new IceFloeTile[NUMBER_OF_TILES];
        int[][] tileFishCounts = new int[][] {{1, NUMBER_OF_ONE_FISH_TILES}, {2, NUMBER_OF_TWO_FISH_TILES}, {3, NUMBER_OF_THREE_FISH_TILES}};

        int randomNumberUpperBound = 3;

        for (int i = 0; i < NUMBER_OF_TILES; i++) {
            int randomNumber = getRandomNumber(1, randomNumberUpperBound);
            int index = randomNumber - 1;

            IceFloeTile randomTile = new IceFloeTile(tileFishCounts[index][0]);
            randomTiles[i] = randomTile;
            tileFishCounts[index][1]--;
            
            if (tileFishCounts[index][1] == 0) {
                if (randomNumberUpperBound == 3) {
                    switch (randomNumber) {
                        case 1:
                            tileFishCounts = new int[][] {{2, tileFishCounts[1][1]}, {3, tileFishCounts[2][1]}};
                            break;
                        case 2:
                            tileFishCounts = new int[][] {{1, tileFishCounts[0][1]}, {3, tileFishCounts[2][1]}};
                            break;
                        case 3:
                            tileFishCounts = new int[][] {{1, tileFishCounts[0][1]}, {2, tileFishCounts[1][1]}};
                            break;
                    }
                } else if (randomNumberUpperBound == 2) {
                    switch (randomNumber) {
                        case 1:
                            tileFishCounts = new int[][] {{tileFishCounts[1][0], tileFishCounts[1][1]}};
                            break;
                        case 2:
                            tileFishCounts = new int[][] {{tileFishCounts[0][0], tileFishCounts[0][1]}};
                            break;
                    }
                }

                randomNumberUpperBound--;
            }
        }

        return randomTiles;
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public void printGameBoard() {
        String str = "";

        for (int i = 0; i < this.tiles.length; i++) {
            if (i >= 0 && i <= 6 || i >= 15 && i <= 21 || i >= 30 && i <= 36 || i >= 45 && i <= 51) {
                str += "  " + this.tiles[i].getFishCount();
            } else {
                str += " " + this.tiles[i].getFishCount() + " ";
            }

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
