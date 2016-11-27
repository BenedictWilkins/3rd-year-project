package housemodels;

import utilities.DateTime;

/**
 * Used as a global timing mechanism. All houses should access the TIME and DATE
 * fields to get the current time and date. Time starts at 00:00:00, and date
 * starts at January 1st (year is irrelevant in context).
 * 
 * @author Benedict Wilkins
 *
 */
public class TimeDateTracker implements Runnable {

  private static TimeDateTracker instance = new TimeDateTracker();

  private static DateTime dateTime = new DateTime(0, 0);

  private static Integer interval = 100;
  private static Boolean run = false;

  /**
   * Constructor.
   */
  private TimeDateTracker() {

  }

  public static void setInterval(Integer interval) {
    TimeDateTracker.interval = interval;
  }

  public static DateTime getDateTime() {
    return dateTime;
  }

  public static int getTime() {
    return dateTime.getTime();
  }

  public static int getDate() {
    return dateTime.getDate();
  }

  @Override
  public void run() {
    run = true;
    while (run) {
      try {
        Thread.sleep(interval);
        dateTime.incTime();
        if (dateTime.getTime() > DateTime.DAYLENGTH - 1) {
          dateTime.setTime(0);
          dateTime.incDate();
          if (dateTime.getDate() > DateTime.YEARLENGTH - 1) {
            dateTime.setDate(0);
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void stop() {
    run = false;
  }

  public static void reset() {
    dateTime.reset();
  }

  public static TimeDateTracker getInstance() {
    return instance;
  }

  public String toString() {
    return dateTime.getDate() + "," + dateTime.getTime();
  }
}
