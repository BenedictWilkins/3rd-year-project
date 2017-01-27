package utilities.datawriter;

/**
 * 
 * @author Benedict Wilkins
 *
 */
public class FileFormatCSV extends AbstractFileFormat {

  private int numColumns = 0;

  public FileFormatCSV(int numColumns) {
    super("*.csv");
    this.numColumns = numColumns;
  }

  @Override
  public String construct(String[] toWrite) {
    StringBuilder builder = new StringBuilder();
    if (toWrite.length % numColumns != 0) {
      throw new IllegalArgumentException("The given argument of length ( "
          + toWrite.length + " ) is not a factor of the number of columns ( "
          + numColumns + " ) provided.");
    }
    for (int i = 0; i < toWrite.length; i += numColumns) {
      for (int j = 0; j < numColumns - 1; j++) {
        builder.append(toWrite[i + j] + ", ");
      }
      builder.append(toWrite[i + numColumns - 1]);
      builder.append(System.lineSeparator());
    }
    return builder.toString();
  }
}
