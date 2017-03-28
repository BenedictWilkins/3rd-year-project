package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;

import java.util.Set;

/**
 * An abstract class extending {@link AbstractAgentMind}. See
 * {@link GeneralAgentBody} for a more detailed explanation of the 'General'
 * agent part.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class GeneralAgentMind extends AbstractAgentMind {

  // class of brain this mind is linked to
  private Class<?> brainclass;
  // the body of this agent (may be null, i.e. it does not know its self,
  // realised = false)
  private GeneralAgentBody body;
  private Set<Class<? extends AbstractAction>> possibleActions;
  // once this agent has a body, it cannot be reset
  private boolean realised = false;

  /**
   * Constructor.
   * 
   * @param brainclass
   *          class of the {@link GeneralAgentBrain} that this {@link Mind} is
   *          linked to
   * @param possibleActions
   *          the {@link Set} of all possible {@link AbstractAction}s this agent can
   *          perform.
   */
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
