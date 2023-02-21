import game.Game;

public class App {
  public static void main(String[] args) {
    Game game = Game.getInstance();
    game.startPlacementPhase();
    game.startMovementPhase();
  }
}
