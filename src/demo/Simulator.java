package demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import threading.AgentRunnable;
import threading.AgentThreadManager;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import agent.IPCommunicationActuator;
import agent.IPCommunicationSensor;
import agent.SmartMeterAgentActuator;
import agent.SmartMeterAgentBody;
import agent.SmartMeterAgentBrain;
import agent.SmartMeterAgentMind;
import agent.SmartMeterAgentSensor;
import agent.actions.RecordAction;
import environment.HouseEnvironment;
import environment.HouseEnvironmentAppearance;
import environment.HouseEnvironmentSpace;
import environment.HousePhysics;
import environment.NationalGridUniversePhysics;
import environment.NationalGridUniverse;
import environment.NationalGridUniverseSpace;

public class Simulator {

	public static final Set<Class<? extends AbstractAction>> HOUSEACTIONS = new HashSet<>();
	public static final Set<Class<? extends AbstractAction>> UNIVERSEACTIONS = new HashSet<>();
	private int totalHouses = 0;
	private static final int SERVERPORT = 8888;
	private static final String SERVERNAME = "localhost";
	private static final Boolean IPCommunication = false;

	public Set<HouseEnvironment> houses = new HashSet<>();

	public Simulator(int numberOfHouses) {
		HOUSEACTIONS.add(RecordAction.class);
		AgentThreadManager manager = new AgentThreadManager();
		NationalGridUniverse universe = createNationalGridUniverse(numberOfHouses);
		for(HouseEnvironment h : universe.getHouseSubEnvironments()) {
			manager.addAgent(new AgentRunnable(h.getSmartMeterAgent().getMind()));
		}
		manager.start();
	}

	public NationalGridUniverse createNationalGridUniverse(int numberOfHouses) {
		List<HouseEnvironment> houses = new ArrayList<HouseEnvironment>();
		for(int i = 0; i < numberOfHouses; i++) {
			houses.add(doHouseEnvironment());
		}
		NationalGridUniverse u = new NationalGridUniverse(new NationalGridUniverseSpace(),
				UNIVERSEACTIONS, new HashSet<Body>(),
				new NationalGridUniversePhysics(), houses);
		return u;
	}

	public HouseEnvironment doHouseEnvironment() {
		HouseEnvironment h = createHouse();
		SmartMeterAgentBody a = h.getSmartMeterAgent();
		((SmartMeterAgentActuator) a.getSmartMeterActuator())
				.addObserver(h);
		h.addObserver(a.getSensors().get(0));
		return h;
	}

	private HouseEnvironment createHouse() {
		Set<Body> agent = new HashSet<>();
		agent.add(createSmartMeterAgent());
		return new HouseEnvironment(new HouseEnvironmentSpace(HouseModel), HOUSEACTIONS,
				agent, new HousePhysics(), true,
				new HouseEnvironmentAppearance(++totalHouses));
	}

	private SmartMeterAgentBody createSmartMeterAgent() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(new SmartMeterAgentSensor());
		List<Actuator> actuators = new ArrayList<Actuator>();
		actuators.add(new SmartMeterAgentActuator());
		if (IPCommunication) {
			sensors.add(new IPCommunicationSensor(SERVERNAME, SERVERPORT));
			actuators.add(new IPCommunicationActuator(SERVERNAME, SERVERPORT));
		}
		return new SmartMeterAgentBody(sensors, actuators,
				new SmartMeterAgentMind(HOUSEACTIONS),
				new SmartMeterAgentBrain());
	}
}
