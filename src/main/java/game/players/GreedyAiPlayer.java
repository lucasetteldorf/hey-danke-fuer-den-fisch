package game.players;

import game.GameBoard;
import game.IceFloeTile;
import game.Penguin;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.List;

import static utility.RandomNumbers.getRandomIndex;

public class GreedyAiPlayer extends Player {
    public GreedyAiPlayer(String name, int penguinCount, String penguinColor) {
        super(PlayerType.GREEDY_AI, name, penguinCount, penguinColor);
    }

    public int[] getBestPenguinPosition(GameBoard board) {
        int maxThreeFishCount = 0;
        int maxTwoFishCount = 0;
        Penguin bestPenguin = getPenguinByIndex(getRandomIndex(this.getPenguins().length));

        for (Penguin penguin : this.getPenguins()) {
            List<IceFloeTile> threeFishTiles = new ArrayList<>();
            List<IceFloeTile> twoFishTiles = new ArrayList<>();
            for (int[] positions : board.getLegalMovesForPenguin(penguin)) {
                IceFloeTile tile = board.getTile(positions[0], positions[1]);
                if (tile != null) {
                    switch (tile.getFishCount()) {
                        case 3:
                            threeFishTiles.add(tile);
                            break;
                        case 2:
                            twoFishTiles.add(tile);
                            break;
                    }
                }
            }

            if (threeFishTiles.size() > maxThreeFishCount) {
                maxThreeFishCount = threeFishTiles.size();
                bestPenguin = penguin;
            } else if (threeFishTiles.size() <= maxThreeFishCount
                    && twoFishTiles.size() > maxTwoFishCount) {
                maxTwoFishCount = twoFishTiles.size();
                bestPenguin = penguin;
            }
        }

        return bestPenguin.getPosition();
    }

    public int[] getBestMovementPosition(GameBoard board, Penguin penguin) {
        List<IceFloeTile> threeFishTiles = new ArrayList<>();
        List<IceFloeTile> twoFishTiles = new ArrayList<>();
        List<IceFloeTile> oneFishTiles = new ArrayList<>();
        for (int[] positions : board.getLegalMovesForPenguin(penguin)) {
            IceFloeTile tile = board.getTile(positions[0], positions[1]);
            if (tile != null) {
                switch (tile.getFishCount()) {
                    case 3:
                        threeFishTiles.add(tile);
                        break;
                    case 2:
                        twoFishTiles.add(tile);
                        break;
                    case 1:
                        oneFishTiles.add(tile);
                        break;
                }
            }
        }

        // TODO index out of bounds exception (0)
        int[] bestPosition = board.getLegalMovesForPenguin(penguin).get(RandomNumbers.getRandomIndex(board.getLegalMovesForPenguin(penguin).size()));
        if (!threeFishTiles.isEmpty()) {
            bestPosition = threeFishTiles.get(getRandomIndex(threeFishTiles.size())).getPosition();
        } else if (!twoFishTiles.isEmpty()) {
            bestPosition = twoFishTiles.get(getRandomIndex(twoFishTiles.size())).getPosition();
        } else if (!oneFishTiles.isEmpty()) {
            bestPosition = oneFishTiles.get(getRandomIndex(oneFishTiles.size())).getPosition();
        }

        return bestPosition;
    }
}
