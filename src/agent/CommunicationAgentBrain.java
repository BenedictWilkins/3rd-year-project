package agent;

import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.general.GeneralAgentBody;
import agent.general.GeneralAgentBrain;
import agent.general.GeneralAgentMind;

import java.util.HashSet;
import java.util.Set;

public abstract class CommunicationAgentBrain extends GeneralAgentBrain {

  public Set<NetworkObject> comPerceptions;

  public CommunicationAgentBrain(Class<? extends GeneralAgentMind> mindclass,
      Class<? extends GeneralAgentBody> bodyclass) {
    super(mindclass, bodyclass);
    comPerceptions = new HashSet<>();
  }

  protected Set<NetworkObject> getClearCommunicationPerceptions() {
    Set<NetworkObject> toReturn = new HashSet<>();
    toReturn.addAll(comPerceptions);
    comPerceptions.clear();
    return toReturn;
  }

  protected Set<NetworkObject> getCommunicationPerceptions() {
    return this.comPerceptions;
  }

  protected void addCommunicationPerception(NetworkObjectPayload arg) {
    // an agent should receive NetworkObjectPayloads from the body
    comPerceptions.add(((NetworkObjectPayload) arg).getPayload());
  }
}
