package machinelearning.agent;

import java.util.Arrays;

/**
 * Abstract class for a single row in a {@link DataFrame}. <br>
 * Known subclasses: {@link DataFrameRowReading}, {@link DataFrameRowDefault}. <br>
 * Implements: {@link DataFrameRow}.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class AbstractDataFrameRow implements DataFrameRow {

  private static final long serialVersionUID = 1L;

  private Object[] data;

  /**
   * Constructor. Note that when adding a {@link AbstractDataFrameRow} to a
   * {@link DataFrame} the order of the arguments matters. Type checking will be
   * done in order. If a {@link AbstractDataFrameRow} is to be added to a
   * {@link DataFrame} the types must be compatible with the types defined in
   * the {@link DataFrame}s {@link DataFrameMetaData}.
   * 
   * @param data
   *          some {@link Object}s that are to be held by this
   *          {@link AbstractDataFrameRow}.
   */
  public AbstractDataFrameRow(Object... data) {
    this.data = data;
  }

  /**
   * Getter for the {@link Object}s held in this {@link AbstractDataFrameRow}.
   * 
   * @return the {@link Object}s in this row
   */
  public Object[] getValues() {
    return data;
  }

  /**
   * Gets the {@link Object} given its {@link Class} of a given column in this
   * {@link AbstractDataFrameRow}. WARNING: this method does unchecked casts,
   * only {@link DataFrame} should use this method.
   * 
   * @param column
   *          to get
   * @param type
   *          of the {@link Object} to get
   * @return the instance
   */
  @SuppressWarnings("unchecked")
  public <T> T getValue(int column, Class<T> type) {
    return (T) data[column];
  }

  public int getSize() {
    return data.length;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!AbstractDataFrameRow.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final AbstractDataFrameRow other = (AbstractDataFrameRow) obj;
    return Arrays.deepEquals(this.data, other.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.data);
  }

  @Override
  public String toString() {
    return Arrays.toString(this.data);
  }
}
