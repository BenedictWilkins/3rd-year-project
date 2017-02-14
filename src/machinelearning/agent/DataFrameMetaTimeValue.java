package machinelearning.agent;


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
}
