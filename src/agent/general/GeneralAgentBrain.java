package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;

public abstract class GeneralAgentBrain extends AbstractAgentBrain {

  private Class<?> mindclass;
  private Class<?> bodyclass;

  public GeneralAgentBrain(Class<? extends GeneralAgentMind> mindclass,
      Class<? extends GeneralAgentBody> bodyclass) {
    this.mindclass = mindclass;
    this.bodyclass = bodyclass;
  }

  public final Class<?> getMindclass() {
    return mindclass;
  }

  public final Class<?> getBodyclass() {
    return bodyclass;
  }
}
