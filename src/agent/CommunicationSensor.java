package agent;

import agent.general.GeneralAgentSensor;
import environment.AbstractEnvironment;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The {@link Sensor} implementation for agents in the
 * {@link NationalGridUniverse}. Provides no special functionality. Should be
 * used in place of {@link IpCommunicationSensor} during simulation. <br>
 * Extends: {@link GeneralAgentSensor}.
 * 
 * @author Benedict Wilkins
 *
 * @param <B>
 *          the type of the {@link AbstractAgent} associated with this
 *          {@link CommunicationSensor}.
 * @param <E>
 *          the type of the {@link AbstractEnvironment} that this
 *          {@link CommunicationSensor} will send {@link Event}s to.
 */
public class CommunicationSensor<B extends AbstractAgent, E extends AbstractEnvironment>
    extends GeneralAgentSensor<B, E> {

  /**
   * Constructor. Arguments should be the same class as the class parameters E
   * and B.
   * 
   * @param bodyclass
   *          {@link AbstractAgent} sub class associated with this
   *          {@link CommunicationSensor}
   * @param environmentclass
   *          {@link AbstractEnvironment} sub class that this
   *          {@link CommunicationSensor} will send {@link Event}s to
   */
  public CommunicationSensor(Class<B> bodyclass, Class<E> environmentclass) {
    super(bodyclass, environmentclass);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (this.getEnvironmentclass().isAssignableFrom(observable.getClass())) {
      handleEnvironmentMessage(arg);
    }
  }

  private void handleEnvironmentMessage(Object arg) {
    notifyObservers(arg, this.getBodyclass());
  }
}
