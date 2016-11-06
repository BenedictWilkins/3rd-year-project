package agent;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import environment.communication.module.CommunicationModeReceiver;
import environment.communication.module.CommunicationModule;

public class IPCommunicationSensor extends AbstractSensor {
	
private CommunicationModule module;
	
	/**
	 * Constructor.
	 * @param hostName address to connect to
	 * @param port to to connect to
	 */
	public IPCommunicationSensor(String hostName, Integer port) {
		module = new CommunicationModule(hostName, port, CommunicationModeReceiver.class);
	}
	
	public void listen() {
		module.start();
	}
	
	@Override
	public void update(CustomObservable o, Object arg) {
		//
	}
	
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
