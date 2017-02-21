package agent.communication;

import agent.SmartMeterAgentMind;
import machinelearning.agent.DataFrameRow;
import machinelearning.agent.DataFrameRowReading;

import java.util.Arrays;
import java.util.Collection;

/**
 * A class to represent the message that a {@link SmartMeterAgentMind
 * SmartMeterAgent} may send to its manager. It contains information such as its
 * id, a list of recent readings.
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterReadingNetworkObject implements NetworkObject {

  private static final long serialVersionUID = 1L;

  private Collection<? extends DataFrameRow> data;
  private String actorid;

  public SmartMeterReadingNetworkObject(Collection<? extends DataFrameRow> list,
      String actorid) {
    super();
    this.data = list;
    this.actorid = actorid;
  }

  public Collection<? extends DataFrameRow> getData() {
    return data;
  }

  public void setData(Collection<DataFrameRowReading> data) {
    this.data = data;
  }

  public String getActorid() {
    return actorid;
  }

  public void setActorid(String actorid) {
    this.actorid = actorid;
  }

  @Override
  public String toString() {
    return this.actorid + Arrays.toString(this.data.toArray());
  }

}
