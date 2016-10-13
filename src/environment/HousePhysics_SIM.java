package environment;

import agent.actions.RecordAction;
import agent.actions.ReportAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

public class HousePhysics_SIM implements Physics, HousePhysicsInterface_SIM {

	@Override
	public Result attempt(Event event, Space space) {
		return null;
	}

	@Override
	public boolean isPossible(RecordAction action, Space context) {
		return true; // TODO may not be possible?
	}

	@Override
	public boolean isNecessary(RecordAction action, Space context) {
		return true;
	}

	@Override
	public Result perform(RecordAction action, Space context) {
		return null; // TODO
	}

	@Override
	public boolean succeeded(RecordAction action, Space context) {
		return true; // TODO
	}

	@Override
	public boolean isPossible(ReportAction action, Space context) {
		return true;
	}

	@Override
	public boolean isNecessary(ReportAction action, Space context) {
		return true;
	}

	@Override
	public Result perform(ReportAction action, Space context) {
		// TODO
		return null;
	}

	@Override
	public boolean succeeded(ReportAction action, Space context) {
		return true; //TODO
	}
}
