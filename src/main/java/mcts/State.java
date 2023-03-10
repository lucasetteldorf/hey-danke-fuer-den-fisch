package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class State {
    private GameBoard board;
    private Player currentPlayer;
    private Move previousMove;

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
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Move getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(Move previousMove) {
        this.previousMove = previousMove;
    }

    // TODO only works for second phase (movement)
    public List<State> getAllPossibleStates(Player opponent) {
        List<State> possibleStates = new ArrayList<>();
        for (Move move : board.getAllLegalMovesForPlayer(currentPlayer)) {
            State newState = new State(this);
            newState.setPreviousMove(move);
            newState.getBoard().movePenguin(currentPlayer, move);
            // TODO may need to be adjusted (add game to board object and access method to get next player)
            newState.setCurrentPlayer(opponent);
            possibleStates.add(newState);
        }
        return possibleStates;
    }

    // TODO only works for second phase (movement)
    public void randomPlay() {
        List<Move> allPossibleMoves = this.board.getAllLegalMovesForPlayer(currentPlayer);
        Move randomMove = allPossibleMoves.get(RandomNumbers.getRandomIndex(allPossibleMoves.size()));
        // TODO also set next player?
        board.movePenguin(currentPlayer, randomMove);
    }
}
