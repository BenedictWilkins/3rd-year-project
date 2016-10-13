package agent.actions;

import environment.HousePhysics_SIM;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

public class RecordAction extends AbstractAction {

	@Override
	public boolean isPossible(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).isPossible(RecordAction.this, context);
	}

	@Override
	public boolean isNecessary(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).isNecessary(RecordAction.this, context);
	}

	@Override
	public Result perform(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).perform(RecordAction.this, context);
	}

	@Override
	public boolean succeeded(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).succeeded(RecordAction.this, context);
	}

}
