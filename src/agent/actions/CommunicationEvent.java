package agent.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

public class CommunicationEvent extends AbstractEvent {

	public CommunicationEvent(EnvironmentalAction action, Long timestamp,
			Actor actor) {
		super(action, timestamp, actor);
	}

	@Override
	public String represent() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean isPossible(Physics physics, Space context) {
		return getAction().isPossible(physics, context);
	}

	@Override
	public boolean isNecessary(Physics physics, Space context) {
		return getAction().isNecessary(physics, context);
	}

	@Override
	public Result perform(Physics physics, Space context) {
		return getAction().perform(physics, context);
	}

	@Override
	public boolean succeeded(Physics physics, Space context) {
		return getAction().succeeded(physics, context);
	}
}
