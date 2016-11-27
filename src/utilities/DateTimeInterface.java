package utilities;

/**
 * An interface that should be implements for a date time representation in the
 * system.
 * 
 * @author Benedict Wilkins
 *
 */
public interface DateTimeInterface {

  public Number getTime();

  public Number getDate();

  public String representTime();

  public String representDate();

  public String representDateTime();
}
