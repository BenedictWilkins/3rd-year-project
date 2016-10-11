package demo;

import java.util.ArrayList;
import java.util.List;

import environment.House_SIM;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import agent.SmartMeterAgentActuator_SIM;
import agent.SmartMeterAgentBody_SIM;
import agent.SmartMeterAgentBrain_SIM;
import agent.SmartMeterAgentMind_SIM;
import agent.SmartMeterAgentSensor_SIM;

public class Simulator {
	
	public Simulator(int numberOfHouses) {
		
	}
	
	public House_SIM createHouse() {
		return new House_SIM();
	}
	
	public SmartMeterAgentBody_SIM createSmartMeterAgent() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(new SmartMeterAgentSensor_SIM());
		List<Actuator> actuators = new ArrayList<Actuator>();
		actuators.add(new SmartMeterAgentActuator_SIM());
		return new SmartMeterAgentBody_SIM(sensors,
				actuators, new SmartMeterAgentMind_SIM(),
				new SmartMeterAgentBrain_SIM());

	}
}
