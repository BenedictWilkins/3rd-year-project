package agent;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import agent.actions.RecordAction;
import agent.actions.ReportAction;

public class SmartMeterAgentMind extends AbstractAgentMind {
	
	private Set<Class<? extends AbstractAction>> possibleActions;
	
	public SmartMeterAgentMind(Set<Class<? extends AbstractAction>> possibleActions) {
		this.possibleActions = possibleActions;
	}

	@Override
	public void perceive(Object perceptionWrapper) {
		System.out.println("I AM PERCEIVING...");
	}

	@Override
	public EnvironmentalAction decide(Object... parameters) {
		System.out.println("I AM DECIDING...");
		return null;
	}

	@Override
	public void execute(EnvironmentalAction action) {
		System.out.println("I AM EXECUTING...");
		notifyObservers(new RecordAction(), SmartMeterAgentBrain.class);
	}

	@Override
	public void update(CustomObservable o, Object arg) {
		if(!(o instanceof SmartMeterAgentBrain) || !(arg instanceof Result)) {
			return;
		}
		//process message from the brain
		DefaultActionResult r = (DefaultActionResult)arg;
		System.out.println("MIND RECIEVED: " + r);
	}
}
