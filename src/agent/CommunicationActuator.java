package agent;

import agent.general.GeneralAgentActuator;
import environment.AbstractEnvironment;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The {@link Actuator} implementation for agents in the
 * {@link NationalGridUniverse}. Provides no special functionality. Should be
 * used in place of {@link IpCommunicationActuator} during simulation. <br>
 * Extends: {@link GeneralAgentActuator}.
 * 
 * @author Benedict Wilkins
 *
 * @param <B>
 *          the type of the {@link AbstractAgent} associated with this
 *          {@link CommunicationActuator}.
 * @param <E>
 *          the type of the {@link AbstractEnvironment} that this
 *          {@link CommunicationActuator} will send {@link Event}s to.
 */
public class CommunicationActuator<B extends AbstractAgent, E extends AbstractEnvironment>
    extends GeneralAgentActuator<B, E> {

  /**
   * Constructor. Arguments should be the same class as the class parameters E
   * and B.
   * 
   * @param bodyclass
   *          {@link AbstractAgent} sub class associated with this
   *          {@link CommunicationActuator}
   * @param environmentclass
   *          {@link AbstractEnvironment} sub class that this
   *          {@link CommunicationActuator} will send {@link Event}s to
   */
  public CommunicationActuator(Class<B> bodyclass, Class<E> environmentclass) {
    super(bodyclass, environmentclass);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (this.getBodyclass().isAssignableFrom(observable.getClass())) {
      handleBodyMessage(arg);
    }
  }

  private void handleBodyMessage(Object arg) {
    notifyObservers(arg, this.getEnvironmentclass());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
