package machinelearning.agent;

/**
 * The default {@link DataFrameRow}. Contains some arbitrary {@link Object}s.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFrameRowDefault extends AbstractDataFrameRow {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param objs
   *          {@link Object}s in the row
   */
  public DataFrameRowDefault(Object... objs) {
    super(objs);
  }
}
