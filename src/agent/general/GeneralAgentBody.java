package agent.general;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

import java.util.List;

public abstract class GeneralAgentBody<B extends GeneralAgentBrain<?, ?>>
    extends AbstractAgent {

  private Class<B> brainclass;

  public GeneralAgentBody(AbstractAgentAppearance appearance,
      List<Sensor> sensors, List<Actuator> actuators, GeneralAgentMind<?> mind,
      GeneralAgentBrain<?, ?> brain, Class<B> brainclass) {
    super(appearance, sensors, actuators, mind, brain);
    this.brainclass = brainclass;
    mind.setBody(this);
  }

  public Class<B> getBrainclass() {
    return brainclass;
  }
}
