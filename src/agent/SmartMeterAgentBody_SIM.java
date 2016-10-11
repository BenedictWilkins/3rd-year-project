package agent;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentBody_SIM extends AbstractAgent {

	public SmartMeterAgentBody_SIM(List<Sensor> sensors, List<Actuator> actuators,
			SmartMeterAgentMind_SIM mind, SmartMeterAgentBrain_SIM brain) {
		super(null, sensors, actuators, mind, brain);
	}

	@Override
	public Object simulate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(CustomObservable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
