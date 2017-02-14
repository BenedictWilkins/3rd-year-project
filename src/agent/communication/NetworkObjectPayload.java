package agent.communication;

public class NetworkObjectPayload extends AbstractMessagePayload<NetworkObject> {

  private static final long serialVersionUID = 1L;

  public NetworkObjectPayload(NetworkObject networkObject) {
    super(networkObject);
  }
}
