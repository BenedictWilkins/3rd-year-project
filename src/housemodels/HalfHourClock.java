package housemodels;

import utilities.Clock;
import utilities.DateTime;

/**
 * Used as a global timing mechanism. Time starts at 00:00:00, and date starts
 * at January 1st (year is irrelevant in context). The step of this clock is 30
 * minutes. <br>
 * Extends {@link Clock}.
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

  private HalfHourClock(DateTime startDateTime) {
    super(startDateTime, TIMEINCREMENT, TIMETYPE, realTimeDelay);
  }

  public void setRealTimeDelay(int delay) {
    super.setRealTimeDelay(delay);
  }

  /**
   * Gets the current half hour interval that the given data time is in. (48
   * half hour intervals in 24 hours).
   * 
   * @param dateTime
   *          to get the half hour interval from
   * @return the half hour interval
   */
  public int getHalfHourInDay(DateTime dateTime) {
    int hour = dateTime.getHour();
    int min = dateTime.getMinute();
    return (min >= 30) ? (hour * 2) + 1 : hour * 2;
  }

  /**
   * Get the next half hour interval of this data time (i.e. step 30 minutes).
   * 
   * @param dateTime
   *          to step
   * @return result of a 30 minute step
   */
  public static DateTime next(DateTime dateTime) {
    DateTime result = new DateTime(dateTime.toString());
    result.setMinute(dateTime.getMinute() + TIMEINCREMENT);
    return result;
  }

  public static void nextInPlace(DateTime dateTime) {
    dateTime.setMinute(dateTime.getMinute() + TIMEINCREMENT);
  }

  /**
   * Get the current {@link DateTime} represented by this {@link HalfHourClock}
   * 
   * @return current {@link DateTime}.
   */
  public DateTime getDateTime() {
    return this.getCurrentDateTime();
  }

  /**
   * Getter for the single instance of {@link HalfHourClock}.
   * 
   * @return the single instance
   */
  public static HalfHourClock getInstance() {
    return instance;
  }

  public String toString() {
    return this.getDateTime().toString();
  }
}
