package environment.communication;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import agent.actions.SendAction;

public interface CommunicationPhysicsInterface {

	// SendAction methods
	public boolean isPossible(SendAction action, Space context);
	public boolean isNecessary(SendAction action, Space context);
	public Result perform(SendAction action, Space context);
	public boolean succeeded(SendAction action, Space context);
}
