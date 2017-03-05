package environment;

import agent.CommunicationSensor;
import agent.SmartMeterAgentBody;
import agent.actions.CommunicationAction;
import agent.actions.GlobalResult;
import agent.actions.TakeReadingAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import housemodel.threshold.ModelModifier;

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

  // change this to change the chance of model modification, e.g. 0.5 = 50/50
  // chance to modify
  private static final Double MODIFYDECIDE = 0.1;

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
    Result result = null;
    if (Event.class.isAssignableFrom(arg.getClass())) {
      Event event = (Event) arg;
      result = event.attempt(getPhysics(), getState());

    } else if (GlobalResult.class.isAssignableFrom(arg.getClass())) {
      result = (Result) arg;
      if (ModelModifier.class.isAssignableFrom(((GlobalResult) arg)
          .getPayload().getPayload().getClass())) {
        if (Math.random() < MODIFYDECIDE) {
          modifyModel((ModelModifier) ((GlobalResult) arg).getPayload()
              .getPayload());
        }
        result = null;
      }
    } else {
      System.err.println(this + " RECEIVED BAD DATA IN UPDATE: " + arg);
    }
    if (result != null) {
      if (((GlobalResult) result).getPayload() != null) {
        // System.out.println("PASSING: " + arg);
        notifyObservers(result, CommunicationSensor.class);
      }
    }
  }

  private void modifyModel(ModelModifier modifier) {
    HouseEnvironmentSpace space = (HouseEnvironmentSpace) this.getState();
    space.modifyModel(modifier);
  }

  @Override
  public HouseEnvironmentAppearance getAppearance() {
    return (HouseEnvironmentAppearance) super.getAppearance();
  }
}
