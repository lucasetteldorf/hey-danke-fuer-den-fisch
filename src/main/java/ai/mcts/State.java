package ai.mcts;

import game.GameBoard;
import game.Move;
import game.Penguin;
import game.Player;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class State {
    private GameBoard board;
    private Player currentPlayer;
    private int visitCount;
    private double score;

    public State() {
        this.board = new GameBoard();
    }

    // copy constructor
    public State(State state) {
        this.board = new GameBoard(state.board);
        this.currentPlayer = new Player(state.currentPlayer);
        this.visitCount = state.visitCount;
        this.score = state.score;
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

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    // TODO only works for second phase (movement)
    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();

        for (Move move : board.getAllLegalMovesForPlayer(this.currentPlayer)) {
            if (move.getPenguin().isOnGameBoard() && board.hasPenguinLegalMoves(move.getPenguin())) {
                State newState = new State(this);
                newState.getBoard().movePenguin(move.getPenguin(), move.getRowIndex(), move.getColIndex());
                // TODO working?
                possibleStates.add(newState);
            }
        }

        return possibleStates;
    }

    // TODO only works for second phase (movement)
    public void randomPlay() {
        List<Move> allPossibleMoves = this.board.getAllLegalMovesForPlayer(this.currentPlayer);
        Move randomMove = allPossibleMoves.get(RandomNumbers.getRandomIndex(allPossibleMoves.size()));
        this.board.movePenguin(randomMove.getPenguin(), randomMove.getRowIndex(), randomMove.getColIndex());
    }
}
