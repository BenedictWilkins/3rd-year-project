package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;

import java.util.Set;

public abstract class GeneralAgentMind<B extends GeneralAgentBrain<?, ?>>
    extends AbstractAgentMind {

  private Class<B> brainclass;
  private Set<Class<? extends AbstractAction>> possibleActions;
  private GeneralAgentBody<?> body;
  private boolean realised = false;

  protected GeneralAgentMind(Class<B> brainclass,
      Set<Class<? extends AbstractAction>> possibleActions) {
    this.possibleActions = possibleActions;
    this.brainclass = brainclass;
  }

  protected final Class<B> getBrainClass() {
    return brainclass;
  }

  public Set<Class<? extends AbstractAction>> getPossibleActions() {
    return possibleActions;
  }

  protected GeneralAgentBody<?> getBody() {
    return body;
  }

  protected void setBody(GeneralAgentBody<?> generalAgentBody) {
    if (!realised) {
      this.body = generalAgentBody;
    } else {
      throw new IllegalAccessError(
          "Once a body has been assigned it cannot be changed");
    }

  }
}
