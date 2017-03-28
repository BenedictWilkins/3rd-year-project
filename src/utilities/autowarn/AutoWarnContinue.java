package utilities.autowarn;

import utilities.datawriter.DataWriter;

import java.io.IOException;

/**
 * An extension of {@link AutoWarn} specifically for asking the user whether
 * they want to continue after the warning has been given. See
 * {@link DataWriter}. Also see {@link Questioner}.
 * 
 * @author Benedict Wilkins
 *
 */
public class AutoWarnContinue extends AutoWarn {

  private static final String QUESTION = "Continue?";

  private YesNoQuestioner questioner;

  /**
   * Constructor. Initialises a new {@link AutoWarnContinue}. User answer: yes -
   * normal execution will continue. User answer: no - program exit using 0 exit
   * code.
   * 
   * @param warning
   *          to display
   * @param exception
   *          to display (may be null)
   */
  public AutoWarnContinue(String warning, Exception exception) {
    super(warning, exception);
    questioner = new YesNoQuestioner(QUESTION, null, new Runnable() {
      @Override
      public void run() {
        System.out.println("EXITING...");
        System.exit(0);
      }
    });
  }

  /**
   * Constructor. Initialises a new {@link AutoWarnContinue}. User answer: yes -
   * runs the given yes argument. User answer: no - runs the given no argument.
   * 
   * @param warning
   *          to display
   * @param exception
   *          to display (may be null)
   * @param yes
   *          runnable to run on yes
   * @param no
   *          runnalbe to run on no
   */
  public AutoWarnContinue(String warning, Exception exception, Runnable yes,
      Runnable no) {
    super(warning, exception);
    questioner = new YesNoQuestioner(QUESTION, yes, no);
  }

  @Override
  public void warn() {
    try {
      super.warn();
      questioner.ask();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
