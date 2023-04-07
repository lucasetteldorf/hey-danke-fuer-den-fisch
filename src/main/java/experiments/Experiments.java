package experiments;

public class Experiments {
    public static void main(String[] args){
        MctsHeavyPlayoutPlacement.startExperiments(2000, 50);
        MctsHeavyPlayoutMovement.startExperiments();
    }
}
