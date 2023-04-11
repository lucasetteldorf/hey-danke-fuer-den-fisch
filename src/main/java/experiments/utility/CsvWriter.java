package experiments.utility;

import com.opencsv.CSVWriter;
import game.Game;
import game.players.Player;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
  public static void createCsv(String path) {
    try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
      String[] header =
          new String[] {"AI 1 Name", "AI 1 Fish Count", "AI 2 Name", "AI 2 Fish Count", "Winner"};
      writer.writeNext(header);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void appendLineToCsv(String path, Game game) {
    try (CSVWriter writer = new CSVWriter(new FileWriter(path, true))) {
      String[] data = new String[game.getBoard().getPlayers().length * 2 + 1];
      int index = 0;
      for (Player player : game.getBoard().getPlayers()) {
        data[index++] = player.getName();
        data[index++] = String.valueOf(player.getCollectedFishCount());
      }
      data[index] =
          (game.getBoard().getWinnerIndex() == -1)
              ? "Tie"
              : game.getBoard().getPlayers()[game.getBoard().getWinnerIndex()].getName();
      writer.writeNext(data);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
