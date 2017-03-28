package agent;

import agent.actions.GlobalResult;
import agent.actions.PerceiveAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.Result;

/**
 * The {@link Brain} implementation for a Smart Meter Agent. <br>
 * Extends: {@link AbstactAgentBrain}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentBrain extends CommunicationAgentBrain {

  private Set<GlobalResult> actionResults;

  public SmartMeterAgentBrain(Class<? extends SmartMeterAgentMind> mindclass,
      Class<? extends SmartMeterAgentBody> bodyclass) {
    super(mindclass, bodyclass);
    this.actionResults = new HashSet<>();
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    // System.out.println(this + " BRAIN: RECEIVED " + arg + " FROM: "
    // + observable);
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
    } else if (GlobalResult.class.isAssignableFrom(arg.getClass())) {
      actionResults.add((GlobalResult) arg);
    }
  }

  private void handleMindMessage(Object arg) {
    if (PerceiveAction.class.isAssignableFrom(arg.getClass())) {
      // the mind is requesting the perceptions
      notifyObservers(
          new PerceptionWrapper(super.getClearCommunicationPerceptions(),
              this.getClearActionResults()), this.getMindclass());
    } else {
      notifyObservers(arg, this.getBodyclass());
    }
  }

  /**
   * Gets and clears received {@link GlobalResult}s. (These results will contain
   * energy readings)
   * 
   * @return a {@link Set} of {@link GlobalResult}s
   */
  public Set<GlobalResult> getClearActionResults() {
    Set<GlobalResult> toReturn = new HashSet<>();
    toReturn.addAll(actionResults);
    actionResults.clear();
    return toReturn;
  }

  /**
   * Wrapper class for this agents most recent perceptions.
   * 
   * @author Benedict Wilkins
   *
   */
  public class PerceptionWrapper {
    Set<NetworkObject> communicationPerceptions;
    Set<GlobalResult> actionResults;

    /**
     * Constructor.
     * 
     * @param communicationPerceptions
     *          recently received {@link NetworkObject}s
     * @param actionResults
     *          recently received {@link GlobalResult}s
     */
    public PerceptionWrapper(Set<NetworkObject> communicationPerceptions,
        Set<GlobalResult> actionResults) {
      super();
      this.communicationPerceptions = communicationPerceptions;
      this.actionResults = actionResults;
    }

    public Set<NetworkObject> getCommunicationPerceptions() {
      return communicationPerceptions;
    }

    public void setCommunicationPerceptions(
        Set<NetworkObject> communicationPerceptions) {
      this.communicationPerceptions = communicationPerceptions;
    }

    public Set<GlobalResult> getActionResults() {
      return actionResults;
    }

    public void setActionResults(Set<GlobalResult> actionResults) {
      this.actionResults = actionResults;
    }
  }
}
