package mcts;

import game.GameBoard;
import game.Move;
import java.util.List;
import utility.RandomNumbers;

public class State {
  private GameBoard board;
  private Move previousMove;
  private int[] previousPlacement;

  public State() {
    this.board = new GameBoard();
    previousPlacement = new int[2];
  }

  // copy constructor
  public State(State state) {
    this.board = new GameBoard(state.board);
    if (state.previousMove != null) {
      this.previousMove = new Move(state.previousMove);
    }
    if (state.previousPlacement != null) {
      this.previousPlacement = new int[] {state.previousPlacement[0], state.previousPlacement[1]};
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

  public int[] getPreviousPlacement() {
    return previousPlacement;
  }

  public void setPreviousPlacement(int[] previousPlacement) {
    this.previousPlacement = previousPlacement;
  }

  public boolean isTerminalState() {
    return board.isMovementPhaseOver();
  }
  
  public void playRandomMove() {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    Move randomMove = possibleMoves.get(RandomNumbers.getRandomIndex(possibleMoves.size()));
    board.movePenguin(randomMove);
  }

  public void playRandomPlacement() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int[] randomPlacement =
        possiblePlacements.get(RandomNumbers.getRandomIndex(possiblePlacements.size()));
    board.placePenguin(randomPlacement[0], randomPlacement[1]);
  }
}
