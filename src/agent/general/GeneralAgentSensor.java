package agent.general;

import environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;

public abstract class GeneralAgentSensor extends AbstractSensor {

  private Class<?> bodyclass;
  private Class<?> environmentclass;

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
