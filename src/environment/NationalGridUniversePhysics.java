package environment;

import agent.actions.CommunicationAction;
import agent.actions.GlobalResult;
import agent.actions.TakeReadingAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import utilities.MessageConstructor;

/**
 * The {@link Physics} used by a {@link NationalGridUniverse}. This class
 * defines what {@link Action}s do in the {@link NationalGridUniverse}. <br>
 * Implements: {@link NationalGridUniversePhysicsInterface}.
 * 
 * @author Benedict Wilkins
 *
 */
public class NationalGridUniversePhysics implements
    NationalGridUniversePhysicsInterface {

  @Override
  public Result attempt(Event event, Space space) {
    System.out.println("PHYSICS ATTEMPTING EVENT: " + event.getAction());
    return event.getAction().attempt(this, space);
  }

  // *********** REPORT ACTION METHODS *********** //
  @Override
  public boolean isPossible(CommunicationAction action, Space context) {
    return true;
  }

  @Override
  public boolean isNecessary(CommunicationAction action, Space context) {
    return true;
  }

  @Override
  public Result perform(CommunicationAction action, Space context) {
    System.out.println("FORWARD THE REPORT!");
    GlobalResult result = new GlobalResult(
        MessageConstructor.constructMessage(action), action.getActor(),
        ActionResult.ACTION_DONE, null, action.getRecipients());
    return result;
  }

  @Override
  public boolean succeeded(CommunicationAction action, Space context) {
    return true;
  }

  // *********** EVENT UNSUPPORTED BY THIS PHYSICS *********** //
  // *********** RECORD ACTION METHODS *********** //
  @Override
  public boolean isPossible(TakeReadingAction action, Space context) {
    return false;
  }

  @Override
  public boolean isNecessary(TakeReadingAction action, Space context) {
    return false;
  }

  @Override
  public Result perform(TakeReadingAction action, Space context) {
    return new DefaultActionResult(ActionResult.ACTION_IMPOSSIBLE, null);
  }

  @Override
  public boolean succeeded(TakeReadingAction action, Space context) {
    return false;
  }
}
