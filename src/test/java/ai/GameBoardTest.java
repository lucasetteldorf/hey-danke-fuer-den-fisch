package ai;

import game.logic.GameBoard;
import game.logic.Move;
import game.players.Player;
import game.players.PlayerType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {
  static final int[] fishCounts =
      new int[] {
        3, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 3, 2, 3, 2, 3, 1,
        1, 3, 1, 2, 2, 1, 2, 2, 3, 3, 2, 2, 1, 2, 1, 2, 2, 3, 1, 1, 2, 1, 3, 1, 1, 1, 1, 2, 1
      };
  static Player p1;
  static Player p2;
  static GameBoard board;

  void initBoard() {
    p1 = new Player(PlayerType.MCTS, "P1", 4, "B");
    p2 = new Player(PlayerType.MCTS, "P2", 4, "R");
    board = new GameBoard(new Player[] {p1, p2}, fishCounts);
  }

  @BeforeEach
  void beforeEach() {
    initBoard();
  }

  @Test
  void testPlayerPositions() {
    board.placePenguin(0, 3);
    board.placePenguin(0, 5);
    board.placePenguin(0, 7);
    board.placePenguin(0, 9);
    board.placePenguin(0, 11);
    board.placePenguin(0, 13);
    board.placePenguin(1, 0);
    board.placePenguin(1, 4);
    board.printBoard();
    List<int[]> p1Positions = board.getAllPenguinPositionsForPlayer(p1);
    List<int[]> p2Positions = board.getAllPenguinPositionsForPlayer(p2);
    board.movePenguin(new Move(new int[] {0, 3}, new int[] {1, 2}));
    board.movePenguin(new Move(new int[] {0, 5}, new int[] {1, 6}));
    p1Positions = board.getAllPenguinPositionsForPlayer(p1);
    p2Positions = board.getAllPenguinPositionsForPlayer(p2);
  }

  @Test
  void testThreeFishTilesCountForPenguin() {
    board.placePenguin(2, 9);
    board.placePenguin(2, 7);
    board.printBoard();
    int threeFishTilesCount = board.getThreeFishTilesCountForPenguin(new int[] {1, 8});
  }

  @Test
  void testThreeFishTilesCountForAllPenguins() {
    board.placePenguin(p1, 0, 3);
    List<int[]> positions = new ArrayList<>();
    positions.add(new int[] {0, 5});
    positions.add(new int[] {2, 7});
    int threeFishTilesCount = board.getThreeFishTilesForAllPenguins(positions);
  }

  @Test
  void testReachableFishCountForPenguin() {
    int reachableFishCount = board.getReachableFishCountForPenguin(new int[] {0, 1});
    board.placePenguin(0, 3);
    reachableFishCount = board.getReachableFishCountForPenguin(new int[] {0, 1});
  }

  @Test
  void getReachableTilesForPenguin() {
    int reachableTiles = board.getReachableTilesForPenguin(new int[] {0, 1});
    board.placePenguin(0, 3);
    reachableTiles = board.getReachableTilesForPenguin(new int[] {0, 1});
  }

  @Test
  void testReachableFishCountForAllPenguins() {
    List<int[]> positions = new ArrayList<>();
    positions.add(new int[] {0, 3});
    positions.add(new int[] {0, 5});
    int reachableFishCount = board.getReachableFishCountForAllPenguins(positions);
  }

  @Test
  void testReachableTilesForAllPenguins() {
    board.placePenguin(0, 3);
    board.placePenguin(0, 5);
    List<int[]> positions = new ArrayList<>();
    positions.add(new int[] {0, 3});
    positions.add(new int[] {0, 5});
    int reachableFishCount = board.getReachableTilesForAllPenguins(positions);
  }

  @Test
  void testReachableTileCount() {
    board.printReachableTileCount();
  }
}
