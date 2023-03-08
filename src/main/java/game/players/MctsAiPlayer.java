package game.players;

import game.GameBoard;
import game.Move;
import mcts.Mcts;

public class MctsAiPlayer extends Player {
    private final Mcts mcts;

    public MctsAiPlayer(int index, String name, int penguinCount, String penguinColor) {
        super(PlayerType.MCTS_AI, index, name, penguinCount, penguinColor);
        mcts = new Mcts();
    }

    public int[] getBestPlacementPosition(GameBoard board) {
        return null;
    }

    public Move getBestMove(GameBoard board) {
        GameBoard bestMoveBoard = mcts.getNextMove(board);
        return null;
    }
}
