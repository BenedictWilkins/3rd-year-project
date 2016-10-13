package environment.communication;

import java.util.Set;

import environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

public abstract class CommunicationEnvironment extends AbstractEnvironment {

	public CommunicationEnvironment(CommunicationSpace state,
			Set<Class<? extends AbstractAction>> admissibleActions,
			Set<Body> bodies, Physics physics, Boolean bounded,
			Appearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
	}
	
	public void start() {
		((CommunicationSpace)this.getState()).getModule().start();
	}
	
	@Override
	public CommunicationSpace getState() {
		return (CommunicationSpace) super.getState();
	}
	
	@Override
	public void setState(Space state) {
		super.setState((CommunicationSpace)state);
	}
}
