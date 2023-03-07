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
        List<Penguin> threeFishPenguins = new ArrayList<>();
        List<Penguin> twoFishPenguins = new ArrayList<>();
        List<Penguin> oneFishPenguins = new ArrayList<>();

        for (Penguin penguin : this.getPenguins()) {
            if (penguin.isOnGameBoard()) {
                for (int[] position : board.getLegalMovesForPenguin(penguin)) {
                    switch (board.getTile(position[0], position[1]).getFishCount()) {
                        case 3:
                            if (!threeFishPenguins.contains(penguin)) {
                                threeFishPenguins.add(penguin);
                            }
                            break;
                        case 2:
                            if (!twoFishPenguins.contains(penguin)) {
                                twoFishPenguins.add(penguin);
                            }
                            break;
                        case 1:
                            if (!oneFishPenguins.contains(penguin)) {
                                oneFishPenguins.add(penguin);
                            }
                            break;
                    }
                }
            }
        }

        Penguin bestPenguin;
        if (threeFishPenguins.size() > 0) {
            bestPenguin = threeFishPenguins.get(RandomNumbers.getRandomIndex(threeFishPenguins.size()));
        } else if (twoFishPenguins.size() > 0) {
            bestPenguin = twoFishPenguins.get(RandomNumbers.getRandomIndex(twoFishPenguins.size()));
        } else {
            // TODO maybe repeat until penguin with moves is found
            bestPenguin = oneFishPenguins.get(RandomNumbers.getRandomIndex(oneFishPenguins.size()));
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
