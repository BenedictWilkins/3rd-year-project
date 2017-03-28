package agent.communication;

import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;

/**
 * A {@link MessagePayload} whose payload is a byte array. This class will be
 * used to send {@link Object}s that have been serialised by {@link ObjectOutputStream}
 * using {@link ByteArrayInputStream}.
 * 
 * @author Benedict Wilkins
 *
 */
public class ByteArrayPayload implements MessagePayload<byte[]> {

  private static final long serialVersionUID = 1L;

  private byte[] payload;

  /**
   * Constructor.
   * 
   * @param bytes
   *          array of bytes that is this {@link MessagePayload}s payload
   */
  public ByteArrayPayload(byte[] bytes) {
    setPayload(bytes);
  }

  @Override
  public byte[] getPayload() {
    return payload;
  }

  @Override
  public void setPayload(byte[] payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
