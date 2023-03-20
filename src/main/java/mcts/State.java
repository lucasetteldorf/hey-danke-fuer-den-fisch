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
    this.currentPlayer = new Player(state.currentPlayer);
    // previousMove at root is null
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
      State state = new State(this);
      state.setPreviousMove(move);
      state.getBoard().movePenguin(state.getCurrentPlayer(), move);
      // TODO working as intended?
      state.setCurrentPlayer(board.getNextPlayer());
      possibleStates.add(state);
    }
    return possibleStates;
  }

  // TODO only works for second phase (movement)
  public void playRandomMove() {
    // TODO problem: at this point legal moves is always empty because penguin of player have made
    // TODO all the moves of the calculated possible moves -> calling getAllPossibleStates() causes
    // TODO problems because the tiles, penguins and players are updated
    List<Move> possibleMoves = board.getAllLegalMovesForPlayer(currentPlayer);
    Move randomMove = possibleMoves.get(RandomNumbers.getRandomIndex(possibleMoves.size()));
    // TODO working as intended?
    board.movePenguin(currentPlayer, randomMove);
    currentPlayer = board.getNextPlayer();
  }
}
