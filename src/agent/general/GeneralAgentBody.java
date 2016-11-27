package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

import java.util.List;

public abstract class GeneralAgentBody<B extends Brain>
    extends AbstractAgent {

  private Class<B> brainclass;

  public GeneralAgentBody(AbstractAgentAppearance appearance,
      List<Sensor> sensors, List<Actuator> actuators, AbstractAgentMind mind,
      AbstractAgentBrain brain, Class<B> brainclass) {
    super(appearance, sensors, actuators, mind, brain);
    this.brainclass = brainclass;
  }

  public Class<B> getBrainclass() {
    return brainclass;
  }
}
