package game.players;

import java.util.HashMap;

public enum PlayerType {
  HUMAN,
  RANDOM,
  GREEDY,
  MCTS;

  private static final HashMap<Integer, PlayerType> numberToType;
  static {
    numberToType = new HashMap<>();
    numberToType.put(1, HUMAN);
    numberToType.put(2, RANDOM);
    numberToType.put(3, GREEDY);
    numberToType.put(4, MCTS);
  }

  public static PlayerType getTypeFromNumber(int number) {
    return numberToType.get(number);
  }
}
