package machinelearning.agent;

/**
 * An extension of {@link DataFrameMetaData} specifically for a
 * {@link DataFrame} that contains only energy reading data. A {@link DataFrame}
 * of this kind contains only DateTime (as {@link String}), and Value (as
 * {@link Double}). Each row should be represented by
 * {@link DataFrameRowReading}.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFrameMetaTimeValue extends DataFrameMetaData {

  private static final long serialVersionUID = 1L;

  private static final String[] HEADERS = new String[] { "Time", "Consumption" };
  private static final Class<?>[] COLUMNTYPES = new Class<?>[] { String.class,
      Double.class };
  private static DataFrameMetaTimeValue instance = new DataFrameMetaTimeValue();

  private DataFrameMetaTimeValue() {
    super(HEADERS, COLUMNTYPES);
  }

  public static DataFrameMetaTimeValue getInstance() {
    return instance;
  }

  public static Class<?> getTimeColumnType() {
    return COLUMNTYPES[getTimeColumnIndex()];
  }

  public static Class<?> getValueColumnType() {
    return COLUMNTYPES[getValueColumnIndex()];
  }

  public static Integer getTimeColumnIndex() {
    return 0;
  }

  public static Integer getValueColumnIndex() {
    return 1;
  }
}
