package environment.communication.module;

import java.io.IOException;
import java.net.Socket;

/**
 * The client state of a {@link CommunicationModule}. <br>
 * Extends {@link CommunicationModuleState}.
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationModuleStateClient extends CommunicationModuleState {

  private String hostName = null;
  private Integer port = null;
  private Socket socket = null;

  /**
   * Constructor.
   * 
   * @param hostName
   *          to connect to
   * @param port
   *          to connect to
   * @param mode
   *          of connection
   */
  public CommunicationModuleStateClient(String hostName, Integer port,
      Class<? extends CommunicationMode> mode) {
    super(mode);
    this.port = port;
    this.hostName = hostName;
  }

  @Override
  protected ConnectionStatusClient connectionStatus() {
    return new ConnectionStatusClient(socket);
  }

  @Override
  public void start() {
    try {
      System.out.println("Running Communication module as client...");
      System.out.println("Attempting to connect to " + hostName + " on port: "
          + port);
      socket = new Socket(hostName, port);
      System.out.println("Client Connected to: "
          + socket.getRemoteSocketAddress());
      super.run(socket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
