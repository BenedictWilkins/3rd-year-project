package agent;

import agent.actions.CommunicationAction;
import agent.actions.HouseEvent;
import agent.general.GeneralAgentActuator;
import agent.general.GeneralAgentBody;
import environment.AbstractEnvironment;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The {@link Actuator} implementation for agents in the
 * {@link NationalGridUniverse}. This actuator should be used when communicating
 * between agents in a simulation. It can process {@link CommunicationAction}s
 * properly. This class should be used in place of
 * {@link IpCommunicationActuator} during simulation. <br>
 * Extends: {@link GeneralAgentActuator}.
 * 
 * @author Benedict Wilkins
 */
public class CommunicationActuator extends GeneralAgentActuator {

  /**
   * Constructor.
   */
  public CommunicationActuator(Class<? extends GeneralAgentBody> bodyclass,
      Class<? extends AbstractEnvironment> environmentclass) {
    super(bodyclass, environmentclass);
  }

  /**
   * The {@link Object} <b> arg </b> that is provided must be a subclass of
   * {@link Action}. Specifically (for proper use) it should be a subclass of
   * {@link CommunicationAction}, if it is not a subclass of
   * {@link CommunicationAction} the action will be performed regardless but as
   * is.
   */
  @Override
  public void update(CustomObservable observable, Object arg) {
    
    if (this.getBodyclass().isAssignableFrom(observable.getClass())) {
      handleBodyMessage((AbstractAction) arg);
    }
  }

  private void handleBodyMessage(AbstractAction arg) {
    notifyObservers(
        new HouseEvent(arg, System.currentTimeMillis(), arg.getActor()),
        this.getEnvironmentclass());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
