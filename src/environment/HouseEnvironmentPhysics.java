package environment;

import agent.actions.CommunicationAction;
import agent.actions.CommunicationEvent;
import agent.actions.GlobalResult;
import agent.actions.TakeReadingAction;
import agent.actions.TakeReadingResult;
import agent.communication.ReadingPayload;
import housemodels.HalfHourClock;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import utilities.DateTime;

/**
 * The {@link Physics} used by a {@link HouseEnvironment}. This class defines
 * what {@link Action}s do in the {@link HouseEnvironment}. <br>
 * Implements: {@link NationalGridUniversePhysicsInterface}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEnvironmentPhysics implements
    NationalGridUniversePhysicsInterface {

  @Override
  public Result attempt(Event event, Space space) {
    // System.out.println(this.getClass().getSimpleName() +
    // " ATTEMPTING EVENT: "
    // + event.getAction());
    return event.getAction().attempt(this, space);
  }

  // *********** RECORD ACTION METHODS *********** //

  @Override
  public boolean isPossible(TakeReadingAction action, Space context) {
    return true;
  }

  @Override
  public boolean isNecessary(TakeReadingAction action, Space context) {
    return true;
  }

  @Override
  public Result perform(TakeReadingAction action, Space context) {
    DateTime dt = HalfHourClock.getInstance().getDateTime().clone();
    Double result = ((HouseEnvironmentSpace) context).getReading(dt);
    return new TakeReadingResult(new ReadingPayload(dt.toString(), result),
        action.getActor(), ActionResult.ACTION_DONE, null, null);
  }

  @Override
  public boolean succeeded(TakeReadingAction action, Space context) {
    return true;
  }

  // *********** REPORT ACTION METHODS *********** //

  @Override
  public boolean isPossible(CommunicationAction<?> action, Space context) {
    return true;
  }

  @Override
  public boolean isNecessary(CommunicationAction<?> action, Space context) {
    return true;
  }

  @Override
  public Result perform(CommunicationAction<?> action, Space context) {
    ((HouseEnvironmentSpace) context).notifyObservers(new CommunicationEvent(
        action, null, action.getActor()));
    return new GlobalResult(null, action.getActor(), ActionResult.ACTION_DONE,
        null, null);
  }

  @Override
  public boolean succeeded(CommunicationAction<?> action, Space context) {
    return true;
  }
}
