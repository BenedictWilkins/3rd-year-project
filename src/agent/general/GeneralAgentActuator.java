package agent.general;

import environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;

/**
 * An abstract class extending {@link AbstractActuator}. See
 * {@link GeneralAgentBody} for a more detailed explanation of the 'General'
 * agent part.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class GeneralAgentActuator extends AbstractActuator {

  // class of the body this actuator is linked to
  private Class<?> bodyclass;
  // class of the environment this actuator performs in
  private Class<?> environmentclass;

  /**
   * Constructor.
   * 
   * @param bodyclass
   *          class of the {@link GeneralAgentBody} that this {@link Actuator}
   *          is linked to
   * @param environmentclass
   *          class of the {@link AbstractEnvironment} that this
   *          {@link Actuator} will perform in
   */
  public GeneralAgentActuator(Class<? extends GeneralAgentBody> bodyclass,
      Class<? extends AbstractEnvironment> environmentclass) {
    this.bodyclass = bodyclass;
    this.environmentclass = environmentclass;
  }

  public final Class<?> getBodyclass() {
    return bodyclass;
  }

  public final Class<?> getEnvironmentclass() {
    return environmentclass;
  }
}
