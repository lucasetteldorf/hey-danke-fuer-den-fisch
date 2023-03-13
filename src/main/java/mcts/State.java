package mcts;

import game.GameBoard;
import game.Move;
import game.players.Player;
import java.util.ArrayList;
import java.util.List;
import utility.RandomNumbers;

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
    // TODO shallow copy sufficient?
    this.currentPlayer = state.getCurrentPlayer();
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
  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<>();
    for (Move move : board.getAllLegalMovesForPlayer(currentPlayer)) {
      State newState = new State(this);
      newState.setPreviousMove(move);
      // TODO PROBLEM: player gets all tiles and fish because of these calls!!! create extra method
      newState.getBoard().movePenguin(currentPlayer, move);
      // TODO working as intended?
      newState.setCurrentPlayer(board.getGame().getNextPlayer());
      possibleStates.add(newState);
    }
    return possibleStates;
  }

  public State getRandomPossibleState() {
    List<State> possibleStates = getAllPossibleStates();
    return possibleStates.get(RandomNumbers.getRandomIndex(possibleStates.size()));
  }

  // TODO only works for second phase (movement)
  public void playRandomMove() {
    List<Move> allPossibleMoves = this.board.getAllLegalMovesForPlayer(this.currentPlayer);
    Move randomMove = allPossibleMoves.get(RandomNumbers.getRandomIndex(allPossibleMoves.size()));
    // TODO also set next player?
    board.movePenguin(currentPlayer, randomMove);
    // TODO working as intended?
    currentPlayer = board.getGame().getNextPlayer();
  }
}
