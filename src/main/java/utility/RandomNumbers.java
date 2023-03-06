package utility;

import java.util.Random;

public class RandomNumbers {
    private final static Random random = new Random();

    public static int getRandomNumber(int lowerBound, int upperBound) {
        return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    public static int getRandomIndex (int upperBound) {
        return (upperBound > 0) ? random.nextInt(upperBound) : 0;
    }
}
