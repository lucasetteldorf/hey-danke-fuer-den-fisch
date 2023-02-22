package ai;

import game.GameBoard;
import game.Player;

import java.util.List;

public class State {
  GameBoard board;
  Player currentPlayer;

  public State(GameBoard board, Player currentPlayer) {
    this.board = board;
    this.currentPlayer = currentPlayer;
  }

  public List<State> getAllPossibleStates() {
    return null;
  }
}
