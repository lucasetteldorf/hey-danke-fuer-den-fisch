package experiments;

public class Experiments {
  public static void main(String[] args) {
    int numberOfGames = 20;
    double c = 1 / Math.sqrt(2);
    int simulationTime = 10;

    MctsHeavyPlayoutPlacement.startExperiments(numberOfGames, c, simulationTime);
    //        MctsHeavyPlayoutMovement.startExperiments(1000, 10);
  }
}
