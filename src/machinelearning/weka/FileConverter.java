package machinelearning.weka;

/**
 * Any class whos job is to convert between file types should implement this
 * interface.
 * 
 * @author Benedict Wilkins
 *
 */
public interface FileConverter {

  /**
   * Converts a file from the inputFile type to the outputFile type.
   * 
   * @param inputFile
   *          to convert from
   * @param outputFile
   *          to convert to
   */
  public void convert(String inputFile, String outputFile);
}
