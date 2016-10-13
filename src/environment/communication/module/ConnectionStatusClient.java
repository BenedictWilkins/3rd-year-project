package environment.communication.module;

import java.net.Socket;

public class ConnectionStatusClient extends ConnectionStatus {

	private static final String TYPE = "CLIENT";
	private static final String[] CLIENTLABELS = { " Connected: ",
			System.lineSeparator() + " Connection: " };

	private boolean connected;
	private String connection;

	public ConnectionStatusClient(Socket socket) {
		super(socket.getInetAddress() + ":" + socket.getLocalPort(), TYPE);
		this.connected = socket.isConnected();
		this.connection = socket.getRemoteSocketAddress().toString();
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(super.toString());
		b.append(CLIENTLABELS[0] + connected);
		b.append(CLIENTLABELS[1] + connection);
		return b.toString();
	}
}
