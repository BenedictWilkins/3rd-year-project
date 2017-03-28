package utilities.autowarn;

/**
 * A class that provides warnings to the user. <br>
 * Known subclasses: {@link AutoWarnContinue}.
 * 
 * @author Benedict Wilkins
 *
 */
public class AutoWarn {

  private String warning;
  private Exception exception;

  /**
   * Constructor.
   * 
   * @param warning
   *          to give
   * @param exception
   *          that comes with the warning (may be null)
   */
  public AutoWarn(String warning, Exception exception) {
    super();
    this.warning = warning;
    this.exception = exception;
  }

  public AutoWarn(String warning) {
    super();
    this.warning = warning;
  }

  public void logWarning() {
    // TODO
  }

  /**
   * Outputs this warning to std out.
   */
  public void warn() {
    System.out.println(warning);
    if (exception != null) {
      exception.printStackTrace();
    }
  }

  public String getWarning() {
    return warning;
  }

  public void setWarning(String warning) {
    this.warning = warning;
  }

  public Exception getException() {
    return exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }
}
