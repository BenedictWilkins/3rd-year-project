package environment;

import agent.CommunicationSensor;
import agent.SmartMeterAgentBody;
import agent.actions.CommunicationAction;
import agent.actions.GlobalResult;
import agent.actions.HouseEvent;
import agent.actions.TakeReadingAction;
import agent.communication.NetworkObject;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Simulated environment that represents a house-hold. A single
 * {@link SmartMeterAgentBody} may be placed in this {@link HouseEnvironment}. <br>
 * Extends: {@link AbstractEnvironment}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEnvironment extends AbstractEnvironment {

  public static final Set<Class<? extends AbstractAction>> HOUSEACTIONS;

  static {
    Set<Class<? extends AbstractAction>> actions = new HashSet<>();
    actions.add(TakeReadingAction.class);
    actions.add(CommunicationAction.class);
    HOUSEACTIONS = Collections.unmodifiableSet(actions);
  }

  /**
   * Constructor. See
   * {@link AbstractEnvironment#AbstractEnvironment(Space, Set, Set, Physics, Boolean, Appearance)}
   */
  public HouseEnvironment(HouseEnvironmentSpace state, Set<Body> bodies,
      Physics physics, Boolean bounded, HouseEnvironmentAppearance appearance) {
    super(state, HOUSEACTIONS, bodies, physics, bounded, appearance);
    bodies.forEach((Body body) -> this.getAppearance().setName(
        (String) body.getId()));
  }

  /**
   * Gets the {@link SmartMeterAgentBody} that is situated in this
   * {@link HouseEnvironment}.
   * 
   * @return the {@link SmartMeterAgentBody}
   */
  public SmartMeterAgentBody getSmartMeterAgent() {
    return (SmartMeterAgentBody) this.getBodies().toArray()[0];
  }

  @Override
  public Boolean isSimple() {
    return true;
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    HouseEvent event = (HouseEvent) arg;
    Result result = event.attempt(getPhysics(), getState());
    if (!NetworkObject.class.isAssignableFrom(((GlobalResult) result)
        .getPayload().getPayload().getClass())) {
      notifyObservers(result, CommunicationSensor.class);
    }
  }

  @Override
  public HouseEnvironmentAppearance getAppearance() {
    return (HouseEnvironmentAppearance) super.getAppearance();
  }
}
