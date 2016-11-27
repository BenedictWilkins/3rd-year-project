package environment.communication.module;

public abstract class ConnectionStatus {

  protected static String CONNECTIONSTATUS = "ConnectionStatus"
      + System.lineSeparator();
  protected static String[] LABELS = { " Type: ", " Address: " };
  private String address = null;
  private String type = null;

  public ConnectionStatus(String address, String type) {
    this.address = address;
    this.type = type;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(CONNECTIONSTATUS);
    builder.append(LABELS[0] + type);
    builder.append(LABELS[1] + address);
    return builder.toString();
  }
}
