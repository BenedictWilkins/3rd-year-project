package housemodels;

import utilities.Clock;
import utilities.DateTime;

/**
 * Used as a global timing mechanism. All houses should access the TIME and DATE
 * fields to get the current time and date. Time starts at 00:00:00, and date
 * starts at January 1st (year is irrelevant in context).
 * 
 * @author Benedict Wilkins
 *
 */
public class HalfHourClock extends Clock {

  public static final int TIMEINCREMENT = 30;
  public static final int TIMETYPE = Clock.MINUTE;

  public static final int DAYLENGTH = (24 * 60) / TIMEINCREMENT;
  // TODO handle leap years - this may become an issue
  public static final int YEARLENGTH = 365;

  private static Integer realTimeDelay = 100;

  private static HalfHourClock instance = new HalfHourClock(new DateTime(
      "2013-01-01 00:00:00"));

  /**
   * Constructor.
   */
  private HalfHourClock(DateTime startDateTime) {
    super(startDateTime, TIMEINCREMENT, TIMETYPE, realTimeDelay);
  }

  public void setRealTimeDelay(int delay) {
    super.setRealTimeDelay(delay);
  }

  public int getHalfHourInDay(DateTime dateTime) {
    int hour = dateTime.getHour();
    int min = dateTime.getMinute();
    return (min >= 30) ? (hour * 2) + 1 : hour * 2;
  }

  public static DateTime next(DateTime dateTime) {
    DateTime result = new DateTime(dateTime.toString());
    result.setMinute(dateTime.getMinute() + TIMEINCREMENT);
    return result;
  }

  public static void nextInPlace(DateTime dateTime) {
    dateTime.setMinute(dateTime.getMinute() + TIMEINCREMENT);
  }

  public DateTime getDateTime() {
    return this.getCurrentDateTime();
  }

  public static HalfHourClock getInstance() {
    return instance;
  }

  public String toString() {
    return this.getDateTime().toString();
  }
}
