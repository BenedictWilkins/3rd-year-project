package environment;

import java.util.Set;

import environment.communication.CommunicationEnvironment;
import environment.communication.CommunicationSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

public class LayeredEnvironment extends CommunicationEnvironment {

	public LayeredEnvironment(CommunicationSpace space,
			Set<Class<? extends AbstractAction>> admissibleActions,
			Set<Body> bodies, Physics physics, Boolean bounded,
			SimpleEnvironmentAppearance appearance) {
		super(space, admissibleActions, bodies, physics, bounded, appearance);
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
