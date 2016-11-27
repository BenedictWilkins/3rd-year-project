package utilities;

import java.util.Arrays;
import java.util.Collection;

/**
 * A utility class that contains general useful functions.
 * 
 * @author Benedict Wilkins
 *
 */
public class GeneralUtilities {

  /**
   * Creates a {@link String} representing the array.
   * 
   * @param arg
   *          to create a representation from
   * @return the string representation
   */
  public static <T> String arrayToString(T[] arg) {
    if (arg == null) {
      return null;
    }
    return arg.getClass().getSimpleName() + Arrays.toString(arg);
  }

  /**
   * Creates a {@link String} representing the collection.
   * 
   * @param arg
   *          to create a representation from
   * @return the string representation
   */
  public static <T> String collectionToString(Collection<T> arg) {
    if (arg == null) {
      return null;
    }
    return arg.getClass().getSimpleName() + Arrays.toString(arg.toArray());
  }

}
