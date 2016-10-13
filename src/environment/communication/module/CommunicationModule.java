package environment.communication.module;


public class CommunicationModule {

	private CommunicationModuleState state = null;
	private boolean isServer;
	
	/**
	 * Client constructor.
	 * 
	 * @param hostname
	 * @param port
	 */
	public CommunicationModule(String hostName, Integer port) {
		state = new CommunicationModuleStateClient(hostName, port);
		this.isServer = false;
	}

	/**
	 * Server constructor.
	 * @param port
	 * @param maxConnections
	 */
	public CommunicationModule(Integer port, Integer maxConnections) {
		state = new CommunicationModuleStateServer(port, maxConnections);
		this.isServer = true;
	}
	
	public ConnectionStatus connectionStatus() {
		return state.connectionStatus();
	}

	/**
	 * Starts the communication module. If the module is a server it will start
	 * listening for connections. If it is a client it will try to connect to
	 * the given host
	 */
	public void start() {
		state.start();
	}

	public boolean isServer() {
		return isServer;
	}
}
