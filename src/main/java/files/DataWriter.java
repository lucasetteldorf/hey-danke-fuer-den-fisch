package files;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
  public static void writeDataLine(
      String filePath,
      String ai1Name,
      int ai1TileCount,
      int ai1FishCount,
      String ai2Name,
      int ai2TileCount,
      int ai2FishCount) {
    try {
      File file = new File(filePath);
      FileWriter fw;
      CSVWriter writer;

      if (file.createNewFile()) {
        fw = new FileWriter(file);
        writer = new CSVWriter(fw);

        String[] header = {
          ai1Name + " tile count",
          ai1Name + " fish count",
          ai2Name + " tile count",
          ai2Name + " fish count"
        };
        writer.writeNext(header);
      } else {
        fw = new FileWriter(file, true);
        writer = new CSVWriter(fw);
      }

      String[] data = {
        String.valueOf(ai1TileCount),
        String.valueOf(ai1FishCount),
        String.valueOf(ai2TileCount),
        String.valueOf(ai2FishCount)
      };

      writer.writeNext(data);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
