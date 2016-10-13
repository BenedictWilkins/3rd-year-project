package environment;

import agent.actions.RecordAction;
import agent.actions.ReportAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;

public interface HousePhysicsInterface_SIM {
	
	// RecordAction methods
	public boolean isPossible(RecordAction action, Space context);
	public boolean isNecessary(RecordAction action, Space context);
	public Result perform(RecordAction action, Space context);
	public boolean succeeded(RecordAction action, Space context);
	// ReportAction methods
	public boolean isPossible(ReportAction action, Space context);
	public boolean isNecessary(ReportAction action, Space context);
	public Result perform(ReportAction action, Space context);
	public boolean succeeded(ReportAction action, Space context);
}
