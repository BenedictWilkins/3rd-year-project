package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;

import java.util.Set;

public abstract class GeneralAgentMind extends AbstractAgentMind {

  private Class<?> brainclass;
  private GeneralAgentBody body;
  private Set<Class<? extends AbstractAction>> possibleActions;

  private boolean realised = false;

  protected GeneralAgentMind(Class<? extends GeneralAgentBrain> brainclass,
      Set<Class<? extends AbstractAction>> possibleActions) {
    this.possibleActions = possibleActions;
    this.brainclass = brainclass;
  }

  protected final Class<?> getBrainClass() {
    return brainclass;
  }

  public Set<Class<? extends AbstractAction>> getPossibleActions() {
    return possibleActions;
  }

  protected GeneralAgentBody getBody() {
    return body;
  }

  protected void setBody(GeneralAgentBody generalAgentBody) {
    if (!realised) {
      this.body = generalAgentBody;
    } else {
      throw new IllegalAccessError(
          "Once a body has been assigned it cannot be changed");
    }

  }
}
