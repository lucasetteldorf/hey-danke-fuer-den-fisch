package utility;

import java.util.Random;

public class RandomUtility {
  private static final Random random = new Random();

  public static int getRandomIndex(int upperBound) {
    return (upperBound > 0) ? random.nextInt(upperBound) : 0;
  }
}
