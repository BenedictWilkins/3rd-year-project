package agent.communication;

/**
 * Abstract class that implements {@link MessagePayload}. Stores the payload.
 * 
 * @author Benedict Wilkins
 *
 * @param <T>
 *          type of the payload
 */
public abstract class AbstractMessagePayload<T> implements MessagePayload<T> {

  private static final long serialVersionUID = 1L;
  // payload of the message
  protected T payload;

  /**
   * Constructor.
   * 
   * @param payload
   *          of the message
   */
  public AbstractMessagePayload(T payload) {
    super();
    this.payload = payload;
  }

  @Override
  public T getPayload() {
    return payload;
  }

  @Override
  public void setPayload(T payload) {
    this.payload = payload;
  }

}
