package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class State {
    private GameBoard board;
    private Move previousMove;

    public State() {
        this.board = new GameBoard();
    }

    // copy constructor
    public State(State state) {
        this.board = new GameBoard(state.board);
        this.previousMove = state.previousMove;
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

    public Move getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(Move previousMove) {
        this.previousMove = previousMove;
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

        for (Move move : board.getAllLegalMovesForPlayer(board.getCurrentPlayer())) {
            if (board.hasPenguinLegalMoves(move.getPenguin())) {
                // TODO problem with copy constructor (penguin is null)
                State newState = new State(this);
                newState.setPreviousMove(move);
                // current player moves penguin to position and next player is set
                // TODO working as intended?
                newState.getBoard().movePenguin(move.getPenguin().getPosition()[0], move.getPenguin().getPosition()[1], move.getRowIndex(), move.getColIndex());
                possibleStates.add(newState);
            }
        }

        return possibleStates;
    }

    // TODO only works for second phase (movement)
    public void randomPlay() {
        List<Move> allPossibleMoves = this.board.getAllLegalMovesForPlayer(board.getCurrentPlayer());
        Move randomMove = allPossibleMoves.get(RandomNumbers.getRandomIndex(allPossibleMoves.size()));
        // TODO moves penguin, but also sets next player (may not be needed)
        this.board.movePenguin(randomMove.getPenguin().getPosition()[0], randomMove.getPenguin().getPosition()[1], randomMove.getRowIndex(), randomMove.getColIndex());
    }
}
