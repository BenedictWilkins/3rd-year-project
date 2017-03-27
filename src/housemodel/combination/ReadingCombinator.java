package housemodel.combination;

import agent.communication.SmartMeterReadingNetworkObject;
import machinelearning.agent.DataFrameRow;
import machinelearning.agent.DataFrameRowReading;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A complex {@link Combinator} that combines a {@link Collection} of
 * {@link SmartMeterReadingNetworkObject}s into a {@link List} of
 * {@link DataFrameRowReading}s. Each reading in the
 * {@link SmartMeterReadingNetworkObject} is combined with readings from the
 * other {@link SmartMeterReadingNetworkObject} that were taken at the same time
 * interval. The combination of the readings is computed by a given sub
 * {@link Combinator} that supports combination of {@link Double} values, this
 * defaults to the {@link AdditiveCombinator} when using an empty constructor.
 * 
 * @author Benedict Wilkins
 *
 */
public class ReadingCombinator implements
    Combinator<SmartMeterReadingNetworkObject, List<DataFrameRowReading>> {

  // used to combine readings
  private Combinator<Double, Double> combinator;
  private Integer expectedReadings = -1;

  /**
   * Constructor.
   * 
   * @param combinator
   *          used to combine readings that occured within the same time
   *          interval
   */
  public ReadingCombinator(Combinator<Double, Double> combinator) {
    this.combinator = combinator;
  }

  /**
   * Constructor. Default {@link Combinator} set to {@link AdditiveCombinator}.
   */
  public ReadingCombinator() {
    this.combinator = new AdditiveCombinator();
  }

  public Map<String, List<Double>> getDataFrameRowReadingsMap(
      Collection<SmartMeterReadingNetworkObject> readings) {
    Map<String, List<Double>> map = new HashMap<>();
    readings.forEach((SmartMeterReadingNetworkObject so) -> {
      so.getData().forEach((DataFrameRow row) -> {
        DataFrameRowReading read = (DataFrameRowReading) row;
        if (!map.containsKey(read.getDateTime())) {
          List<Double> readingsAtTime = new ArrayList<>();
          readingsAtTime.add(read.getReading());
          map.put(read.getDateTime(), readingsAtTime);
        } else {
          map.get(read.getDateTime()).add(read.getReading());
        }
      });
    });
    return map;
  }

  @Override
  public List<DataFrameRowReading> combine(
      Collection<SmartMeterReadingNetworkObject> readings) {
    Map<String, List<Double>> map = getDataFrameRowReadingsMap(readings);

    List<DataFrameRowReading> result = new ArrayList<>();

    map.forEach((String dateTime, Collection<Double> values) -> {
      if (checkReadingsSize(values)) {
        insertAvg(values);
      }
      result.add(new DataFrameRowReading(dateTime, combinator.combine(values)));
    });
    return result;
  }

  private boolean checkReadingsSize(Collection<Double> readings) {
    return readings.size() < expectedReadings && expectedReadings > 0;
  }

  private void insertAvg(Collection<Double> readings) {
    int missing = expectedReadings - readings.size();
    System.out.println("MISSING READINGS: " + missing);
    if (readings.size() > 0) {
      double avg = 0;
      for (Double d : readings) {
        avg += d;
      }
      avg = avg / readings.size();
      for (int i = 0; i < missing; i++) {
        readings.add(avg);
      }
    } else {
      System.err.println("NO READINGS RECEIVED?");
    }
  }

  public Integer getExpectedReadings() {
    return expectedReadings;
  }

  public void setExpectedReadings(Integer expectedReadings) {
    this.expectedReadings = expectedReadings;
  }
}
