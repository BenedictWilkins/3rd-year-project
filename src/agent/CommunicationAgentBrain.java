package agent;

import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.general.GeneralAgentBody;
import agent.general.GeneralAgentBrain;
import agent.general.GeneralAgentMind;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class extending {@link GeneralAgentBrain} that should be extended
 * by any {@link Brain} that expects to receive messages from other agents in
 * the form of {@link NetworkObject}s. This should be all agents residing in the
 * {@link NationalGridUniverse}. <br>
 * Extends {@link GeneralAgentBrain}. <br>
 * Known subclasses: {@link NeighbourhoodAgentBrain},
 * {@link SmartMeterAgentBrain}.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class CommunicationAgentBrain extends GeneralAgentBrain {

  // received messages
  protected Set<NetworkObject> comPerceptions;

  /**
   * Constructor. See {@link GeneralAgentBrain#GeneralAgentBrain(Class, Class)}
   * 
   * @param mindclass
   *          class of the {@link GeneralAgentMind} this {@link Brain} is linked
   *          with
   * @param bodyclass
   *          class of the {@link GeneralAgentMind} this {@link Brain} is linked
   *          with
   */
  public CommunicationAgentBrain(Class<? extends GeneralAgentMind> mindclass,
      Class<? extends GeneralAgentBody> bodyclass) {
    super(mindclass, bodyclass);
    comPerceptions = new HashSet<>();
  }

  /**
   * Gets and clears the most recent messages received.
   * 
   * @return A {@link Set} of the most recent {@link NetworkObject}s received
   */
  protected Set<NetworkObject> getClearCommunicationPerceptions() {
    Set<NetworkObject> toReturn = new HashSet<>();
    toReturn.addAll(comPerceptions);
    comPerceptions.clear();
    return toReturn;
  }

  /**
   * Getter the most the most recent perceptions.
   * 
   * @return A {@link Set} of the most recent {@link NetworkObject}s received
   */
  protected Set<NetworkObject> getCommunicationPerceptions() {
    return this.comPerceptions;
  }

  /**
   * Adds a new message by getting the {@link NetworkObject} from the given arg.
   * 
   * @param arg
   *          to get message from
   */
  protected synchronized void addCommunicationPerception(
      NetworkObjectPayload arg) {
    // an agent should receive NetworkObjectPayloads from the body
    comPerceptions.add(((NetworkObjectPayload) arg).getPayload());
  }
}
