package challenge;

import game.logic.GameBoard;
import game.logic.Move;
import game.players.Player;

public class MctsMovement {
  private static final int WIN_SCORE = 1;
  private static final int TIE_SCORE = 0;
  private static final int LOSE_SCORE = -1;
  private final int computationalBudget;
  private final double c;
  MovementHeuristicType type;
  private int numberOfSimulations;
  private boolean printSimulations;
  private Player currentPlayer;
  private int callCount;
  private int totalNumberOfSimulations;

  public MctsMovement() {
    this.computationalBudget = 100;
    this.c = Math.sqrt(2);
    this.type = MovementHeuristicType.NONE;
  }

  public MctsMovement(double c, int computationalBudget) {
    this.c = c;
    this.computationalBudget = computationalBudget;
    this.type = MovementHeuristicType.NONE;
  }

  public MctsMovement(double c, int computationalBudget, MovementHeuristicType type) {
    this.c = c;
    this.computationalBudget = computationalBudget;
    this.type = type;
  }

  public Move getNextMove(GameBoard board) {
    this.currentPlayer = board.getCurrentPlayer();

    numberOfSimulations = 0;

    NodeMovement root = new NodeMovement();
    root.setBoard(board);
    root.initUntriedMoves();
    // init starting tree
    root.expandChildrenMovement();
    long start = System.currentTimeMillis();
    while ((System.currentTimeMillis() - start) < computationalBudget) {
      // 1: Selection
      NodeMovement selectedNode = selectNode(root);

      // 2: Expansion
      NodeMovement expandedNode = selectedNode;
      if (!selectedNode.getBoard().isMovementPhaseOver()) {
        expandedNode = expandNode(selectedNode);
      }

      // 3: Simulation
      int playoutResult = simulatePlayout(expandedNode);

      // 4: Backpropagation
      backpropagate(expandedNode, playoutResult);
    }
    callCount++;
    if (printSimulations) {
      System.out.println(
          callCount
              + ": "
              + numberOfSimulations
              + " movement simulations ("
              + currentPlayer.getName()
              + ")");
    }
    NodeMovement bestNode = (NodeMovement) root.getChildWithMaxVisits();
    return bestNode.getPreviousMove();
  }

  private NodeMovement selectNode(NodeMovement root) {
    NodeMovement node = root;
    while (node.getChildren().size() > 0) {
      if (node.hasUntriedMoves()) {
        break;
      } else {
        node = (NodeMovement) Uct.findBestNode(node, c);
      }
    }
    return node;
  }

  private NodeMovement expandNode(NodeMovement node) {
    Move randomUntriedMove = node.getRandomUntriedMove();
    node.getUntriedMoves().remove(randomUntriedMove);
    NodeMovement expandedNode = new NodeMovement(node);
    expandedNode.setPreviousMove(randomUntriedMove);
    expandedNode.getBoard().movePenguin(randomUntriedMove);
    expandedNode.setParent(node);
    expandedNode.initUntriedMoves();
    node.addChild(expandedNode);
    return expandedNode;
  }

  protected int simulatePlayout(NodeMovement node) {
    NodeMovement tmp = new NodeMovement(node);

    while (!tmp.getBoard().isMovementPhaseOver()) {
      switch (type) {
        case MAX_FISH_ON_NEW_TILE -> tmp.playMaxFishOnNewTile();
        case MAX_NEW_FISH_FOR_PENGUIN -> tmp.playMaxNewFishForPenguin();
        case MAX_NEW_TILES_FOR_PENGUIN -> tmp.playMaxNewTilesForPenguin();
        case MAX_NEW_FISH_PER_TILE_FOR_PENGUIN -> tmp.playMaxNewFishPerTileForPenguin();
        case MAX_NEW_FISH_FOR_ALL_PENGUINS -> tmp.playMaxNewFishForAllPenguins();
        case MAX_NEW_TILES_FOR_ALL_PENGUINS -> tmp.playMaxNewTilesForAllPenguins();
        case MAX_NEW_FISH_PER_TILE_FOR_ALL_PENGUINS -> tmp.playMaxNewFishPerTileForAllPenguins();
        case MIN_NEW_FISH_FOR_OPPONENT_PENGUINS -> tmp.playMinNewFishForOpponentPenguins();
        case MIN_NEW_TILES_FOR_OPPONENT_PENGUINS -> tmp.playMinNewTilesForOpponentPenguins();
        case MIM_NEW_FISH_PER_TILE_FOR_OPPONENT_PENGUINS -> tmp
            .playMinNewFishPerTileForOpponentPenguins();
        case NONE -> tmp.playRandomMove();
      }
    }

    numberOfSimulations++;
    totalNumberOfSimulations++;

    return tmp.getBoard().getWinnerIndex();
  }

  private void backpropagate(NodeMovement expandedNode, int winnerIndex) {
    NodeMovement tmp = expandedNode;
    while (tmp != null) {
      tmp.updateVisits();
      if (winnerIndex == -1) {
        tmp.updateScore(TIE_SCORE);
      } else if (currentPlayer.equals(tmp.getBoard().getPlayers()[winnerIndex])) {
        tmp.updateScore(WIN_SCORE);
      } else if (!currentPlayer.equals(tmp.getBoard().getPlayers()[winnerIndex])) {
        tmp.updateScore(LOSE_SCORE);
      }
      tmp = (NodeMovement) tmp.getParent();
    }
  }

  public int getTotalNumberOfSimulations() {
    return totalNumberOfSimulations;
  }

  public double getAverageSimulations() {
    return (double) totalNumberOfSimulations / callCount;
  }

  public int getCallCount() {
    return callCount;
  }

  public int getComputationalBudget() {
    return computationalBudget;
  }

  public double getC() {
    return c;
  }

  public void enableSimulationPrint() {
    printSimulations = true;
  }

  public void resetStats() {
    callCount = 0;
    totalNumberOfSimulations = 0;
    numberOfSimulations = 0;
  }

  public MovementHeuristicType getType() {
    return type;
  }
}
