package agent.communication;

/**
 * A concrete implementation of {@link AbstractMessagePayload} whos payload is
 * of type {@link NetworkObject}.
 * 
 * @author Benedict Wilkins
 *
 */
public class NetworkObjectPayload extends AbstractMessagePayload<NetworkObject> {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param networkObject
   *          payload
   */
  public NetworkObjectPayload(NetworkObject networkObject) {
    super(networkObject);
  }
}
