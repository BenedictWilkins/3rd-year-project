package graph;

import utilities.ArgumentUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

  private BufferedReader reader;

  public DataReader() {

  }

  /**
   * Creates a {@link DataFrame} from a text file.
   * 
   * @param filepath
   *          of the file to read
   * @param sep
   *          Delimiter of each line
   * @param header
   *          does the file contain header values
   * @return a new {@link DataFrame} containing the contents of the read file
   * @throws IllegalArgumentException
   *           if any argument give is null
   */
  public DataFrame readFile(String filepath, String sep, boolean header)
      throws IllegalArgumentException {
    ArgumentUtilities.checkNullArgs(new Object[] { (Object) filepath,
        (Object) sep });
    DataFrame frame = null;
    try {
      reader = new BufferedReader(new FileReader(filepath));
      String line;
      if (header) {
        frame = new DataFrame(reader.readLine().split(sep), null);
      } else {
        String[] firstLine = reader.readLine().split(sep);
        frame = new DataFrame(constructVoidHeaders(firstLine.length), null);
      }
      while ((line = reader.readLine()) != null) {
        frame.addRow(parseLine(line, sep));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return frame;
  }

  private String[] constructVoidHeaders(int numberOfHeaders) {
    String[] headers = new String[numberOfHeaders];
    for (int i = 0; i < numberOfHeaders; i++) {
      headers[i] = "V" + i;
    }
    return headers;
  }

  private String[] parseLine(String line, String sep) {
    return line.split(sep);
    // parse the value
  }

  // type determination will be done here
  @SuppressWarnings("unused")
  private void parseValue(String value) {
    // TODO
  }

  /**
   * Represents a Time/Date value.
   * 
   * @author Benedict Wilkins
   *
   */
  public class TimeStruct {
    private final String defaultSeperator = ":";
    @SuppressWarnings("unused")
    private final String validSeperator = ":;./| ";
    private String seperator = defaultSeperator;

    private String[] time = new String[3];

    public TimeStruct(String time) {
      this.time = time.split(seperator);
    }

    public TimeStruct(String time, String seperator) {
      this.seperator = seperator;
    }

    public String getTime() {
      return time[0] + seperator + time[1] + seperator + time[2];
    }

    public String getHours() {
      return time[0];
    }

    public String getMinuites() {
      return time[1];
    }

    public String getSeconds() {
      return time[2];
    }
  }
}
