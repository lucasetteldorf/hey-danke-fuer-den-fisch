import game.Game;

public class App {
  public static void main(String[] args) {
    Game game = Game.getInstance();
    System.out.println();
    game.startPlacementPhase();
    game.startMovementPhase();
    game.printAllScores();
  }
}
