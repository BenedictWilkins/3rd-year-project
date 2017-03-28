package utilities.datawriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract implementation of {@link FileFormat}. This class handles valid
 * file extensions using regex.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class AbstractFileFormat implements FileFormat {

  private static final String FILEEXTENSIONFORMAT = "\\*\\.[a-zA-Z]+";

  protected String fileExtension;

  /**
   * Constructor.
   * 
   * @param fileExtension
   *          should be provided in the form "*.[...]" for example "*.txt".
   */
  public AbstractFileFormat(String fileExtension) {
    super();
    validateFileExtension(fileExtension);
    this.fileExtension = fileExtension;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  private void validateFileExtension(String fileExtension)
      throws IllegalArgumentException {
    Pattern pattern = Pattern.compile(FILEEXTENSIONFORMAT);
    Matcher matcher = pattern.matcher(fileExtension);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("INVALID FILE EXTENSION");
    }
  }
}
