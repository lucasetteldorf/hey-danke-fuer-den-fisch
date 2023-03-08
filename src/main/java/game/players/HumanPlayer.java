package game.players;

public class HumanPlayer extends Player {
    public HumanPlayer(int index, String name, int penguinCount, String penguinColor) {
        super(PlayerType.HUMAN, index, name, penguinCount, penguinColor);
    }
}
