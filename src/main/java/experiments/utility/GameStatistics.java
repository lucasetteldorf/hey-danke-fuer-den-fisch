package experiments.utility;

public class GameStatistics {
  private final int numberOfGames;

  private final int[] playerFishCounts;
  private final int[] playerMoveCounts;
  private final int[] playerWinCounts;
  private final boolean isMctsInGame;
  private int ties;
  private final double[] averageMctsPlacementSimulations;
  private final double[] averageMctsMovementSimulations;
  private final double[] averageMctsTotalSimulations;

  public GameStatistics(int numberOfGames, int playerCount, boolean isMctsInGame) {
    this.numberOfGames = numberOfGames;
    this.playerFishCounts = new int[playerCount];
    this.playerMoveCounts = new int[playerCount];
    this.playerWinCounts = new int[playerCount];
    this.averageMctsPlacementSimulations = new double[playerCount];
    this.averageMctsMovementSimulations = new double[playerCount];
    this.averageMctsTotalSimulations = new double[playerCount];
    this.isMctsInGame = isMctsInGame;
  }

  public int getNumberOfGames() {
    return numberOfGames;
  }

  public void updatePlayerWinCount(int playerIndex) {
    if (playerIndex != -1) {
      playerWinCounts[playerIndex]++;
    } else {
      ties++;
    }
  }

  public void updatePlayerFishCount(int playerIndex, int fishCount) {
    playerFishCounts[playerIndex] += fishCount;
  }

  public void updatePlayerMoveCount(int playerIndex, int moveCount) {
    playerMoveCounts[playerIndex] += moveCount;
  }

  public void updateAverageMctsPlacementSimulations(
      int playerIndex, double averagePlacementSimulations) {
    averageMctsPlacementSimulations[playerIndex] += averagePlacementSimulations;
  }

  public void updateAverageMctsMovementSimulations(
      int playerIndex, double averageMovementSimulations) {
    averageMctsMovementSimulations[playerIndex] += averageMovementSimulations;
  }

  public void updateAverageMctsTotalSimulations(int playerIndex, double averageTotalSimulations) {
    averageMctsTotalSimulations[playerIndex] += averageTotalSimulations;
  }

  public boolean isMctsInGame() {
    return isMctsInGame;
  }

  public double getAverageFishCount(int playerIndex) {
    return (double) playerFishCounts[playerIndex] / numberOfGames;
  }

  public double getAverageMoveCount(int playerIndex) {
    return (double) playerMoveCounts[playerIndex] / numberOfGames;
  }

  public double getWinRate(int playerIndex) {
    return (double) playerWinCounts[playerIndex] / numberOfGames;
  }

  public double getAverageMctsPlacementSimulations(int playerIndex) {
    return averageMctsPlacementSimulations[playerIndex] / numberOfGames;
  }

  public double getAverageMctsMovementSimulations(int playerIndex) {
    return averageMctsMovementSimulations[playerIndex] / numberOfGames;
  }

  public double getAverageMctsTotalSimulations(int playerIndex) {
    return averageMctsTotalSimulations[playerIndex] / numberOfGames;
  }

  public int getTies() {
    return ties;
  }

  public double getTiePercentage() {
    return (double) ties / numberOfGames;
  }
}
