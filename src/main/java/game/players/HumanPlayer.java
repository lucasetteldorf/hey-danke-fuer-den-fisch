package game.players;

public class HumanPlayer extends BasePlayer {
    public HumanPlayer(String name, int penguinCount, String penguinColor) {
        super(PlayerType.HUMAN, name, penguinCount, penguinColor);
    }
}
