package game;

import org.junit.jupiter.api.Test;

public class BaselineAiTest {
    @Test
    void testRandomAi() {
        Game game;
        for (int i = 0; i < 1000; i++) {
            game = new Game("random-test");
            game.start();
        }
    }

    @Test
    void testRandomVsGreedyAi() {
        long start = System.currentTimeMillis();
        Game game;
        for (int i = 0; i < 1000; i++) {
            game = new Game("random-greedy-test");
            game.start();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
