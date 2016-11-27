package environment;

import agent.CommunicationSensor;
import agent.SmartMeterAgentBody;
import agent.actions.HouseEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

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

  /**
   * Constructor. See
   * {@link AbstractEnvironment#AbstractEnvironment(Space, Set, Set, Physics, Boolean, Appearance)}
   */
  public HouseEnvironment(HouseEnvironmentSpace state,
      Set<Class<? extends AbstractAction>> admissibleActions, Set<Body> bodies,
      Physics physics, Boolean bounded, HouseEnvironmentAppearance appearance) {
    super(state, admissibleActions, bodies, physics, bounded, appearance);
    bodies.forEach((Body body) -> this.getAppearance().setName((String)body.getId()));
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
    notifyObservers(result, CommunicationSensor.class);
  }

  @Override
  public HouseEnvironmentAppearance getAppearance() {
    return (HouseEnvironmentAppearance) super.getAppearance();
  }
}
