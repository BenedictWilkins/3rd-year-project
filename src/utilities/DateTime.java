package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Represents the date and time in the system.
 * 
 * @author Benedict Wilkins
 *
 */
public class DateTime {

  public static final String DEFAULTPATTERN = "yyyy-MM-dd HH:mm:ss";
  private Date currentDateTime;
  private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");

  /**
   * Constructor. Creates a new instance of {@link DateTime}. The default
   * pattern for the dateTime argument is: {@link #DEFAULTPATTERN}.
   * 
   * @param dateTime
   *          the dateTime to be represented by this {@link DateTime}.
   * @throws ParseException
   *           if the given argument does not fit the {@link #DEFAULTPATTERN}
   */
  public DateTime(String dateTime) {
    try {
      this.currentDateTime = dateTimeFormat.parse(dateTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Constructor. Creates a new instance of {@link DateTime}. The default
   * pattern for the dateTime argument is: {@link #DEFAULTPATTERN}.
   * 
   * @param year
   *          usually 4 digit or 2
   * @param month
   *          2 or 1 digit
   * @param day
   *          2 or 1 digit
   * @param hour
   *          default 24 hour clock
   * @param minute
   *          of an hour
   * @param second
   *          of a minute
   */
  public DateTime(int year, int month, int day, int hour, int minute, int second) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.set(year, month - 1, day, hour, minute, second);
    currentDateTime = calendar.getTime();
  }
  
  public boolean isBefore(DateTime dateTime) {
    return this.currentDateTime.before(dateTime.currentDateTime);
  }

  /**
   * Gets the number of days that have passed this year. 1 is the first day.
   * See: {@link Calendar}{@link #getYearDay()}.
   * 
   * @return the day of the year
   */
  public int getYearDay() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.DAY_OF_YEAR);
  }

  public void setFormat(String format) {
    dateTimeFormat = new SimpleDateFormat(format);
  }

  public int getHour() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  public void setHour(int hour) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    calendar.set(Calendar.HOUR_OF_DAY, hour);
    currentDateTime = calendar.getTime();
  }

  public int getMinute() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.MINUTE);
  }

  public void setMinute(int minute) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    calendar.set(Calendar.MINUTE, minute);
    currentDateTime = calendar.getTime();
  }

  public int getSecond() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.SECOND);
  }

  public void setSecond(int second) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    calendar.set(Calendar.SECOND, second);
    currentDateTime = calendar.getTime();
  }

  public int getYear() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.YEAR);
  }

  public void setYear(int year) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    calendar.set(Calendar.YEAR, year);
    currentDateTime = calendar.getTime();
  }

  public int getMonth() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.MONTH);
  }

  public void setMonth(int month) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    calendar.set(Calendar.MONTH, month - 1);
    currentDateTime = calendar.getTime();
  }

  public int getDay() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  public void setDay(int day) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDateTime);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    currentDateTime = calendar.getTime();
  }

  @Override
  public String toString() {
    return dateTimeFormat.format(currentDateTime);
  }

  @Override
  public DateTime clone() {
    DateTime clone = new DateTime(dateTimeFormat.format(currentDateTime));
    clone.setFormat(this.dateTimeFormat.toPattern());
    return clone;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.currentDateTime);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DateTime)) {
      return false;
    }
    DateTime dt = (DateTime) obj;
    return Objects.equals(this.currentDateTime, dt.currentDateTime);
  }
}
