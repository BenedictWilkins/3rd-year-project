package machinelearning.agent;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A description of an instance of {@link DataFrame}. This class contains
 * information about the columns (meta data) including their headers and types.
 * By implication the size of the type and header arguments should be precisely
 * equal.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFrameMetaData implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final String ERRCOLUMNTYPES = "Illegal: "
      + DataFrameMetaData.class.getSimpleName() + " Columns must be typed.";

  private String[] headers;
  private Class<?>[] columnTypes;

  /**
   * Constructor.
   * 
   * @param headers
   *          of each column in a {@link DataFrame}
   * @param columnTypes
   *          of each column in a {@link DataFrame}
   * @throws IllegalArgumentException
   *           if the columnTypes are invalid (null or empty) or the length of
   *           the two arguments are not equal
   */
  public DataFrameMetaData(String[] headers, Class<?>[] columnTypes) {
    System.out.println(Arrays.toString(columnTypes));
    if (columnTypes == null) {
      throw new IllegalArgumentException(ERRCOLUMNTYPES);
    }
    if (columnTypes.length == 0) {
      throw new IllegalArgumentException(ERRCOLUMNTYPES);
    }
    if (headers == null) {
      fillEmptyHeaders(new String[columnTypes.length]);
    }
    // TODO check length of types and headers;
    this.headers = headers;
    this.columnTypes = columnTypes;
  }

  // fills headers with empty Strings
  private void fillEmptyHeaders(String[] headers) {
    for (int i = 0; i < headers.length; i++) {
      headers[i] = "";
    }
  }

  /**
   * Gets the type of the column specified by its index.
   * 
   * @param column
   *          index of the column
   * @return the type, null if the index is out of range
   */
  public Class<?> getColumnType(int column) {
    if (column >= 0 && column < columnTypes.length) {
      return columnTypes[column];
    } else {
      return null;
    }
  }

  public int getColumnsSize() {
    return headers.length;
  }

  public String[] getHeaders() {
    return headers;
  }

  public void setHeaders(String[] headers) {
    this.headers = headers;
  }

  public Class<?>[] getColumnTypes() {
    return columnTypes;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!DataFrameMetaData.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final DataFrameMetaData other = (DataFrameMetaData) obj;
    return Arrays.deepEquals(this.headers, other.headers)
        && Arrays.deepEquals(this.columnTypes, other.columnTypes);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(headers) + Arrays.hashCode(columnTypes);
  }
}
