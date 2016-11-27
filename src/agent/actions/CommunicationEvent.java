package agent.actions;

import environment.HouseEnvironment;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

import java.awt.Event;

/**
 * An {@link Event} that is used for communication between agents. <br>
 * Extends: {@link AbstractEvent}. Specifically for communicating
 * {@link CommunicationAction}s from the {@link HouseEnvironment} out to the
 * {@link NationalGridUniverse}.
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationEvent extends AbstractEvent {

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
  public CommunicationEvent(EnvironmentalAction action, Long timestamp,
      Actor actor) {
    super(action, timestamp, actor);
  }

  @Override
  public String represent() {
    return this.getClass().getSimpleName();
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
