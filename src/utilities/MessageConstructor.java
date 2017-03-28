package utilities;

import agent.actions.CommunicationAction;
import agent.actions.GlobalResult;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PhysicalBody;

/**
 * A class used to constructor and de-construct messages sent in the simulation.
 * 
 * @deprecated currently not in use. See {@link NetworkObject} and
 *             {@link NetworkObjectPayload}.
 * @author Benedict Wilkins
 *
 */
public class MessageConstructor {

  private static final String MESSAGEDELIM = "|";

  public static String constructMessage(GlobalResult result) {
    return result.getActorId() + MESSAGEDELIM + result.getPayload();
  }

  public static String constructMessage(CommunicationAction action) {
    return ((PhysicalBody) action.getActor()).getId().toString() + MESSAGEDELIM
        + action.getPayload();
  }

  public static String getSenderId(String message) {
    return message.split(MESSAGEDELIM)[0];
  }

  public static String getPayload(String message) {
    return message.split(MESSAGEDELIM)[1];
  }

}
