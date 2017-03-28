package agent;

import agent.general.GeneralAgentBody;
import environment.NationalGridUniverse;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.List;

/**
 * The {@link Body} implementation for a Neighbourhood Agent (also known as a
 * Group agent) - an agent that resides in the {@link NationalGridUniverse} and
 * acts as the manager agent/data receiver for the {@link SmartMeterAgent}s. <br>
 * Extends: {@link GeneralAgentBody}.
 * 
 * @author Benedict Wilkins
 *
 */
public class NeighbourhoodAgentBody extends GeneralAgentBody {

  /**
   * Constructor. See:
   * {@link AbstractAgent#
   * AbstractAgent(AbstractAgentAppearance, List, List, AbstractAgentMind, AbstractAgentBrain)}
   * .
   */
  public NeighbourhoodAgentBody(AbstractAgentAppearance appearance,
      List<Sensor> sensors, List<Actuator> actuators,
      NeighbourhoodAgentMind mind, NeighbourhoodAgentBrain brain) {
    super(appearance, sensors, actuators, mind, brain);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (this.getBrainclass().isAssignableFrom(observable.getClass())) {
      handleBrainMessage(arg);
    } else if (CommunicationSensor.class
        .isAssignableFrom(observable.getClass())) {
      handleSensorMessage(arg);
    }
  }

  private void handleBrainMessage(Object arg) {
    notifyObservers(arg, CommunicationActuator.class);
  }

  private void handleSensorMessage(Object arg) {
    notifyObservers(arg, this.getBrainclass());
  }

  @Override
  public Object simulate() {
    return null;
  }

}
