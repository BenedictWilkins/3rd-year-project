package agent.communication;

public class AbstractMessagePayload<T> implements MessagePayload<T> {

  private static final long serialVersionUID = 1L;
  protected T payload;

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
