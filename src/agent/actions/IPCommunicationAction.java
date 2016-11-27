package agent.actions;

import agent.IpCommunicationActuator;
import agent.IpCommunicationSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;

/**
 * An {@link Action} that may be created by any IPCommunication enabled agent.
 * An agent must have a {@link IpCommunicationActuator} and
 * {@link IpCommunicationSensor} to be IPCommunication enabled. <br>
 * Implements: {@link Action}
 * 
 * @author Benedict Wilkins
 *
 */
public class IpCommunicationAction implements Action {

  /*
   * ************************************ NOTE *******************************
   * This action has no implementation yet. It will include a message that
   * should be sent via IP. The implementation of IPCommunication is not yet
   * complete (it cannot handle message parsing yet) and for simulation purposes
   * this action is not required.
   */

}
