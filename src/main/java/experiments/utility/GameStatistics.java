package experiments.utility;

public class GameStatistics {
  private final int numberOfGames;

  private final int[] playerFishCounts;
  private final int[] playerMoveCounts;
  private final int[] playerWinCounts;
  private final boolean isMctsInGame;
  private int ties;
  private double averageMctsPlacementSimulations;
  private double averageMctsMovementSimulations;
  private double averageMctsTotalSimulations;

  public GameStatistics(int numberOfGames, int playerCount, boolean isMctsInGame) {
    this.numberOfGames = numberOfGames;
    this.playerFishCounts = new int[playerCount];
    this.playerMoveCounts = new int[playerCount];
    this.playerWinCounts = new int[playerCount];
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

  public void updateAverageMctsPlacementSimulations(double averagePlacementSimulations) {
    averageMctsPlacementSimulations += averagePlacementSimulations;
  }

  public void updateAverageMctsMovementSimulations(double averageMovementSimulations) {
    averageMctsMovementSimulations += averageMovementSimulations;
  }

  public void updateAverageMctsTotalSimulations(double averageTotalSimulations) {
    averageMctsTotalSimulations += averageTotalSimulations;
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

  public double getAverageMctsPlacementSimulations() {
    return averageMctsPlacementSimulations / numberOfGames;
  }

  public double getAverageMctsMovementSimulations() {
    return averageMctsMovementSimulations / numberOfGames;
  }

  public double getAverageMctsTotalSimulations() {
    return averageMctsTotalSimulations / numberOfGames;
  }

  public int getTies() {
    return ties;
  }

  public double getTiePercentage() {
    return (double) ties / numberOfGames;
  }
}
