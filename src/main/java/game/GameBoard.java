package game;

import utility.RandomNumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameBoard {
    private static final int TILE_COUNT = 60;
    private static final int ROW_COUNT = 8;
    private static final int COL_COUNT = 15;

    private final Player[] players;
    private final HashMap<Integer, IceFloeTile> tiles;
    private int currentPlayerIndex;

    public GameBoard() {
        this.players = null;
        this.tiles = createGameBoard();
    }

    public GameBoard(Player[] players) {
        this.players = players;
        this.tiles = createGameBoard();
    }

    // copy constructor
    public GameBoard(GameBoard board) {
        // TODO shallow copy sufficient because same players are needed or implement deep copy?
        this.players = board.getPlayers();
        this.tiles = new HashMap<>();
        for (IceFloeTile tile : board.getTiles()) {
            IceFloeTile tileCopy = new IceFloeTile(tile);
            this.tiles.put(tileCopy.hashCode(), tileCopy);
        }
    }

    public GameBoard(int[] fishCounts) {
        this.players = null;
        this.tiles = new HashMap<>();
        for (int i = 0, k = 0; i < ROW_COUNT; i++) {
            for (int j = (i % 2 == 0) ? 1 : 0; j < COL_COUNT; j += 2) {
                IceFloeTile tile = new IceFloeTile(fishCounts[k++], i, j);
                tiles.put(tile.hashCode(), tile);
            }
        }
    }

    private HashMap<Integer, IceFloeTile> createGameBoard() {
        ArrayList<Integer> fishCounts = new ArrayList<>();
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

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
    }

    public void setNextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
    }

    private int hashPosition(int rowIndex, int colIndex) {
        return Arrays.hashCode(new int[]{rowIndex, colIndex});
    }

    public IceFloeTile getTile(int rowIndex, int colIndex) {
        return this.tiles.get(hashPosition(rowIndex, colIndex));
    }

    public List<IceFloeTile> getTiles() {
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

    private boolean isLegalPlacementTile(IceFloeTile tile) {
        return tile != null && tile.isUnoccupied() && tile.getFishCount() == 1;
    }

    public List<int[]> getLegalPlacementPositions() {
        List<int[]> legalPlacementPositions = new ArrayList<>();
        for (IceFloeTile tile : getTiles()) {
            if (isLegalPlacementTile(tile)) {
                legalPlacementPositions.add(tile.getPosition());
            }
        }
        return legalPlacementPositions;
    }

    private boolean canPlayerPlacePenguin(Player player) {
        return player.getPenguinToPlace() != null;
    }

    private boolean canPenguinsBePlaced() {
        for (Player player : this.players) {
            if (canPlayerPlacePenguin(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean placeCurrentPlayerPenguin(int rowIndex, int colIndex) {
        IceFloeTile selectedTile = getTile(rowIndex, colIndex);
        if (canPlayerPlacePenguin(getCurrentPlayer()) && isLegalPlacementTile(selectedTile)) {
            selectedTile.placePenguin(this.getCurrentPlayer().getPenguinToPlace());
            getCurrentPlayer().getPenguinToPlace().place(rowIndex, colIndex);
            getCurrentPlayer().updatePenguinToPlace();
            setNextPlayer();
            return true;
        }
        return false;
    }

    public boolean moveCurrentPlayerPenguin(Penguin penguin, int rowIndex, int colIndex) {
        // selected penguin must have remaining moves, else return false
        // remove all penguins & corresponding tiles if current player can't move any penguins
        IceFloeTile oldTile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);
        IceFloeTile newTile = getTile(rowIndex, colIndex);

        if (isValidMove(oldTile, newTile)) {
            newTile.placePenguin(penguin);
            penguin.setPosition(rowIndex, colIndex);
            penguin.getPlayer().updateTileCount();
            penguin.getPlayer().updateFishCount(oldTile.getFishCount());
            tiles.remove(hashPosition(oldTile.getPosition()[0], oldTile.getPosition()[1]));

            return true;
        }

        return false;
    }

    public boolean movePenguin(Penguin penguin, int rowIndex, int colIndex) {
        IceFloeTile oldTile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);
        IceFloeTile newTile = getTile(rowIndex, colIndex);

        if (isValidMove(oldTile, newTile)) {
            newTile.placePenguin(penguin);
            penguin.setPosition(rowIndex, colIndex);
            penguin.getPlayer().updateTileCount();
            penguin.getPlayer().updateFishCount(oldTile.getFishCount());
            tiles.remove(hashPosition(oldTile.getPosition()[0], oldTile.getPosition()[1]));

            return true;
        }

        return false;
    }

    private boolean isValidMove(IceFloeTile oldTile, IceFloeTile newTile) {
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

    public void removePenguinAndTile(Penguin penguin) {
        if (penguin.getPosition() == null) {
            return;
        }

        IceFloeTile tile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);
        penguin.getPlayer().updateTileCount();
        penguin.getPlayer().updateFishCount(tile.getFishCount());
        penguin.removeFromGameBoard();
        this.tiles.remove(hashPosition(tile.getPosition()[0], tile.getPosition()[1]));
    }

    public boolean hasPenguinLegalMoves(Penguin penguin) {
        return getAllLegalMovesForPenguin(penguin).size() > 0;
    }

    public List<Move> getAllLegalMovesForPlayer(Player player) {
        List<Move> possibleMoveCoordinates = new ArrayList<>();

        for (Penguin penguin : player.getPenguins()) {
            if (penguin.isOnGameBoard()) {
                for (int[] coordinates : getAllLegalMovesForPenguin(penguin)) {
                    possibleMoveCoordinates.add(new Move(penguin, coordinates[0], coordinates[1]));
                }
            }
        }

        return possibleMoveCoordinates;
    }

    public List<Move> getAllLegalMovesForPlayerByIndex(int playerIndex) {
        return getAllLegalMovesForPlayer(this.players[playerIndex]);
    }

    public List<IceFloeTile> getNeighborTiles(int rowIndex, int colIndex) {
        IceFloeTile srcTile = getTile(rowIndex, colIndex);
        List<IceFloeTile> neighborTiles = new ArrayList<>();
        for (int[] neighborCoordinates : srcTile.getNeighborPositions()) {
            if (neighborCoordinates != null) {
                neighborTiles.add(getTile(neighborCoordinates[0], neighborCoordinates[1]));
            }
        }
        return neighborTiles;
    }

    public List<int[]> getAllLegalMovesForPenguin(Penguin penguin) {
        List<int[]> possibleMoveCoordinates = new ArrayList<>();

        IceFloeTile srcTile = getTile(penguin.getPosition()[0], penguin.getPosition()[1]);

        int[][] neighborCoordinates = srcTile.getNeighborPositions();
        // iterate through all neighbors
        for (int i = 0; i < neighborCoordinates.length; i++) {
            if (neighborCoordinates[i] == null) {
                continue;
            }
            IceFloeTile neighbor = getTile(neighborCoordinates[i][0], neighborCoordinates[i][1]);
            // continue to move in this direction until no more move can be made
            while (neighbor != null && neighbor.isUnoccupied()) {
                possibleMoveCoordinates.add(
                        new int[]{neighbor.getPosition()[0], neighbor.getPosition()[1]});

                if (neighbor.getNeighborPositions()[i] == null) {
                    break;
                }
                neighbor =
                        getTile(
                                neighbor.getNeighborPositions()[i][0], neighbor.getNeighborPositions()[i][1]);
            }
        }

        return possibleMoveCoordinates;
    }


    public int getNextPlayerIndex(int currentPlayerIndex) {
        return ((currentPlayerIndex + 1) % this.players.length);
    }

    public int checkGameStatus() {

    }

    public int checkGameOver() {

    }

    @Override
    public String toString() {
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
                String tileStr = (getTile(i, j) == null) ? "X" : getTile(i, j).toString();
                str.append(spacingLeft).append(tileStr).append(spacingRight);
            }
            str.append("\n");
        }

        return str.toString();
    }
}
