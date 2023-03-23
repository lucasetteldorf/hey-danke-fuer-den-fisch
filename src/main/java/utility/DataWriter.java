package utility;

import com.opencsv.CSVWriter;
import game.GameBoard;import game.players.Player;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
  public static void writeDataLine(String filePath, GameBoard board) {
    Player[] players = board.getPlayers();

    try {
      File file = new File(filePath);
      FileWriter fw;
      CSVWriter writer;

      if (file.createNewFile()) {
        fw = new FileWriter(file);
        writer = new CSVWriter(fw);

        String[] header = new String[2 * players.length];
        int index = 0;
        for (Player player : players) {
          header[index++] = player.getName() + " collected tiles";
          header[index++] = player.getName() + " collected fish";
        }
        header[index] = "Winner";
        writer.writeNext(header);
      } else {
        fw = new FileWriter(file, true);
        writer = new CSVWriter(fw);
      }

      String[] data = new String[2 * players.length + 1];
      int index = 0;
      for (Player player : players) {
        data[index++] = String.valueOf(player.getCollectedTileCount());
        data[index++] = String.valueOf(player.getCollectedFishCount());
      }
      data[index] = (board.getWinner() == null) ? "Tie" : board.getWinner().getName();

      writer.writeNext(data);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
