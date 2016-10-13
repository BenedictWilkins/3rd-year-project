package environment.communication.module;


public abstract class ConnectionStatus {

	protected static String CONNECTIONSTATUS = "ConnectionStatus"+System.lineSeparator();
	protected static String[] LABELS = {" Type: ", " Address: "};
	private String address = null;
	private String type = null;
	
	public ConnectionStatus(String address, String type) {
		this.address = address;
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(CONNECTIONSTATUS);
		b.append(LABELS[0] + type);
		b.append(LABELS[1] + address);
		return b.toString();
	}
}
