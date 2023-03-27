package mcts;

import game.GameBoard;import game.Move;public class Mcts {
    private MctsPlacement mctsPlacement;
    private MctsMovement mctsMovement;

    public Mcts() {
        this.mctsPlacement = new MctsPlacement();
        this.mctsMovement = new MctsMovement();
    }

    public int[] computeBestPlacementPosition(GameBoard board) {
        return mctsPlacement.getNextPlacementPosition(board);
    }

    public Move computeBestMove(GameBoard board) {
        return mctsMovement.getNextMove(board);
    }
}
