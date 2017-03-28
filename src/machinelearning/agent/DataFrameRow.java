package machinelearning.agent;

import java.io.Serializable;

/**
 * An interface that should be implemented by a class that is a row in a
 * {@link DataFrame}. <br>
 * Known subclasses: {@link AbstractDataFrameRow}
 * 
 */
public interface DataFrameRow extends Serializable {

  /**
   * Gets all the values in the row as an array of {@link Object}s.
   * 
   * @return an array of {@link Object}s
   */
  public Object[] getValues();

  /**
   * Gets a value from a given column that is of type T.F
   * 
   * @param column
   *          to get from
   * @param type
   *          to get
   * @return the value of type T
   */
  public <T> T getValue(int column, Class<T> type);

  /**
   * Getter the size of the row.
   * 
   * @return the size of the row
   */
  public int getSize();
}
