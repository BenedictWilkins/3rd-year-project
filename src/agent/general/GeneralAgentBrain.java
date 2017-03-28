package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;

/**
 * An abstract class extending {@link AbstractAgentBrain}. See
 * {@link GeneralAgentBody} for a more detailed explanation of the 'General'
 * agent part.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class GeneralAgentBrain extends AbstractAgentBrain {

  // class of mind this brain is linked to
  private Class<?> mindclass;
  // class of body this brain is linked to
  private Class<?> bodyclass;

  /**
   * Constructor.
   * 
   * @param mindclass
   *          class of the {@link GeneralAgentMind} that this {@link Brain} is
   *          linked to
   * 
   * @param bodyclass
   *          class of the {@link GeneralAgentBody} that this {@link Brain} is
   *          linked to
   */
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
