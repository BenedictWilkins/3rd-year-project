package environment.communication.module;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class CommunicationModuleStateServer extends CommunicationModuleState {

	private Integer port = null;
	private ServerSocket server = null;
	private Set<Socket> sockets = new HashSet<Socket>();

	private Integer maxConnections = null;
	private boolean waitingOnConnections;
	private String address = null;

	public CommunicationModuleStateServer(Integer port, Integer maxConnections) {
		this.port = port;
		this.maxConnections = maxConnections;
	}

	@Override
	public void run() {
		ServerDaemon sd = new ServerDaemon();
		sd.setDaemon(true);
		sd.start();
	}

	@Override
	public void taredown() {
	}

	private class ServerDaemon extends Thread {
		@Override
		public void run() {
			try {
				waitingOnConnections = true;
				System.out.println("Running Communication module as server...");
				server = new ServerSocket(port);
				address = server.getInetAddress().toString() + ":"
						+ server.getLocalPort();
				for (int i = 0; i < maxConnections; i++) {
					System.out.println("Waiting on port: " + port);
					Socket s = server.accept();
					sockets.add(s);
					System.out.println("Connected to: "
							+ s.getRemoteSocketAddress());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			waitingOnConnections = false;
		}
	}

	@Override
	protected ConnectionStatusServer connectionStatus() {
		return new ConnectionStatusServer(address, sockets,
				waitingOnConnections, maxConnections);
	}

}
