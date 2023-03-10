package game.players;

import game.Penguin;

public interface Player {
    PlayerType getType();

    String getName();

    String getPenguinColor();

    Penguin[] getPenguins();

    Penguin getPenguin(int index);

    Penguin getPenguinToPlace();

    void updatePenguinToPlace();

    boolean canPlacePenguin();

    int getCollectedTileCount();

    void updateCollectedTileCount();

    int getCollectedFishCount();

    void updateCollectedFishCount(int fishCount);

    boolean arePenguinsRemovedFromBoard();

    void setPenguinsRemovedFromBoard(boolean penguinsRemovedFromBoard);

    String getScore();

    @Override
    boolean equals(Object obj);

    @Override
    String toString();
}
