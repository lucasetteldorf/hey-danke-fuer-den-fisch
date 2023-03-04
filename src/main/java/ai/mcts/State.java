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

  // copy constructor
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


  // TODO only works for second phase (movement)
  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<>();

    for (Penguin penguin : currentPlayer.getPenguins()) {
        if (penguin.isOnGameBoard() && board.hasPenguinLegalMoves(penguin)) {
          for (int[] coordinates : board.getAllLegalMovesForPenguin(penguin)) {
            State newState = new State(this);
            newState.getBoard().movePenguin(penguin, coordinates[0], coordinates[1]);
            possibleStates.add(newState);
          }
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
