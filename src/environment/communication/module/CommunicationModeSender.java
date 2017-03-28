package environment.communication.module;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A {@link CommunicationMode} representing a sender. A sender may only send
 * messages.
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationModeSender extends CommunicationMode {

  private Socket socket = null;
  private ObjectOutputStream out = null;

  public CommunicationModeSender(Socket socket) throws IOException {
    this.socket = socket;
    out = new ObjectOutputStream(socket.getOutputStream());
  }

  @Override
  public void run() {
    try {
      System.out.println("start sender:\n" + socket.getLocalAddress() + ":"
          + socket.getLocalPort() + "->" + socket.getInetAddress() + ":"
          + socket.getPort());
      for (int i = 0; i < 3; i++) {
        Thread.sleep(100);
        out.writeObject("TEST MESSAGE");
      }
      System.out.println("FINISHED SENDING MESSAGES!");
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
