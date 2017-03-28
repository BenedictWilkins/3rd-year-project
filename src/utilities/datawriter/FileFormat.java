package utilities.datawriter;

/**
 * A concrete implementation of this Interface should be able to constuct a
 * formatted file from a {@link String} array.
 * 
 * @author Benedict Wilkins
 *
 */
public interface FileFormat {

  /**
   * This method should convert an array of {@link String}s and appropriately
   * format them to be written to a file as a single {@link String}.
   * 
   * @param toWrite
   * 
   * @return the formated {@link String} to write to a file
   */
  public String construct(String[] toWrite);

}
