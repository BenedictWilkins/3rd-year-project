package agent.general;

import environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * An abstract class extending {@link AbstractSensor}. See
 * {@link GeneralAgentBody} for a more detailed explanation of the 'General'
 * agent part.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class GeneralAgentSensor extends AbstractSensor {

  // class of the body this sensor is linked to
  private Class<?> bodyclass;
  // class of the environment this sensor performs in
  private Class<?> environmentclass;

  /**
   * Constructor.
   * 
   * @param bodyclass
   *          class of the {@link GeneralAgentBody} that this {@link Sensor} is
   *          linked to
   * @param environmentclass
   *          class of the {@link AbstractEnvironment} that this
   *          {@link Actuator} will sense from
   */
  public GeneralAgentSensor(Class<? extends GeneralAgentBody> bodyclass,
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
