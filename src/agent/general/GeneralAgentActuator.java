package agent.general;

import environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;

public abstract class GeneralAgentActuator<B extends AbstractAgent, E extends AbstractEnvironment>
    extends AbstractActuator {

  private Class<B> bodyclass;
  private Class<E> environmentclass;

  public GeneralAgentActuator(Class<B> bodyclass, Class<E> environmentclass) {
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
