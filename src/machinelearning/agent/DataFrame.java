package machinelearning.agent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of some data.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFrame implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String ERRROWSIZE = "Cannot add new row with size: ";
  public static final String ERRTYPE = "Incompatible type on row: ";

  private static final int PRINTMAXCHARS = 8;
  @SuppressWarnings("unused")
  private String printformat;

  private DataFrameMetaData meta;
  private List<DataFrameRow> data;

  /**
   * Constructor.
   * 
   * @param headers
   *          names of each column
   * @param coltypes
   *          types of each column
   */
  public DataFrame(String[] headers, Class<?>[] coltypes) {
    this.meta = new DataFrameMetaData(headers, coltypes);
    this.data = new ArrayList<>();
    this.printformat = createPrintFormat();
  }

  /**
   * Constructor.
   * 
   * @param meta
   *          data describing this {@link DataFrame}
   */
  public DataFrame(DataFrameMetaData meta) {
    this.meta = meta;
    this.data = new ArrayList<>();
    this.printformat = createPrintFormat();
  }

  // TODO tdd
  private String createPrintFormat() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < getNumColumns(); i++) {
      builder.append("%" + PRINTMAXCHARS + "s");
    }
    return builder.toString();
  }

  /**
   * Adds a new row to this {@link DataFrame}. A row may only be added if it
   * matches this {@link DataFrame}s structure i.e. the number of columns and
   * the type of each {@link Object} in the row to be added matches the existing
   * structure of this {@link DataFrame}.
   * 
   * @param row
   *          to add
   * @throws IllegalArgumentException
   *           if the row cannot be added
   */
  public void addRow(DataFrameRow row) throws IllegalArgumentException {
    checkRowSize(row);
    checkRowType(row);
    data.add(row);
  }

  public int getNumRows() {
    return data.size();
  }

  public int getNumColumns() {
    return meta.getColumnsSize();
  }

  // TODO tdd
  /**
   * Prints the contents of this {@link DataFrame} to to std.out
   * 
   * @param limit
   *          the amount of rows printed
   */
  public void printData(int limit) {
    limit = (limit > getNumRows()) ? getNumRows() : limit;
    System.out.println(rowToString(meta.getHeaders()));
    for (int i = 0; i < limit; i++) {
      // System.out.println(rowToString(data.get(i)));
    }
  }

  /**
   * Gets an entire column of this {@link DataFrame}.
   * 
   * @param index
   *          of the column to get
   * @param type
   *          of the column to get (this must be the {@link Class} of the
   *          column) typically this may be found by calling the
   *          {@link DataFrame}{@link #getColumnType(int)} method.
   * @return a {@link List} containing all values in the column
   */
  public <T> List<T> getColumn(int index, Class<T> type) {
    // do a type check on the column
    if (!meta.getColumnType(index).equals(type)) {
      throw new IllegalArgumentException(type + " type should be: "
          + meta.getColumnType(index));
    }
    List<T> column = new ArrayList<>();

    for (int i = 0; i < this.getNumRows(); i++) {
      column.add(data.get(i).getValue(index, type));
    }
    return column;
  }

  /**
   * Gets an entire row of this {@link DataFrame} as a {@link AbstractDataFrameRow}.
   * 
   * @param index
   *          of the row
   * @return the row
   */
  public DataFrameRow getRow(int index) {
    return data.get(index);
  }

  /**
   * Gets the type of a column represented by {@link Class}.
   * 
   * @param index
   *          of the column
   * @return the {@link Class} representing the type
   */
  public Class<?> getColumnType(int index) {
    return meta.getColumnType(index);
  }

  // TODO
  protected String rowToString(String[] row) {
    StringBuilder builder = new StringBuilder();
    for (String s : row) {
      builder.append(s);
      builder.append(" ");
    }
    return builder.toString();
  }

  /**
   * Checks the type of each {@link Object} in the given {@link AbstractDataFrameRow}
   * given against the structure of this {@link DataFrame}.
   * 
   * @param row
   *          to check
   * @throws IllegalArgumentException
   *           if types do not match
   */
  protected void checkRowType(DataFrameRow row) throws IllegalArgumentException {
    Object[] data = row.getValues();
    for (int i = 0; i < meta.getColumnsSize(); i++) {
      if (!meta.getColumnType(i).isInstance(data[i])) {
        throw new IllegalArgumentException(ERRTYPE + i + ":"
            + data[i].getClass() + " : " + meta.getColumnType(i));
      }
    }
  }

  /**
   * Checks the size of the {@link AbstractDataFrameRow} given against the structure of
   * this {@link DataFrame}.
   * 
   * @param row
   *          to check
   * @throws IllegalArgumentException
   *           if size does not match
   */
  protected void checkRowSize(DataFrameRow row) throws IllegalArgumentException {
    if (row.getSize() != meta.getColumnsSize()) {
      throw new IllegalArgumentException(ERRROWSIZE + row.getSize());
    }
  }

  public List<? extends DataFrameRow> getData() {
    return data;
  }

  /**
   * Removes all data in this {@link DataFrame}, the structure is preserved.
   */
  public void clear() {
    data.clear();
  }
}
