package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class State {
    private GameBoard board;
    // TODO maybe add Move that lead to this state

    public State() {
        this.board = new GameBoard();
    }

    // copy constructor
    public State(State state) {
        this.board = new GameBoard(state.board);
    }

    public State(GameBoard board) {
        this.board = new GameBoard(board);
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return this.board.getCurrentPlayer();
    }

    public Player getNextPlayer() {
        return this.board.getPlayerByIndex(this.board.getNextPlayerIndex());
    }

    // TODO only works for second phase (movement)
    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();

        for (Move move : board.getLegalMovesForPlayer(board.getCurrentPlayer())) {
            if (board.hasPenguinLegalMoves(move.getPenguin())) {
                // create (deep) copy of the state
                State newState = new State(this);
                // current player moves penguin to position and next player is set
                // TODO working as intended?
                newState.getBoard().moveCurrentPlayerPenguin(move.getPenguin().getPosition()[0], move.getPenguin().getPosition()[1], move.getRowIndex(), move.getColIndex());
                possibleStates.add(newState);
            }
        }

        return possibleStates;
    }

    // TODO only works for second phase (movement)
    public void randomPlay() {
        List<Move> allPossibleMoves = this.board.getLegalMovesForPlayer(board.getCurrentPlayer());
        Move randomMove = allPossibleMoves.get(RandomNumbers.getRandomIndex(allPossibleMoves.size()));
        // TODO moves penguin, but also sets next player (may not be needed)
        this.board.moveCurrentPlayerPenguin(randomMove.getPenguin().getPosition()[0], randomMove.getPenguin().getPosition()[1], randomMove.getRowIndex(), randomMove.getColIndex());
    }
}
