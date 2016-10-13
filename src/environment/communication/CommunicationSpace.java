package environment.communication;

import environment.communication.module.CommunicationModule;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;

public class CommunicationSpace implements Space {
	
	private CommunicationModule module;
	
	public CommunicationSpace(CommunicationModule module) {
		this.setModule(module);
	}

	public CommunicationModule getModule() {
		return module;
	}

	public void setModule(CommunicationModule module) {
		this.module = module;
	}
}
