package graph;

import machinelearning.agent.AbstractDataFrameRow;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameRowDefault;
import utilities.ArgumentUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Use this class to read data from a text file into a {@link DataFrame}.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataReader {

  public static final Map<Class<?>, StringToType<?>> CONVERTMAP;
  public static final String ERRCONVERT = "Cannot convert to type: ";

  private interface StringToType<T> {
    public T convert(String arg);
  }

  static {
    Map<Class<?>, StringToType<?>> map = new HashMap<>();
    // no conversion needed
    map.put(String.class, new StringToType<String>() {

      @Override
      public String convert(String arg) {
        return arg;
      }
    });
    // convert to double
    map.put(Double.class, new StringToType<Double>() {
      @Override
      public Double convert(String arg) {
        return Double.valueOf(arg);
      }
    });
    // convert to integer
    map.put(Integer.class, new StringToType<Integer>() {
      @Override
      public Integer convert(String arg) {
        return Integer.valueOf(arg);
      }
    });
    CONVERTMAP = Collections.unmodifiableMap(map);
  }

  private BufferedReader reader;

  /**
   * Creates a {@link DataFrame} from a text file.
   * 
   * @param filepath
   *          of the file to read
   * @param sep
   *          Delimiter of each line
   * @param header
   *          does the file contain header values
   * @param types
   *          an array containing the types of each column (in order)
   * @return a new {@link DataFrame} containing the contents of the read file
   * @throws IllegalArgumentException
   *           if any argument give is null
   */
  public DataFrame readFile(String filepath, String sep, boolean header,
      Class<?>[] types) throws IllegalArgumentException {
    ArgumentUtilities.checkNullArgs(filepath, sep, types);
    ArgumentUtilities.checkEmptyArray(types);
    for (Class<?> c : types) {
      if (!CONVERTMAP.containsKey(c)) {
        throw new IllegalArgumentException(ERRCONVERT + c);
      }
    }
    DataFrame frame = null;
    try {
      reader = new BufferedReader(new FileReader(filepath));
      String line;
      if (header) {
        String[] headers = reader.readLine().split(sep);
        frame = new DataFrame(headers, types);
      } else {
        String[] firstLine = reader.readLine().split(sep);
        frame = new DataFrame(constructVoidHeaders(firstLine.length), types);
      }
      while ((line = reader.readLine()) != null) {
        frame.addRow(parseLine(line, sep, types));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return frame;
  }

  // may be used at some point (for generic data)
  @SuppressWarnings("unused")
  private Class<?>[] constructStringColumnTypes(int numberOfColumns) {
    Class<?>[] types = new Class<?>[numberOfColumns];
    for (int i = 0; i < numberOfColumns; i++) {
      types[i] = String.class;
    }
    return types;
  }

  private String[] constructVoidHeaders(int numberOfColumns) {
    String[] headers = new String[numberOfColumns];
    for (int i = 0; i < numberOfColumns; i++) {
      headers[i] = "V" + i;
    }
    return headers;
  }

  private AbstractDataFrameRow parseLine(String line, String sep,
      Class<?>[] types) {
    String[] split = line.split(sep);
    Object[] data = new Object[split.length];
    for (int i = 0; i < split.length; i++) {
      data[i] = CONVERTMAP.get(types[i]).convert(split[i]);
    }
    return new DataFrameRowDefault(data);
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
