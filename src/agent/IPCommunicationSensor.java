package agent;

import environment.communication.module.CommunicationModeReceiver;
import environment.communication.module.CommunicationModule;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * An {@link Sensor} that is to be used for IP communication. To receive
 * messages from an {@link IpCommunicationActuator} of a different agent. <br/>
 * Extends: {@link AbstractSensor}
 * 
 * @author Benedict Wilkins
 *
 */
public class IpCommunicationSensor extends AbstractSensor {

  private CommunicationModule module;

  /**
   * Constructor.
   * 
   * @param hostName
   *          address to connect to
   * @param port
   *          to to connect to
   */
  public IpCommunicationSensor(String hostName, Integer port) {
    module = new CommunicationModule(hostName, port,
        CommunicationModeReceiver.class);
  }

  /**
   * Starts to listen for incoming messages.
   */
  public void listen() {
    module.start();
  }

  /**
   * Unused by an {@link IpCommunicationSensor} replaced by:
   * {@link IpCommunicationSensor#listen()}.
   */
  @Override
  public void update(CustomObservable observable, Object arg) {
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
