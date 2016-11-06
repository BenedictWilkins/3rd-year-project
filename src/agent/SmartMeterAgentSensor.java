package agent;

import environment.HouseEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

public class SmartMeterAgentSensor extends AbstractSensor{

	@Override
	public void update(CustomObservable o, Object arg) {
		if(o instanceof HouseEnvironment) {
			handleEnvironmentMessage(arg);
		}
	}
	
	private void handleEnvironmentMessage(Object arg) {
		notifyObservers(arg, SmartMeterAgentBody.class);
	}
}
