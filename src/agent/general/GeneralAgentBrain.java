package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;

public abstract class GeneralAgentBrain<M extends AbstractAgentMind, B extends AbstractAgent>
    extends AbstractAgentBrain {

  private Class<M> mindclass;
  private Class<B> bodyclass;

  public GeneralAgentBrain(Class<M> mindclass, Class<B> bodyclass) {
    this.mindclass = mindclass;
    this.bodyclass = bodyclass;
  }

  public Class<M> getMindclass() {
    return mindclass;
  }

  public Class<B> getBodyclass() {
    return bodyclass;
  }
}
