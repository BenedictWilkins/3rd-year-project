package agent;

import agent.general.GeneralAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import javax.xml.transform.Result;

public class NeighbourhoodAgentBrain extends
    GeneralAgentBrain<NeighbourhoodAgentMind, NeighbourhoodAgentBody> {

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
    notifyObservers(arg, this.getMindclass());
  }

  private void handleMindMessage(Object arg) {
    notifyObservers(arg, this.getBodyclass());
  }

}
