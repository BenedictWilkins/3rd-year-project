package machinelearning.agent;

/**
 * A {@link DataFrameRow} that contains only a {@link String} DateTime and a
 * {@link Double} reading. To be used with {@link DataFrameMetaTimeValue}. A
 * single row represents a single energy reading.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFrameRowReading extends AbstractDataFrameRow {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param datetime
   *          the reading was taken
   * @param reading
   *          the value of the reading
   */
  public DataFrameRowReading(String datetime, Double reading) {
    super(datetime, reading);
  }

  public Double getReading() {
    return super.getValue(1, Double.class);
  }

  public String getDateTime() {
    return super.getValue(0, String.class);
  }
}
