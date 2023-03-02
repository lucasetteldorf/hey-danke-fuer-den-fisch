package ai.mcts;

import game.GameBoard;
import game.IceFloeTile;
import game.Penguin;
import game.Player;

import java.util.ArrayList;
import java.util.List;

public class State {
  private GameBoard board;
  private Player currentPlayer;

  // TODO make DEEP COPY of board and player
  public State(State state) {
    // TODO these are only shallow copies
    this.board = state.board;
    this.currentPlayer = state.currentPlayer;
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


  // TODO maybe needs to separated for the two phases
  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<>();

    for (Penguin penguin : currentPlayer.getPenguins()) {
        if (penguin.isOnGameBoard()) {
          for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
            IceFloeTile tile = board.getTile(coordinates[0], coordinates[1]);
            // place or move penguin to generate new state with copy constructor

          }
        }
    }

    return possibleStates;
  }
}
