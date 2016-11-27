package agent.actions;

import environment.HouseEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

import java.awt.Event;

/**
 * An {@link Event} that is used in the {@link HouseEnvironment} context. <br>
 * Extends: {@link AbstractEvent}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEvent extends AbstractEvent {

  /**
   * Constructor. See:
   * {@link AbstractEvent#AbstractEvent(EnvironmentalAction, Long, Actor)}.
   * 
   * @param action
   *          to executed
   * @param timestamp
   *          of the event
   * @param actor
   *          who is performing the action
   */
  public HouseEvent(EnvironmentalAction action, Long timestamp, Actor actor) {
    super(action, timestamp, actor);
  }

  @Override
  public String represent() {
    return getActor().toString() + ":" + getAction().toString() + ":"
        + getTimestamp();
  }

  @Override
  public String toString() {
    return represent();
  }

  @Override
  public boolean isPossible(Physics physics, Space context) {
    return getAction().isPossible(physics, context);
  }

  @Override
  public boolean isNecessary(Physics physics, Space context) {
    return getAction().isNecessary(physics, context);
  }

  @Override
  public Result perform(Physics physics, Space context) {
    return getAction().perform(physics, context);
  }

  @Override
  public boolean succeeded(Physics physics, Space context) {
    return getAction().succeeded(physics, context);
  }
}
