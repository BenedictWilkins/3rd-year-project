package agent;

import environment.HouseEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

public class SmartMeterAgentActuator extends AbstractActuator {
	@Override
	public void update(CustomObservable o, Object arg) {
		if(o instanceof SmartMeterAgentBody) {
			handleBodyMessage(arg);
		}
	}
	
	private void handleBodyMessage(Object arg) {
		notifyObservers(arg, HouseEnvironment.class);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
