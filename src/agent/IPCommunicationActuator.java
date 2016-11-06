package agent;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import environment.communication.module.CommunicationModeSender;
import environment.communication.module.CommunicationModule;

public class IPCommunicationActuator extends AbstractActuator {

	private CommunicationModule module;
	
	/**
	 * Constructor.
	 * @param hostName address to connect to
	 * @param port to to connect to
	 */
	public IPCommunicationActuator(String hostName, Integer port) {
		module = new CommunicationModule(hostName, port, CommunicationModeSender.class);
	}
	
	@Override
	public void update(CustomObservable o, Object arg) {
		if(o instanceof SmartMeterAgentBody) {
			handleBodyMessage(arg);
		}
	}
	
	private void handleBodyMessage(Object arg) {
		System.out.println("DO SOME COMMUNICATION");
		module.start();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
