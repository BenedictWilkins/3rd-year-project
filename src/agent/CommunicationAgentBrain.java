package agent;

import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.general.GeneralAgentBody;
import agent.general.GeneralAgentBrain;
import agent.general.GeneralAgentMind;

import java.util.HashSet;
import java.util.Set;

public abstract class CommunicationAgentBrain<M extends GeneralAgentMind<?>, B extends GeneralAgentBody<?>>
    extends GeneralAgentBrain<M, B> {

  public Set<NetworkObject> comPerceptions;

  public CommunicationAgentBrain(Class<M> mindclass, Class<B> bodyclass) {
    super(mindclass, bodyclass);
    comPerceptions = new HashSet<>();
  }

  protected Set<NetworkObject> getCommunicationPerceptions() {
    System.out.println("GETTING COMMUNICATION PERCEPTION");
    Set<NetworkObject> toReturn = new HashSet<>();
    toReturn.addAll(comPerceptions);
    comPerceptions.clear();
    return toReturn;
  }

  protected void addCommunicationPerception(NetworkObjectPayload arg) {
    System.out.println("ADDING COMMUNICATION PERCEPTION");
    // an agent should receive NetworkObjectPayloads from the body
    comPerceptions.add(((NetworkObjectPayload) arg).getPayload());
  }
}
