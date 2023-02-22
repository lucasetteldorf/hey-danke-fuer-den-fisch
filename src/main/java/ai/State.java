package ai;

import game.GameBoard;
import game.HumanPlayer;

import java.util.List;

public class State {
  GameBoard board;
  HumanPlayer currentHumanPlayer;

  public State(GameBoard board, HumanPlayer currentHumanPlayer) {
    this.board = board;
    this.currentHumanPlayer = currentHumanPlayer;
  }

  public List<State> getAllPossibleStates() {
    return null;
  }
}
