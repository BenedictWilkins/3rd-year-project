package environment;

import agent.CommunicationSensor;
import agent.NeighbourhoodAgentBody;
import agent.actions.CommunicationAction;
import agent.actions.GlobalResult;
import agent.actions.TakeReadingAction;
import agent.general.GeneralAgentBody;
import environment.communication.module.Address;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

  private Map<String, CustomObserver> observables = null;

  public void setUpCommunication(List<HouseEnvironment> houseSubEnvironments,
      Set<Body> bodies) {
    if (observables != null) {
      throw new IllegalStateException("COMMUNICATION HAS ALREADY BEEN SET UP");
    }
    observables = new HashMap<>();
    houseSubEnvironments.forEach((HouseEnvironment house) -> observables.put(
        house.getAppearance().getName(), house));
    bodies.forEach((Body body) -> observables.put(body.getId().toString(),
        ((GeneralAgentBody) body).getSensor(CommunicationSensor.class)));
  }

  @Override
  public Result attempt(Event event, Space space) {
    // System.out.println(this.getClass().getSimpleName() +
    // " ATTEMPTING EVENT: "
    // + event.getAction());
    return event.getAction().attempt(this, space);
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

    List<Address> recipients = action.getRecipients();
    GlobalResult result = new GlobalResult(action.getPayload(),
        action.getActor(), ActionResult.ACTION_DONE, null, null);

    recipients.forEach((Address a) -> {
      // System.out.println("NATIONAL MESSAGE FROM: " + action.getActor()
      // + " TO: " + a.getAdress());
        result.setRecipientsIds(Arrays.asList(a.getAdress()));
        CustomObserver obs = observables.get(a.getAdress());

        obs.update((CustomObservable) action.getActor(), result);
      });
    result.setRecipientsIds(GlobalResult.convertAddressToString(recipients));
    return result;
  }

  @Override
  public boolean succeeded(CommunicationAction<?> action, Space context) {
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
