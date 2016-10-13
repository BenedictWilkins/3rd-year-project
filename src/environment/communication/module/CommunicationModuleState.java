package environment.communication.module;

public abstract class CommunicationModuleState extends Thread {

	protected abstract void taredown();
	protected abstract ConnectionStatus connectionStatus();
}
