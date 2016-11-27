package utilities;

import java.util.Objects;

public class DateTime implements DateTimeInterface {
  public static final Integer DAYLENGTH = 48;
  public static final Integer YEARLENGTH = 365;

  private Integer time;
  private Integer date;

  public DateTime(Integer time, Integer date) {
    setTime(time);
    setDate(date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(time, date);
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
    return Objects.equals(time, dt.time) && Objects.equals(date, dt.date);
  }

  @Override
  public String representTime() {
    return null;
  }

  @Override
  public String representDate() {
    return null;
  }

  @Override
  public String representDateTime() {
    return representDate() + representTime();
  }

  public void reset() {
    this.time = 0;
    this.date = 0;
  }

  public void incDate() {
    this.date++;
  }

  public void incTime() {
    this.time++;
  }

  @Override
  public Integer getDate() {
    return date;
  }

  /**
   * Setter for date.
   * 
   * @param date
   *          new date
   */
  public void setDate(Integer date) {
    if (date < 0 || date > YEARLENGTH - 1) {
      throw new IllegalArgumentException();
    }
    this.date = date;
  }

  @Override
  public Integer getTime() {
    return time;
  }

  /**
   * Setter for time.
   * 
   * @param time
   *          new time
   */
  public void setTime(Integer time) {
    if (time < 0 || time > DAYLENGTH - 1) {
      throw new IllegalArgumentException();
    }
    this.time = time;
  }

  @Override
  public String toString() {
    return this.time + "," + this.date;
  }

  @Override
  public DateTime clone() {
    return new DateTime(this.getTime(), this.getDate());
  }
}
