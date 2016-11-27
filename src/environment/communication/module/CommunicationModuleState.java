package environment.communication.module;

import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CommunicationModuleState {

  private Map<Class<? extends CommunicationMode>, Set<CommunicationMode>> classReference;
  private Class<? extends CommunicationMode> mode;
  private Set<CommunicationMode> receivers = null;
  private Set<CommunicationMode> senders = null;

  protected CommunicationModuleState(Class<? extends CommunicationMode> mode) {
    this.mode = mode;
    receivers = new HashSet<>();
    senders = new HashSet<>();
    classReference = new HashMap<>();
    classReference.put(CommunicationModeReceiver.class, receivers);
    classReference.put(CommunicationModeSender.class, senders);
  }

  protected Class<? extends CommunicationMode> getMode() {
    return this.mode;
  }

  public void run(Socket socket) {
    if (mode == null) {
      System.out.println("Set up as: CommunicationSender/ReceiverMode");
      doMultiMode();
    } else {
      System.out.println("Set up as: " + mode.getSimpleName());
      doMode(socket, mode);
    }
  }

  public abstract void start();

  private void doMultiMode() {

  }

  private void doMode(Socket socket, Class<? extends CommunicationMode> modeClass) {
    try {
      Constructor<? extends CommunicationMode> con = modeClass
          .getConstructor(Socket.class);
      CommunicationMode mode = con.newInstance(socket);
      classReference.get(modeClass).add(mode);
      mode.setDaemon(false);
      mode.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected abstract ConnectionStatus connectionStatus();
}
