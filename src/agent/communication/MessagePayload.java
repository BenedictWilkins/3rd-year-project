package agent.communication;

import java.io.Serializable;

/**
 * A class that contains data that will be sent over network or through
 * simulation should implement this interface. <br>
 * See: {@link ByteArrayPayload}.
 * 
 * @author Benedict Wilkins
 *
 * @param <T>
 *          type of the payload that will be sent
 */
public interface MessagePayload<T> extends Serializable {

  /**
   * Getter for the payload held in this {@link MessagePayload}.
   * 
   * @return the payload
   */
  public T getPayload();

  /**
   * Setter for the payload held in this {@link MessagePayload}.
   * 
   * @param payload
   *          to set
   */
  public void setPayload(T payload);

}
