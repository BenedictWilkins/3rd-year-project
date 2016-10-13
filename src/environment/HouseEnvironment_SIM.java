package environment;

import java.util.Set;

import environment.communication.CommunicationEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import agent.SmartMeterAgentBody_SIM;

/**
 * Simulated environment that represents a house-hold. A single
 * {@link SmartMeterAgentBody_SIM} may be placed in this {@link HouseEnvironment_SIM}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEnvironment_SIM extends CommunicationEnvironment {

	public HouseEnvironment_SIM(HouseSpace_SIM state,
			Set<Class<? extends AbstractAction>> admissibleActions,
			Set<Body> bodies, Physics physics, Boolean bounded,
			SimpleEnvironmentAppearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
	}
	
	private SmartMeterAgentBody_SIM getSmartMeterAgent() {
		return (SmartMeterAgentBody_SIM) this.getBodies().toArray()[0];
	}

	@Override
	public Boolean isSimple() {
		return true;
	}

	@Override
	public void update(CustomObservable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
