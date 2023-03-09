package game.players;

import game.GameBoard;
import game.Move;
import utility.RandomNumbers;

public class MctsPlayer extends BasePlayer {
//    private final Mcts mcts;

    public MctsPlayer(String name, int penguinCount, String penguinColor) {
        super(PlayerType.MCTS, name, penguinCount, penguinColor);
//        mcts = new Mcts();
    }

    public int[] getBestPlacementPosition(GameBoard board) {
        // TODO change to MCTS when implemented
        return board.getAllLegalPlacementPositions().get(RandomNumbers.getRandomIndex(board.getAllLegalPlacementPositions().size()));
    }

    public Move getBestMove(GameBoard board) {
//        return mcts.getNextMove(board);
        return null;
    }
}
