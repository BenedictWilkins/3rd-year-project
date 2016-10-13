package environment.communication.module;

import java.io.IOException;
import java.net.Socket;

public class CommunicationModuleStateClient extends CommunicationModuleState {

	private Socket socket = null;
	private String hostName = null;
	private Integer port = null;

	public CommunicationModuleStateClient(String hostName, Integer port) {
		this.port = port;
		this.hostName = hostName;
	}

	@Override
	public void run() {
		try {
			System.out.println("Running Communication module as client...");
			System.out.println("Attempting to connect to " + hostName
					+ " on port: " + port);
			socket = new Socket(hostName, port);
			System.out.println("Connected to: "
					+ socket.getRemoteSocketAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void taredown() {
		// TODO Auto-generated method stub
	}

	@Override
	protected ConnectionStatusClient connectionStatus() {
		return new ConnectionStatusClient(socket);
	}
}
