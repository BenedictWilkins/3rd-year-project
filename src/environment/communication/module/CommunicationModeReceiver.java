package environment.communication.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationModeReceiver extends CommunicationMode {

  private Socket socket = null;
  private ObjectInputStream in = null;

  public CommunicationModeReceiver(Socket socket) throws IOException {
    this.socket = socket;
    this.in = new ObjectInputStream(socket.getInputStream());
  }

  @Override
  public void run() {
    try {
      System.out.println("start receiver:\n" + socket.getLocalAddress() + ":"
          + socket.getLocalPort() + "->" + socket.getInetAddress() + ":"
          + socket.getPort());
      while (true) {
        Object obj = in.readObject();
        System.out.println(obj);
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
