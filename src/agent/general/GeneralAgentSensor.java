package agent.general;

import environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;

public abstract class GeneralAgentSensor<B extends AbstractAgent, E extends AbstractEnvironment>
    extends AbstractSensor {

  private Class<B> bodyclass;
  private Class<E> environmentclass;

  public GeneralAgentSensor(Class<B> bodyclass, Class<E> environmentclass) {
    this.bodyclass = bodyclass;
    this.environmentclass = environmentclass;
  }

  public Class<B> getBodyclass() {
    return bodyclass;
  }

  public Class<E> getEnvironmentclass() {
    return environmentclass;
  }
}
