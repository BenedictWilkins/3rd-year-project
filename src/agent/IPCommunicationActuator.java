package agent;

import environment.communication.module.CommunicationModeSender;
import environment.communication.module.CommunicationModule;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * An {@link Actuator} that is to be used for IP communication. To send messages
 * to an {@link IpCommunicationSensor} of a different agent. <br/>
 * Extends: {@link AbstractActuator}
 * 
 * @author Benedict Wilkins
 *
 */
public class IpCommunicationActuator extends AbstractActuator {

  private CommunicationModule module;

  /**
   * Constructor.
   * 
   * @param hostName
   *          address to connect to
   * @param port
   *          to to connect to
   */
  public IpCommunicationActuator(String hostName, Integer port) {
    module = new CommunicationModule(hostName, port,
        CommunicationModeSender.class);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (observable instanceof SmartMeterAgentBody) {
      handleBodyMessage(arg);
    }
  }

  // Handles a message from the body - sends a new message
  private void handleBodyMessage(Object arg) {
    System.out.println("DO SOME COMMUNICATION");
    module.start();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
