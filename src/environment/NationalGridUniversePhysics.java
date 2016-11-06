package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import agent.actions.RecordAction;
import agent.actions.ReportAction;

public class NationalGridUniversePhysics implements
		NationalGridUniversePhysicsInterface {

	@Override
	public Result attempt(Event event, Space space) {
		System.out.println("PHYSICS ATTEMPTING EVENT: " + event.getAction());
		return event.getAction().attempt(this, space);
	}

	// *********** REPORT ACTION METHODS *********** //
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
		System.out.println("FORWARD THE REPORT!");
		return new DefaultActionResult(ActionResult.ACTION_DONE, null);
	}

	@Override
	public boolean succeeded(ReportAction action, Space context) {
		return true;
	}

	// *********** EVENT UNSUPPORTED BY THIS PHYSICS *********** //
	// *********** RECORD ACTION METHODS *********** //
	@Override
	public boolean isPossible(RecordAction action, Space context) {
		return false;
	}

	@Override
	public boolean isNecessary(RecordAction action, Space context) {
		return false;
	}

	@Override
	public Result perform(RecordAction action, Space context) {
		return new DefaultActionResult(ActionResult.ACTION_IMPOSSIBLE, null);
	}

	@Override
	public boolean succeeded(RecordAction action, Space context) {
		return false;
	}
}
