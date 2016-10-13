package agent.actions;

import environment.HousePhysics_SIM;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

public class ReportAction extends AbstractAction {

	@Override
	public boolean isPossible(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).isPossible(ReportAction.this, context);
	}

	@Override
	public boolean isNecessary(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).isNecessary(ReportAction.this, context);
	}

	@Override
	public Result perform(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).perform(ReportAction.this, context);
	}

	@Override
	public boolean succeeded(Physics physics, Space context) {
		return ((HousePhysics_SIM)physics).succeeded(ReportAction.this, context);
	}

}
