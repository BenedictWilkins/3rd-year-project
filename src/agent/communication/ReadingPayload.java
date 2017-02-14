package agent.communication;

import machinelearning.agent.DataFrameRowReading;

public class ReadingPayload extends AbstractMessagePayload<DataFrameRowReading> {

  private static final long serialVersionUID = 1L;

  public ReadingPayload(String dateTime, Double reading) {
    super(new DataFrameRowReading(dateTime, reading));
  }
}
