package agent.actions;

import environment.communication.module.Address;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import utilities.DateTime;

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

  // the energy reading value
  private Double reading = null;
  private DateTime dateTime = null;

  /**
   * Constructor.
   * 
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
  public TakeReadingResult(Actor actor, Double reading, DateTime dateTime,
      ActionResult result, Exception failureReason, List<Address> recipients) {
    super(null, actor, result, failureReason, recipients);
    this.reading = reading;
    this.dateTime = dateTime;
  }

  /**
   * Constructor.
   * 
   * @param payload
   *          a message
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
  public TakeReadingResult(String payload, Actor actor, Double reading,
      DateTime dateTime, ActionResult result, Exception failureReason,
      List<Address> recipients) {
    super(payload, actor, result, failureReason, recipients);
    this.reading = reading;
    this.dateTime = dateTime;
  }

  /**
   * Getter for the stored value.
   * 
   * @return the reading
   */
  public Double getReading() {
    return this.reading;
  }

  /**
   * Getter for the date and time of the reading.
   * 
   * @return the date time for the reading
   */
  public DateTime getDateTime() {
    return this.dateTime;
  }

  @Override
  public String toString() {
    return super.toString() + ":" + this.reading;
  }
}
