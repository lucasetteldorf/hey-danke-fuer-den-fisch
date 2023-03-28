package experiments.utility;

public class PlayerUtility {
  private final int numberOfGames;

  private final int[] playerFishCounts;
  private final int[] playerMoveCounts;
  private final int[] playerWinCounts;

  public PlayerUtility(int numberOfGames, int playerCount) {
    this.numberOfGames = numberOfGames;
    this.playerFishCounts = new int[playerCount];
    this.playerMoveCounts = new int[playerCount];
    this.playerWinCounts = new int[playerCount];
  }

  public int getNumberOfGames() {
    return numberOfGames;
  }

  public void updatePlayerWinCount(int playerIndex) {
    if (playerIndex != -1) {
      playerWinCounts[playerIndex]++;
    }
  }

  public void updatePlayerFishCount(int playerIndex, int fishCount) {
    playerFishCounts[playerIndex] += fishCount;
  }

  public void updatePlayerMoveCount(int playerIndex, int moveCount) {
    playerMoveCounts[playerIndex] += moveCount;
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
}
