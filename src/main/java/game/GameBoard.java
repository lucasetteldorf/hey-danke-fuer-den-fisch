package game;

import game.players.Player;
import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameBoard {
    private static final int TILE_COUNT = 60;
    private static final int ROW_COUNT = 8;
    private static final int COL_COUNT = 15;

    private final HashMap<Integer, IceFloeTile> tiles;
    private final HashMap<Integer, Penguin> penguins;

    public GameBoard() {
        this.tiles = createGameBoard();
        this.penguins = new HashMap<>();
    }

    public GameBoard(int[] fishCounts) {
        this.tiles = new HashMap<>();
        for (int i = 0, k = 0; i < ROW_COUNT; i++) {
            for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
                IceFloeTile tile = new IceFloeTile(fishCounts[k++], i, j);
                tiles.put(tile.hashCode(), tile);
            }
        }
        this.penguins = new HashMap<>();
    }

    // copy constructor
    public GameBoard(GameBoard board) {
        this.tiles = new HashMap<>();
        for (IceFloeTile tile : board.getAllTiles()) {
            IceFloeTile tileCopy = new IceFloeTile(tile);
            this.tiles.put(tileCopy.hashCode(), tileCopy);
        }
        this.penguins = new HashMap<>();
        for (Penguin penguin : board.getAllPenguins()) {
            Penguin penguinCopy = new Penguin(penguin);
            this.penguins.put(penguin.hashCode(), penguinCopy);
        }
    }

    private HashMap<Integer, IceFloeTile> createGameBoard() {
        List<Integer> fishCounts = new ArrayList<>();
        int fishCount = 1;
        for (int i = 0; i < TILE_COUNT; i++) {
            fishCounts.add(fishCount);

            if (i == 29 || i == 49) {
                fishCount++;
            }
        }

        HashMap<Integer, IceFloeTile> tiles = new HashMap();
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
                int randomIndex = RandomNumbers.getRandomIndex(fishCounts.size());
                IceFloeTile randomTile = new IceFloeTile(fishCounts.get(randomIndex), i, j);
                tiles.put(randomTile.hashCode(), randomTile);
                fishCounts.remove(randomIndex);
            }
        }
        return tiles;
    }

    private int hashPosition(int row, int col) {
        return Arrays.hashCode(new int[]{row, col});
    }

    public IceFloeTile getTile(int row, int col) {
        return tiles.get(hashPosition(row, col));
    }

    public List<IceFloeTile> getAllTiles() {
        List<IceFloeTile> allTiles = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
                IceFloeTile tile = getTile(i, j);
                if (tile != null) {
                    allTiles.add(tile);
                }
            }
        }
        return allTiles;
    }

    public Penguin getPenguin(int row, int col) {
        return penguins.get(hashPosition(row, col));
    }

    public List<Penguin> getAllPenguins() {
        List<Penguin> allPenguins = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
                Penguin penguin = getPenguin(i, j);
                if (penguin != null) {
                    allPenguins.add(penguin);
                }
            }
        }
        return allPenguins;
    }

    public boolean isPlacementPhaseOver(Player[] players) {
        for (Player player : players) {
            if (player.canPlacePenguin()) {
                return true;
            }
        }
        return false;
    }

    private boolean isLegalPlacementTile(IceFloeTile tile) {
        return tile != null && tile.isUnoccupied() && tile.getFishCount() == 1;
    }

    public List<int[]> getAllLegalPlacementPositions() {
        List<int[]> legalPlacementPositions = new ArrayList<>();
        for (IceFloeTile tile : getAllTiles()) {
            if (isLegalPlacementTile(tile)) {
                legalPlacementPositions.add(tile.getPosition());
            }
        }
        return legalPlacementPositions;
    }

    public void placePenguin(Player player, int row, int col) {
        IceFloeTile selectedTile = getTile(row, col);
        if (player.canPlacePenguin() && isLegalPlacementTile(selectedTile)) {
            selectedTile.setUnoccupied(false);
            Penguin penguinToPlace = player.getPenguinToPlace();
            penguinToPlace.place(row, col);
            penguins.put(penguinToPlace.hashCode(), penguinToPlace);
            player.updatePenguinToPlace();
        }
    }

    public boolean isMovementPhaseOver(Player[] players) {
        for (Player player : players) {
            if (hasPlayerLegalMoves(player)) {
                return true;
            }
        }
        return false;
    }

    public void movePenguin(Player player, Move move) {
        Penguin penguin = getPenguin(move.getOldRow(), move.getOldCol());
        IceFloeTile oldTile = getTile(move.getOldRow(), move.getOldCol());
        IceFloeTile newTile = getTile(move.getNewRow(), move.getNewCol());

        if (canPenguinMove(penguin) && isLegalMove(oldTile, newTile)) {
            newTile.setUnoccupied(false);
            penguin.setPosition(move.getNewRow(), move.getNewCol());
            player.updateTileCount();
            player.updateFishCount(oldTile.getFishCount());
            removeTile(oldTile);
        }
    }

    public boolean canPenguinMove(int row, int col) {
        Penguin penguin = getPenguin(row, col);
        return penguin != null && hasPenguinLegalMoves(penguin);
    }

    public boolean canPenguinMove(Penguin penguin) {
        return penguin != null && hasPenguinLegalMoves(penguin);
    }

    public boolean isLegalMove(IceFloeTile oldTile, IceFloeTile newTile) {
        if (newTile != null && !oldTile.equals(newTile) && newTile.isUnoccupied()) {
            int rowDiff = newTile.getPosition()[0] - oldTile.getPosition()[0];
            int colDiff = newTile.getPosition()[1] - oldTile.getPosition()[1];
            int totalDiff = Math.abs(rowDiff) - Math.abs(colDiff);

            // top right (direction 0)
            if (rowDiff <= -1 && colDiff >= 1) {
                if (totalDiff != 0) {
                    return false;
                }
                return areAllTilesValid(oldTile, newTile, 0);
            }

            // right (direction 1)
            if (rowDiff == 0 && colDiff >= 2) {
                return areAllTilesValid(oldTile, newTile, 1);
            }

            // bottom right (direction 2)
            if (rowDiff >= 1 && colDiff >= 1) {
                if (totalDiff != 0) {
                    return false;
                }
                return areAllTilesValid(oldTile, newTile, 2);
            }

            // bottom left (direction 3)
            if (rowDiff >= 1 && colDiff <= -1) {
                if (totalDiff != 0) {
                    return false;
                }
                return areAllTilesValid(oldTile, newTile, 3);
            }

            // left (direction 4)
            if (rowDiff == 0 && colDiff <= -2) {
                return areAllTilesValid(oldTile, newTile, 4);
            }

            // top left (direction 5)
            if (rowDiff <= -1 && colDiff <= -1) {
                if (totalDiff != 0) {
                    return false;
                }
                return areAllTilesValid(oldTile, newTile, 5);
            }
        }

        return false;
    }

    private boolean areAllTilesValid(IceFloeTile oldTile, IceFloeTile newTile, int direction) {
        IceFloeTile neighbor = oldTile;

        int colDiff = Math.abs(newTile.getPosition()[1] - oldTile.getPosition()[1]);
        if (direction == 1 || direction == 4) {
            colDiff /= 2;
        }

        for (int i = 0; i < colDiff; i++) {
            neighbor =
                    getTile(
                            neighbor.getNeighborPositions()[direction][0],
                            neighbor.getNeighborPositions()[direction][1]);

            if (neighbor == null || !neighbor.isUnoccupied()) {
                return false;
            }

            if (i == colDiff - 1 && !neighbor.equals(newTile)) {
                return false;
            }
        }

        return true;
    }

    private void removeTile(IceFloeTile tile) {
        tiles.remove(hashPosition(tile.getRow(), tile.getCol()));
    }

    private void removePenguin(Penguin penguin) {
        penguins.remove(hashPosition(penguin.getRow(), penguin.getCol()));
    }

    public void removeAllPenguinsAndTiles(Player player) {
        for (Penguin penguin : player.getPenguins()) {
            removePenguin(penguin);
            removeTile(getTile(penguin.getRow(), penguin.getCol()));
        }
    }

    public List<Move> getAllLegalMovesForPenguin(Penguin penguin) {
        List<Move> possibleMoves = new ArrayList<>();
        IceFloeTile oldTile = getTile(penguin.getRow(), penguin.getCol());
        int[][] neighborPositions = oldTile.getNeighborPositions();
        // iterate through all neighbors
        for (int i = 0; i < neighborPositions.length; i++) {
            // no neighbor at this position
            if (neighborPositions[i] == null) {
                continue;
            }

            IceFloeTile neighbor = getTile(neighborPositions[i][0], neighborPositions[i][1]);
            // continue to move in this direction until no more move can be made
            while (neighbor != null && neighbor.isUnoccupied()) {
                possibleMoves.add(new Move(penguin.getPosition(), neighbor.getPosition()));
                if (neighbor.getNeighborPositions()[i] == null) {
                    break;
                }
                neighbor =
                        getTile(
                                neighbor.getNeighborPositions()[i][0], neighbor.getNeighborPositions()[i][1]);
            }
        }
        return possibleMoves;
    }

    public boolean hasPenguinLegalMoves(Penguin penguin) {
        return !getAllLegalMovesForPenguin(penguin).isEmpty();
    }

    public List<Move> getAllLegalMovesForPlayer(Player player) {
        List<Move> possibleMoves = new ArrayList<>();
        for (Penguin penguin : player.getPenguins()) {
            if (penguin.isOnGameBoard()) {
                for (Move move : getAllLegalMovesForPenguin(penguin)) {
                    possibleMoves.add(move);
                }
            }
        }
        return possibleMoves;
    }

    public boolean hasPlayerLegalMoves(Player player) {
        return !getAllLegalMovesForPlayer(player).isEmpty();
    }

    // return index of the winning player (-1 if tied)
    // TODO adjust winning/tie rules
    public int getWinnerIndex(Player[] players) {
        int maxFishCount = players[0].getCollectedFishCount();
        int winnerIndex = 0;
        for (int i = 1; i < players.length; i++) {
            if (players[i].getCollectedFishCount() > maxFishCount) {
                maxFishCount = players[i].getCollectedFishCount();
                winnerIndex = i;
            }
        }
        return winnerIndex;
    }

    // return null if there is no clear winner/a tie
    // TODO maybe adjust to return array of tied players
    public Player getWinner(Player[] players) {
        return (getWinnerIndex(players) != -1) ? players[getWinnerIndex(players)] : null;
    }

    public void printBoard() {
        StringBuilder str =
                new StringBuilder(
                        "\n  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14\n--------------------------------------\n");

        for (int i = 0; i < ROW_COUNT; i++) {
            str.append(i).append(" | ");
            for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
                String spacingLeft;
                String spacingRight;
                if (i % 2 == 0) {
                    spacingLeft = (j < 10) ? "  " : "   ";
                    spacingRight = (j < 9) ? " " : "  ";
                } else {
                    spacingLeft = (j < 10) ? "" : " ";
                    spacingRight = (j < 9) ? "   " : "    ";
                }
                String tileStr = (getTile(i, j) == null) ? "X" : (getPenguin(i, j) == null) ? getTile(i, j).toString() : getPenguin(i, j).toString();
                str.append(spacingLeft).append(tileStr).append(spacingRight);
            }
            str.append("\n");
        }

        System.out.println(str);
    }
}
