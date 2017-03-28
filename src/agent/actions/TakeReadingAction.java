package agent.actions;

import agent.SmartMeterAgentBody;
import environment.HouseEnvironment;
import environment.HouseEnvironmentPhysics;
import environment.NationalGridUniversePhysicsInterface;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * An {@link Action} that created any agent ({@link SmartMeterAgentBody})
 * situated within a {@link HouseEnvironment} . This action is specifically for
 * taking an energy reading. See: {@link HouseEnvironmentPhysics} for perform
 * implementation details.
 * 
 * @author Benedict Wilkins
 *
 */
public class TakeReadingAction extends AbstractAction {

  /**
   * Constructor.
   * 
   * @param actor
   *          the {@link SmartMeterAgentBody} that is taking the {@link Action}
   */
  public TakeReadingAction(Actor actor) {
    super();
    this.setActor(actor);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }

  @Override
  public boolean isPossible(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).isPossible(this,
        context);
  }

  @Override
  public boolean isNecessary(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).isNecessary(this,
        context);
  }

  @Override
  public Result perform(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).perform(this,
        context);
  }

  @Override
  public boolean succeeded(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).succeeded(this,
        context);
  }

}
