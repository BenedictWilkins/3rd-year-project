package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of some data.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFrame {
  private static final String[] ERRMESSAGES = { "Cannot add row with incorrect number of columns" };
  private static final int PRINTMAXCHARS = 8;
  @SuppressWarnings("unused")
  private String printformat;

  private List<String[]> data;
  @SuppressWarnings("unused")
  private Class<?>[] coltypes;
  private String[] headers;

  /**
   * Constructor.
   * 
   * @param headers
   *          names of each column
   * @param coltypes
   *          types of each column
   */
  public DataFrame(String[] headers, Class<?>[] coltypes) {
    data = new ArrayList<String[]>();
    this.coltypes = coltypes;
    this.headers = headers;
    printformat = createPrintFormat();
  }

  // TODO tdd
  private String createPrintFormat() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < getNumColumns(); i++) {
      builder.append("%" + PRINTMAXCHARS + "s");
    }
    return builder.toString();
  }

  // TODO tdd
  /**
   * Adds a new row to this {@link DataFrame}.
   * 
   * @param row
   *          to add
   * @throws IllegalArgumentException
   *           if the row to add does not fit the format or size
   */
  public void addRow(String[] row) throws IllegalArgumentException {
    checkRowSize(row);
    for (int i = 0; i < row.length; i++) {

    }
    // check for types
    data.add(row);
  }

  // TODO tdd
  public int getNumRows() {
    return data.size();
  }

  // TODO tdd
  public int getNumColumns() {
    return headers.length;
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
    System.out.println(rowToString(headers));
    for (int i = 0; i < limit; i++) {
      System.out.println(rowToString(data.get(i)));
    }
  }

  /**
   * Get all values in a numeric column.
   * 
   * @param index
   *          of the column to retreive from
   * @return the column
   */
  public Double[] getColumn(int index) {
    // do a type check on the column
    Double[] values = new Double[getNumRows()];
    for (int i = 0; i < values.length; i++) {
      values[i] = Double.valueOf(data.get(i)[index]);
    }
    return values;
  }

  private String rowToString(String[] row) {
    StringBuilder builder = new StringBuilder();
    for (String s : row) {
      builder.append(s);
      builder.append(" ");
    }
    return builder.toString();
  }

  // TODO tdd
  private void checkRowSize(String[] row) throws IllegalArgumentException {
    if (row.length != getNumColumns()) {
      throw new IllegalArgumentException(ERRMESSAGES[0]);
    }
  }
}
