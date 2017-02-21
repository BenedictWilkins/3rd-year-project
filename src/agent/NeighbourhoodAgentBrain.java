package agent;

import agent.actions.PerceiveAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.Set;

public class NeighbourhoodAgentBrain extends CommunicationAgentBrain {

  public NeighbourhoodAgentBrain(
      Class<? extends NeighbourhoodAgentMind> mindclass,
      Class<? extends NeighbourhoodAgentBody> bodyclass) {
    super(mindclass, bodyclass);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (this.getBodyclass().isAssignableFrom(observable.getClass())) {
      handleBodyMessage(arg);
    } else if (this.getMindclass().isAssignableFrom(observable.getClass())) {
      handleMindMessage(arg);
    }
  }

  /**
   * Stores all {@link Result}s received from the {@link Body}.
   * 
   * @param arg
   *          Received from the body - to store
   */
  private void handleBodyMessage(Object arg) {
    if (NetworkObjectPayload.class.isAssignableFrom(arg.getClass())) {
      // System.out.println("RECEIVED: " + arg);
      super.addCommunicationPerception((NetworkObjectPayload) arg);
      // System.out
      // .println("HANDLEBODY: " + Arrays
      // .toString(((SmartMeterReadingNetworkObject) ((NetworkObjectPayload)
      // arg)
      // .getPayload()).getData().toArray()));
    } else {
      System.err.println(this.getClass() + " SHOULD NOT BE RECEIVING: " + arg);
    }
  }

  private void handleMindMessage(Object arg) {
    if (PerceiveAction.class.isAssignableFrom(arg.getClass())) {
      // the mind is requesting the perceptions
      notifyObservers(
          new PerceptionWrapper(super.getClearCommunicationPerceptions()),
          this.getMindclass());
    } else {
      notifyObservers(arg, this.getBodyclass());
    }
  }

  protected class PerceptionWrapper {
    private Set<NetworkObject> messages;

    private PerceptionWrapper(Set<NetworkObject> messages) {
      this.messages = messages;
    }

    public Set<NetworkObject> getMessages() {
      return messages;
    }

    public void setMessages(Set<NetworkObject> messages) {
      this.messages = messages;
    }
  }
}
