package demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import agent.SmartMeterAgentActuator_SIM;
import agent.SmartMeterAgentBody_SIM;
import agent.SmartMeterAgentBrain_SIM;
import agent.SmartMeterAgentMind_SIM;
import agent.SmartMeterAgentSensor_SIM;
import agent.actions.RecordAction;
import environment.HouseAppearance_SIM;
import environment.HousePhysics_SIM;
import environment.HouseSpace_SIM;
import environment.HouseEnvironment_SIM;
import environment.communication.module.CommunicationModule;

public class Simulator {

	public static final Set<Class<? extends AbstractAction>> HOUSEACTIONS = new HashSet<>();
	private int totalHouses = 0;

	public Set<HouseEnvironment_SIM> houses = new HashSet<>();

	public Simulator(int numberOfHouses) {
		HOUSEACTIONS.add(RecordAction.class);

		Set<Body> agent = new HashSet<>();
		agent.add(createSmartMeterAgent());
		CommunicationModule module = new CommunicationModule(8888,
				numberOfHouses);
		HouseEnvironment_SIM house = new HouseEnvironment_SIM(
				new HouseSpace_SIM(module), HOUSEACTIONS, agent,
				new HousePhysics_SIM(), true, new HouseAppearance_SIM(
						++totalHouses));
		house.start();

		for (int i = 0; i < numberOfHouses; i++) {
			HouseEnvironment_SIM h = createHouse();
			houses.add(h);
			h.start();
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		houses.forEach(new Consumer<HouseEnvironment_SIM>() {
			@Override
			public void accept(HouseEnvironment_SIM h) {
				System.out.println(h.getState().getModule().connectionStatus());
			}
		});
		System.out.println(house.getState().getModule().connectionStatus());
	}

	public HouseEnvironment_SIM createHouse() {
		Set<Body> agent = new HashSet<>();
		agent.add(createSmartMeterAgent());
		CommunicationModule module = new CommunicationModule("localhost", 8888);
		return new HouseEnvironment_SIM(new HouseSpace_SIM(module),
				HOUSEACTIONS, agent, new HousePhysics_SIM(), true,
				new HouseAppearance_SIM(++totalHouses));
	}

	public SmartMeterAgentBody_SIM createSmartMeterAgent() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(new SmartMeterAgentSensor_SIM());
		List<Actuator> actuators = new ArrayList<Actuator>();
		actuators.add(new SmartMeterAgentActuator_SIM());
		return new SmartMeterAgentBody_SIM(sensors, actuators,
				new SmartMeterAgentMind_SIM(), new SmartMeterAgentBrain_SIM());
	}
}
