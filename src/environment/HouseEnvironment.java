package environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import agent.SmartMeterAgentBody;
import agent.SmartMeterAgentSensor;
import agent.actions.HouseEvent;

/**
 * Simulated environment that represents a house-hold. A single
 * {@link SmartMeterAgentBody} may be placed in this {@link HouseEnvironment}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEnvironment extends AbstractEnvironment {

	public HouseEnvironment(HouseEnvironmentSpace state,
			Set<Class<? extends AbstractAction>> admissibleActions,
			Set<Body> bodies, Physics physics, Boolean bounded,
			SimpleEnvironmentAppearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
	}
	
	public SmartMeterAgentBody getSmartMeterAgent() {
		return (SmartMeterAgentBody) this.getBodies().toArray()[0];
	}

	@Override
	public Boolean isSimple() {
		return true;
	}

	@Override
	public void update(CustomObservable o, Object arg) {
		HouseEvent e = (HouseEvent)arg;
		Result r = e.attempt(getPhysics(), getState());
		notifyObservers(r, SmartMeterAgentSensor.class);
	}
}
