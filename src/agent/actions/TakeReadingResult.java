package agent.actions;

import agent.communication.MessagePayload;
import environment.communication.module.Address;
import machinelearning.agent.AbstractDataFrameRow;
import machinelearning.agent.DataFrameRowReading;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;

import java.util.List;

/**
 * A {@link Result} that represents a reading from a SmartMeter. It contains
 * single single numeric value which is the energy reading. <br/>
 * Extends: {@link DefaultActionResult}.
 * 
 * @author Benedict Wilkins
 *
 */
public class TakeReadingResult extends GlobalResult {

  /**
   * Constructor.
   * 
   * @param payload
   *          a message containing a {@link AbstractDataFrameRow} that contains a time
   *          value and a reading
   * @param actor
   *          the agent who performed the action
   * @param reading
   *          that was taken
   * @param dateTime
   *          that the reading was taken
   * @param result
   *          see: {@link ActionResult}
   * @param failureReason
   *          if not {@link ActionResult#ACTION_DONE}
   * @param recipients
   *          a list of agent {@link Address}es - to give this result to
   */
  public TakeReadingResult(MessagePayload<DataFrameRowReading> payload,
      Actor actor, ActionResult result, Exception failureReason,
      List<Address> recipients) {
    super(payload, actor, result, failureReason, recipients);
  }

  /**
   * Getter for the stored value.
   * 
   * @return the reading
   */
  public Double getReading() {
    return ((DataFrameRowReading) this.getPayload().getPayload()).getReading();
  }

  /**
   * Getter for the date and time of the reading.
   * 
   * @return the date time for the reading
   */
  public String getDateTime() {
    return ((DataFrameRowReading) this.getPayload().getPayload()).getDateTime();
  }

  @Override
  public String toString() {
    return super.toString() + ":" + this.getPayload();
  }

}
