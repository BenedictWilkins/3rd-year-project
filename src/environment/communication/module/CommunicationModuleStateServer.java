package environment.communication.module;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * The client state of a {@link CommunicationModule}. <br>
 * Extends {@link CommunicationModuleState}.
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationModuleStateServer extends CommunicationModuleState {

  private Integer port = null;
  private ServerSocket server = null;
  private Set<Socket> sockets = new HashSet<Socket>();

  private Integer maxConnections = null;
  private boolean waitingOnConnections;
  private String address = null;

  /**
   * Constructor.
   * 
   * @param port
   *          to connect to
   * @param maxConnections
   *          the server can have
   * @param mode
   *          of connection
   */
  public CommunicationModuleStateServer(Integer port, Integer maxConnections,
      Class<? extends CommunicationMode> mode) {
    super(mode);
    this.port = port;
    this.maxConnections = maxConnections;
  }

  @Override
  public void start() {
    ServerDaemon sd = new ServerDaemon();
    sd.setDaemon(true);
    sd.start();
  }

  private void activateSocket(Socket socket) {
    super.run(socket);
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
          Socket socket = server.accept();
          sockets.add(socket);
          System.out.println("Server Connected to: "
              + socket.getRemoteSocketAddress());
          activateSocket(socket);
        }
        System.out.println("MAXIMUM CONNECTIONS REACHED");
        postProcessing();
      } catch (IOException e) {
        e.printStackTrace();
      }
      waitingOnConnections = false;
    }
  }

  private void postProcessing() {
    System.out.println("POST PROCESSING");
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("DONE");
  }

  @Override
  protected ConnectionStatusServer connectionStatus() {
    return new ConnectionStatusServer(address, sockets, waitingOnConnections,
        maxConnections);
  }
}
