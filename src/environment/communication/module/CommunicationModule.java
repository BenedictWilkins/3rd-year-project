package environment.communication.module;

public class CommunicationModule {

  private CommunicationModuleState state = null;
  private boolean isServer;

  /**
   * Client constructor.
   * 
   * @param hostname
   *          to connect to
   * @param port
   *          to connect to
   */
  public CommunicationModule(String hostName, Integer port,
      Class<? extends CommunicationMode> mode) {
    state = new CommunicationModuleStateClient(hostName, port, mode);
    this.isServer = false;
  }

  /**
   * Server constructor.
   * 
   * @param port
   *          to connect to
   * @param maxConnections
   *          that the server can make
   */
  public CommunicationModule(Integer port, Integer maxConnections,
      Class<? extends CommunicationMode> mode) {
    state = new CommunicationModuleStateServer(port, maxConnections, mode);
    this.isServer = true;
  }

  public ConnectionStatus connectionStatus() {
    return state.connectionStatus();
  }

  /**
   * Starts the communication module. If the module is a server it will start
   * listening for connections. If it is a client it will try to connect to the
   * given host
   */
  public void start() {
    state.start();
  }

  public boolean isServer() {
    return isServer;
  }
}
