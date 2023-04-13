package experiments;

public class Experiments {
  public static void main(String[] args) {
    int numberOfGames = 1000;
    double c = Math.sqrt(2);
    int simulationTime = 50;

    MctsLightPlayoutSimulationTimes.startExperiments(numberOfGames, c);

    //    MctsLightPlayoutCValue.startExperiments(numberOfGames, simulationTime);

    //    MctsHeavyPlayoutPlacement.startExperiments(numberOfGames, c, simulationTime);

    //    MctsHeavyPlayoutMovement.startExperiments(numberOfGames, c, simulationTime);
  }
}
