//package game;
//
//import game.players.GreedyPlayer;
//import game.players.Player;
//import game.players.RandomPlayer;
//import org.junit.jupiter.api.Test;
//
//public class BaselineAiTest {
//    @Test
//    void testRandomVsRandomAi() {
//        long start = System.currentTimeMillis();
//        Game game;
//        for (int i = 0; i < 1000; i++) {
//            Player[] players = new Player[2];
//            players[0] = new RandomPlayer(0, "Random AI 1", 4, "B");
//            players[1] = new RandomPlayer(1, "Random AI 2", 4, "R");
//            game = new Game("/Users/Lucas/thesis-data/random-vs-random.csv", players);
//            game.start();
//        }
//        System.out.println("\n" + (System.currentTimeMillis() - start) + "ms");
//    }
//
//    @Test
//    void testRandomVsGreedyAi() {
//        long start = System.currentTimeMillis();
//        Game game;
//        for (int i = 0; i < 1000; i++) {
//            Player[] players = new Player[2];
//            players[0] = new RandomPlayer(0, "Random AI", 4, "B");
//            players[1] = new GreedyPlayer(1, "Greedy AI", 4, "R");
//            game = new Game("/Users/Lucas/thesis-data/random-vs-greedy.csv", players);
//            game.start();
//        }
//        System.out.println("\n" + (System.currentTimeMillis() - start) + "ms");
//    }
//}
