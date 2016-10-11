package environment;

import java.util.Set;

import agent.SmartMeterAgentBody_SIM;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.SimpleEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * Simulated environment that represents a house-hold. A single
 * {@link SmartMeterAgentBody_SIM} may be placed in this {@link House_SIM}.
 * 
 * @author Benedict Wilkins
 *
 */
public class House_SIM extends SimpleEnvironment {

	public House_SIM(EnvironmentalSpace state,
			Set<Class<? extends AbstractAction>> admissibleActions,
			Set<Body> bodies, Physics physics, Boolean bounded,
			SimpleEnvironmentAppearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
		// TODO Auto-generated constructor stub
	}
}
