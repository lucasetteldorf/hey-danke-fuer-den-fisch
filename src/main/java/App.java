import game.Game;

public class App {
  public static void main(String[] args) {
    // Start game with human players
    // Game game = new Game();

    // Start game with two baseline AIs (easy vs medium)
    long start = System.currentTimeMillis();
    Game game;
    int numberOfRounds = 1000;
    for (int i = 0; i < numberOfRounds; i++) {
      game = new Game("baseline-test");
      game.startPlacementPhase();
      game.startMovementPhase();
    }
    System.out.println(System.currentTimeMillis() - start + "ms");
  }
}
