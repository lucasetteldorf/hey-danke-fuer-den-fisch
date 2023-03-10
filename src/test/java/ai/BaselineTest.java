package ai;

import game.Game;
import game.players.GreedyPlayer;
import game.players.Player;
import game.players.RandomPlayer;
import org.junit.jupiter.api.Test;
import utility.DataWriter;

public class BaselineTest {
    private static final int NUMBER_OF_GAMES = 1000;

    @Test
    void testRandomVsRandom() {
        Player[] players;
        Game game;
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            RandomPlayer p1 = new RandomPlayer("Random AI 1", 4, "B");
            RandomPlayer p2 = new RandomPlayer("Random AI 2", 4, "R");
            players = new Player[]{p1, p2};
            game = new Game(players);
            game.start();
            DataWriter.writeDataLine("/Users/Lucas/thesis-data/random-vs-random.csv", players);
        }
    }

    @Test
    void testRandomVsGreedy() {
        Player[] players;
        Game game;
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            RandomPlayer p1 = new RandomPlayer("Random AI", 4, "B");
            GreedyPlayer p2 = new GreedyPlayer("Greedy AI", 4, "R");
            players = new Player[]{p1, p2};
            game = new Game(players);
            game.start();
            DataWriter.writeDataLine("/Users/Lucas/thesis-data/random-vs-greedy.csv", players);
        }
    }
}
