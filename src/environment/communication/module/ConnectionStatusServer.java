package environment.communication.module;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ConnectionStatusServer extends ConnectionStatus {

	private final static String TYPE = "SERVER";
	private final static String[] SERVERLABELS = {" WaitingOnConnections: ", " MaxConnections: ",
			System.lineSeparator() + " Connections: " };

	private Set<String> connections = new HashSet<String>();
	private boolean waitingOnConnections;
	private Integer maxConnections = null;

	public ConnectionStatusServer(String address, Set<Socket> sockets,
			boolean waitingOnConnections, Integer maxConnections) {
		super(address, TYPE);
		this.waitingOnConnections = waitingOnConnections;
		this.maxConnections = maxConnections;
		sockets.forEach(new Consumer<Socket>() {
			@Override
			public void accept(Socket s) {
				connections.add(s.getRemoteSocketAddress().toString());
			}
		});
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(super.toString());
		b.append(SERVERLABELS[0] + waitingOnConnections);
		b.append(SERVERLABELS[1] + maxConnections);
		b.append(SERVERLABELS[2] + connections.size() + ":");
		connections.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				b.append(System.lineSeparator() + t);
			}
		});
		return b.toString();
	}
}
