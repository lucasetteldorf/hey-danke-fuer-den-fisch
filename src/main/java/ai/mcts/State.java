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

  public State(State state) {
    this.board = new GameBoard(state.board);
    this.currentPlayer = new Player(state.currentPlayer);
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


  // TODO maybe needs to separated for the two phases, this only focuses on movement
  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<>();

    for (Penguin penguin : currentPlayer.getPenguins()) {
        if (penguin.isOnGameBoard()) {
          for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
            State newState = new State(this);
            newState.getBoard().movePenguin(penguin, coordinates[0], coordinates[1]);
            possibleStates.add(newState);
          }
        }
    }

    return possibleStates;
  }

  public void randomPlay() {

  }
}
