package agent;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

public class SmartMeterAgentBrain extends AbstractAgentBrain {

	@Override
	public void update(CustomObservable o, Object arg) {
		if(o instanceof SmartMeterAgentBody) {
			handleBodyMessage(arg);
		} else if (o instanceof SmartMeterAgentMind) {
			handleMindMessage(arg);
		}
	}
	
	private void handleBodyMessage(Object arg) {
		notifyObservers(arg, SmartMeterAgentMind.class);
	}
	
	private void handleMindMessage(Object arg) {
		notifyObservers(arg, SmartMeterAgentBody.class);
	}
}
