package agent.communication;

import machinelearning.agent.DataFrameRowReading;

/**
 * A concrete implementation of {@link AbstractMessagePayload} whose payload
 * type is {@link DataFrameRowReading}. This payload will contain two values: A
 * {@link String} representing the date and time the reading was taken, and a
 * {@link Double} that is the value of the reading.
 * 
 * @author Benedict Wilkins
 *
 */
public class ReadingPayload extends AbstractMessagePayload<DataFrameRowReading> {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param dateTime
   *          that the reading was taken
   * @param reading
   *          value of the reading
   */
  public ReadingPayload(String dateTime, Double reading) {
    super(new DataFrameRowReading(dateTime, reading));
  }
}
