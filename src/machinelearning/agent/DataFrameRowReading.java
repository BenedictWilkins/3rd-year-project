package machinelearning.agent;

public class DataFrameRowReading extends AbstractDataFrameRow {

  private static final long serialVersionUID = 1L;

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
