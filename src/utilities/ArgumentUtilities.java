package utilities;


/**
 * A utility class for checking function arguments.
 * 
 * @author Benedict Wilkins
 *
 */
public class ArgumentUtilities {

  private static String[] ERRMESSAGES = { "Argument cannot be null: ",
      "Array cannot be empty: " };

  /**
   * Checks if arguments are null.
   * 
   * @param args
   *          to check
   * @throws IllegalArgumentException
   *           if any arguments are null
   */
  public static void checkNullArgs(Object... args)
      throws IllegalArgumentException {
    if (args == null) {
      return;
    }
    for (Object o : args) {
      if (o == null) {
        throw new IllegalArgumentException(ERRMESSAGES[0] + o);
      }
    }
  }

  /**
   * Checks if the given array is empty.
   * 
   * @param arg
   *          to check
   * @throws IllegalArgumentException
   *           if the array is empty
   */
  public static void checkEmptyArray(Object[] arg)
      throws IllegalArgumentException {
    if (arg == null) {
      return;
    }
    if (arg.length <= 0) {
      throw new IllegalArgumentException(ERRMESSAGES[1] + arg);
    }
  }
}
