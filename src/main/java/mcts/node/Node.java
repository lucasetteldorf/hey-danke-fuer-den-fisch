package mcts.node;

import game.logic.GameBoard;
import game.logic.Move;
import java.util.*;
import utility.RandomUtility;

public class Node {
  private final List<Node> children;
  protected GameBoard board;
  private Node parent;
  private int visits;
  private double score;

  public Node() {
    this.children = new ArrayList<>();
  }

  public Node(GameBoard board) {
    this.board = new GameBoard(board);
    this.children = new ArrayList<>();
  }

  // copy constructor
  public Node(Node node) {
    if (node.parent != null) {
      this.parent = node.parent;
    }
    this.children = new ArrayList<>();
    this.children.addAll(node.getChildren());
    this.board = new GameBoard(node.board);
    this.visits = node.visits;
    this.score = node.score;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public List<Node> getChildren() {
    return children;
  }

  public GameBoard getBoard() {
    return board;
  }

  public void setBoard(GameBoard board) {
    this.board = board;
  }

  public int getVisits() {
    return visits;
  }

  public void updateVisits() {
    this.visits++;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public void updateScore(double score) {
    this.score += score;
  }

  public void addChild(Node node) {
    children.add(node);
  }

  public Node getChildWithMaxVisits() {
    return Collections.max(children, Comparator.comparing(Node::getVisits));
  }

  public void placeRandom() {
    List<int[]> possiblePlacements = board.getAllLegalPlacementPositions();
    int[] randomPlacement =
        possiblePlacements.get(RandomUtility.getRandomIndex(possiblePlacements.size()));
    board.placePenguin(randomPlacement[0], randomPlacement[1]);
  }

  public void placeMaxOwnReachableThreeFishTiles() {
    int[] bestPlacementPosition = null;
    int maxThreeFishTiles = Integer.MIN_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int threeFishTiles =
          newBoard.getThreeFishTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (threeFishTiles > maxThreeFishTiles) {
        maxThreeFishTiles = threeFishTiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void placeMinEnemyReachableThreeFishTiles() {
    int[] bestPlacementPosition = null;
    int minThreeFishTiles = Integer.MAX_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int threeFishTiles =
          newBoard.getThreeFishTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (threeFishTiles < minThreeFishTiles) {
        minThreeFishTiles = threeFishTiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void placeMaxOwnReachableFishCount() {
    int[] bestPlacementPosition = null;
    int maxFishCount = Integer.MIN_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void placeMinEnemyReachableFishCount() {
    int[] bestPlacementPosition = null;
    int minFishCount = Integer.MAX_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (fishCount < minFishCount) {
        minFishCount = fishCount;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void placeMaxOwnReachableTiles() {
    int[] bestPlacementPosition = null;
    int maxTiles = Integer.MIN_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int tiles =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (tiles > maxTiles) {
        maxTiles = tiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void placeMinEnemyReachableTiles() {
    int[] bestPlacementPosition = null;
    int minTiles = Integer.MAX_VALUE;
    List<int[]> placementPositions = board.getAllLegalPlacementPositions();
    for (int[] placementPosition : placementPositions) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.placePenguin(placementPosition[0], placementPosition[1]);
      int tiles =
          newBoard.getReachableTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (tiles < minTiles) {
        minTiles = tiles;
        bestPlacementPosition = placementPosition;
      }
    }
    board.placePenguin(bestPlacementPosition[0], bestPlacementPosition[1]);
  }

  public void moveRandom() {
    List<Move> possibleMoves = board.getAllLegalMovesForCurrentPlayer();
    Move randomMove = possibleMoves.get(RandomUtility.getRandomIndex(possibleMoves.size()));
    board.movePenguin(randomMove);
  }

  public void moveMaxNewTileFishCount() {
    Move bestMove = null;
    int maxFishCount = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      int[] newPosition = move.getNewPosition();
      int fishCount = board.getFishCountByPosition(newPosition);
      if (fishCount != 0 && fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void moveMaxOwnReachableThreeFishTiles() {
    Move bestMove = null;
    int maxThreeFishTiles = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int threeFishTiles =
          newBoard.getThreeFishTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (threeFishTiles > maxThreeFishTiles) {
        maxThreeFishTiles = threeFishTiles;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void moveMinEnemyReachableThreeFishTiles() {
    Move bestMove = null;
    int minThreeFishTiles = Integer.MAX_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int threeFishTiles =
          newBoard.getThreeFishTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (threeFishTiles < minThreeFishTiles) {
        minThreeFishTiles = threeFishTiles;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void moveMaxOwnReachableFishCount() {
    Move bestMove = null;
    int maxFishCount = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (fishCount > maxFishCount) {
        maxFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void moveMinEnemyReachableFishCount() {
    Move bestMove = null;
    int minFishCount = Integer.MAX_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int fishCount =
          newBoard.getReachableFishCountForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (fishCount < minFishCount) {
        minFishCount = fishCount;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void moveMaxOwnReachableTiles() {
    Move bestMove = null;
    int maxTiles = Integer.MIN_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int tiles =
          newBoard.getReachableTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForPlayer(board.getCurrentPlayer()));
      if (tiles > maxTiles) {
        maxTiles = tiles;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }

  public void moveMinEnemyReachableTiles() {
    Move bestMove = null;
    int minTiles = Integer.MAX_VALUE;
    for (Move move : board.getAllLegalMovesForCurrentPlayer()) {
      GameBoard newBoard = new GameBoard(board);
      newBoard.movePenguin(move);
      int tiles =
          newBoard.getReachableTilesForAllPenguins(
              newBoard.getAllPenguinPositionsForOpponents(board.getCurrentPlayer()));
      if (tiles < minTiles) {
        minTiles = tiles;
        bestMove = move;
      }
    }
    board.movePenguin(bestMove);
  }
}
