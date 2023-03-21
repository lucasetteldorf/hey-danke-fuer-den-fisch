package mcts;

import game.GameBoard;
import game.Move;
import java.util.ArrayList;
import java.util.List;
import utility.RandomNumbers;

public class State {
  private GameBoard board;
  private Move previousMove;

  public State() {
    this.board = new GameBoard();
  }

  // copy constructor
  public State(State state) {
    this.board = new GameBoard(state.board);
    if (state.previousMove != null) {
      this.previousMove = new Move(state.previousMove);
    }
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

  public boolean isTerminalState() {
    return board.isMovementPhaseOver();
  }

  // TODO only works for second phase (movement)
  // TODO SHOULD ONLY BE CALLED ONCE PER STATE
  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<>();
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      State state = new State(this);
      state.setPreviousMove(move);
      // TODO current player performs move and next player is set WORKING?
      state.getBoard().movePenguin(move);
      possibleStates.add(state);
    }
    return possibleStates;
  }

  // TODO only works for second phase (movement)
  public void playRandomMove() {
    // TODO problem: at this point legal moves is always empty because penguin of player have made
    // TODO all the moves of the calculated possible moves -> calling getAllPossibleStates() causes
    // TODO problems because the tiles, penguins and players are updated^
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    Move randomMove = possibleMoves.get(RandomNumbers.getRandomIndex(possibleMoves.size()));
    // TODO current player performs move and next player is set
    board.movePenguin(randomMove);
  }
}
