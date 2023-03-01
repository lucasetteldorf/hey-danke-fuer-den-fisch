import game.Game;

public class App {
  public static void main(String[] args) {
    // Start game with human players
    Game game = Game.getInstance();
    game.startPlacementPhase();
    game.startMovementPhase();
  }
}
