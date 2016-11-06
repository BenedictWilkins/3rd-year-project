package agent.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import environment.NationalGridUniversePhysicsInterface;

public class RecordAction extends AbstractAction {

	@Override
	public boolean isPossible(Physics physics, Space context) {
		return ((NationalGridUniversePhysicsInterface)physics).isPossible(this, context);
	}

	@Override
	public boolean isNecessary(Physics physics, Space context) {
		return ((NationalGridUniversePhysicsInterface)physics).isNecessary(this, context);
	}

	@Override
	public Result perform(Physics physics, Space context) {
		return ((NationalGridUniversePhysicsInterface)physics).perform(this, context);
	}

	@Override
	public boolean succeeded(Physics physics, Space context) {
		return ((NationalGridUniversePhysicsInterface)physics).succeeded(this, context);
	}

}
