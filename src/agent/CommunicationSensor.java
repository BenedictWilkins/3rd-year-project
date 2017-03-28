package agent;

import agent.actions.GlobalResult;
import agent.communication.NetworkObjectPayload;
import agent.general.GeneralAgentBody;
import agent.general.GeneralAgentSensor;
import environment.AbstractEnvironment;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The {@link Sensor} implementation for agents in the
 * {@link NationalGridUniverse}. Provides no special functionality. Should be
 * used in place of {@link IpCommunicationSensor} during simulation. <br>
 * Extends: {@link GeneralAgentSensor}. <br>
 * This type of sensor is used by {@link NeighbourhoodAgentBody} and
 * {@link SmartMeterAgentBody}.
 * 
 * 
 * @author Benedict Wilkins
 */
public class CommunicationSensor extends GeneralAgentSensor {

  /**
   * Constructor. See
   * {@link GeneralAgentSensor#GeneralAgentSensor(Class, Class)}
   * 
   * @param bodyclass
   *          class of the {@link GeneralAgentBody} this {@link Sensor} is
   *          linked with
   * @param environmentclass
   *          class of {@link AbstractEnvironment} that this {@link Sensor} will get
   *          perceptions from.
   * 
   */
  public CommunicationSensor(Class<? extends GeneralAgentBody> bodyclass,
      Class<? extends AbstractEnvironment> environmentclass) {
    super(bodyclass, environmentclass);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    // System.out.println("RECEIVED: " + arg + System.lineSeparator() + "FROM: "
    // + observable);
    // if (this.getEnvironmentclass().isAssignableFrom(observable.getClass())) {
    handleEnvironmentMessage(arg);
    // }
  }

  private void handleEnvironmentMessage(Object arg) {
    /*
     * check if it is a simulation message (NetworkObjectPayload) or a
     * GlobalResult note that in a simulation the arg will always be a global
     * result, however we want to simulate a network scenario so we should
     * decompose the result into a NetworkObjectPayload if that is what it
     * contains.
     */
    if (NetworkObjectPayload.class.isAssignableFrom(((GlobalResult) arg)
        .getPayload().getClass())) {
      // simulate network, notify with the networkobject payload
      notifyObservers(((GlobalResult) arg).getPayload(), this.getBodyclass());
    } else {
      notifyObservers(arg, this.getBodyclass());
    }
  }
}
