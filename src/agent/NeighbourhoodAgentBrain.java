package agent;

import agent.SmartMeterAgentBrain.PerceptionWrapper;
import agent.actions.GlobalResult;
import agent.actions.PerceiveAction;
import agent.communication.NetworkObjectPayload;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import javax.xml.transform.Result;

public class NeighbourhoodAgentBrain extends
    CommunicationAgentBrain<NeighbourhoodAgentMind, NeighbourhoodAgentBody> {

  public NeighbourhoodAgentBrain() {
    super(NeighbourhoodAgentMind.class, NeighbourhoodAgentBody.class);
    // TODO Auto-generated constructor stub
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
      super.addCommunicationPerception((NetworkObjectPayload) arg);
    } else {
      System.err.println(this.getClass() + " SHOULD NOT BE RECEIVING: " + arg);
    }
  }

  private void handleMindMessage(Object arg) {
    if (PerceiveAction.class.isAssignableFrom(arg.getClass())) {
      // the mind is requesting the perceptions
      notifyObservers(super.getCommunicationPerceptions(), this.getMindclass());
    } else {
      notifyObservers(arg, this.getBodyclass());
    }
  }
}
