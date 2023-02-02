package game;

import java.util.Random;

public class GameBoard {
    private IceFloeTile[][] tiles;

    public GameBoard() {
        this.tiles = initialize();
    }


    private IceFloeTile[][] initialize() {
        IceFloeTile[][] randomTiles = {};

        Random random = new Random();
        random.nextInt(3);

        return randomTiles;
    }

    public void print() {
    }
}