package utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A utility class that contains general useful functions.
 * 
 * @author Benedict Wilkins
 *
 */
public class GeneralUtilities {

  /**
   * Creates/gets a logger that will log to the given file path. Calling info on
   * the logger will simply print a line, it will avoid time and date stamps
   * etc.
   * 
   * @param filepath
   *          of the file to log to (the file will be created)
   * @param loggerkey
   *          of the logger to get, a new one will be created if one is not
   *          found see {@link Logger#getLogger(String)}
   * @return
   */
  public static Logger getLogger(String filepath, String loggerkey) {
    try {
      Logger logger = Logger.getLogger(loggerkey);
      FileHandler fileHandler = new FileHandler(filepath
          + System.currentTimeMillis());
      SimpleFormatter formatter = new SimpleFormatter() {
        @Override
        public synchronized String format(LogRecord record) {
          if (record.getLevel() == Level.INFO) {
            return record.getMessage() + System.lineSeparator();
          } else {
            return super.format(record);
          }
        }
      };
      fileHandler.setFormatter(formatter);
      logger.addHandler(fileHandler);
      logger.setUseParentHandlers(false);
      return logger;
    } catch (SecurityException | IOException e) {
      e.printStackTrace();
      return null;
    }
  }

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
