package environment.communication;

import environment.communication.module.CommunicationModule;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import agent.actions.SendAction;

public class CommunicationPhysics implements Physics, CommunicationPhysicsInterface {
	
	
	@Override
	public Result attempt(Event event, Space context) {
		return null;
	}

	@Override
	public boolean isPossible(SendAction action, Space context) {
		return false;
				//((CommunicationSpace)context).isConnected();
	}

	@Override
	public boolean isNecessary(SendAction action, Space context) {
		return true;
	}

	@Override
	public Result perform(SendAction action, Space context) {
		CommunicationModule module = ((CommunicationSpace)context).getModule();
		System.out.println("performing send action");
		return null;
	}

	@Override
	public boolean succeeded(SendAction action, Space context) {
		return true;
	}

}
