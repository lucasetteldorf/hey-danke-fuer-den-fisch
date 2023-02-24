package ai;

import game.GameBoard;
import game.Penguin;
import game.Player;

public class GreedyAiPlayer extends RandomAiPlayer {
    public GreedyAiPlayer(String name) {
        super(name);
    }

    @Override
    public void movePenguin(GameBoard board) {
        for (Penguin penguin : this.getPenguins()) {
            if (penguin.isOnGameBoard() && board.hasPenguinLegalMoves(penguin)) {
                // TODO move to tile with as many fish as possible (random if all have the same amount of fish)
            }
        }
    }
}
