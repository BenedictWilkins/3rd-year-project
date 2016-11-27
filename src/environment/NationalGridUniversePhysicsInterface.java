package environment;

import agent.actions.TakeReadingAction;
import agent.actions.CommunicationAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * An implementation of {@link Physics} that defines the {@link Action} methods
 * for this project.
 * 
 * @author Benedict Wilkins
 *
 */
public interface NationalGridUniversePhysicsInterface extends Physics {
	// RecordAction methods
	public boolean isPossible(TakeReadingAction action, Space context);

	public boolean isNecessary(TakeReadingAction action, Space context);

	public Result perform(TakeReadingAction action, Space context);

	public boolean succeeded(TakeReadingAction action, Space context);

	// ReportAction methods
	public boolean isPossible(CommunicationAction action, Space context);

	public boolean isNecessary(CommunicationAction action, Space context);

	public Result perform(CommunicationAction action, Space context);

	public boolean succeeded(CommunicationAction action, Space context);
}
