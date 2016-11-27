package environment.communication.module;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ConnectionStatusServer extends ConnectionStatus {

  private static final String TYPE = "SERVER";
  private static final String[] SERVERLABELS = { " WaitingOnConnections: ",
      " MaxConnections: ", System.lineSeparator() + " Connections: " };

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
      public void accept(Socket socket) {
        connections.add(socket.getRemoteSocketAddress().toString());
      }
    });
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(super.toString());
    builder.append(SERVERLABELS[0] + waitingOnConnections);
    builder.append(SERVERLABELS[1] + maxConnections);
    builder.append(SERVERLABELS[2] + connections.size() + ":");
    connections.forEach(new Consumer<String>() {
      @Override
      public void accept(String arg) {
        builder.append(System.lineSeparator() + arg);
      }
    });
    return builder.toString();
  }
}
