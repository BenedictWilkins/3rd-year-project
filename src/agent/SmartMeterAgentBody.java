package agent;

import java.util.Iterator;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import agent.actions.HouseEvent;
import agent.actions.IPCommunicationAction;

/**
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentBody extends AbstractAgent {

	private BrainHandler brainHandler;

	public SmartMeterAgentBody(List<Sensor> sensors, List<Actuator> actuators,
			SmartMeterAgentMind mind, SmartMeterAgentBrain brain) {
		super(null, sensors, actuators, mind, brain);
		if(getIPCommunicationActuator() == null) {
			brainHandler = new NormalBrainHandler();
		} else {
			brainHandler = new IPBrainHandler();
		}
	}

	@Override
	public Object simulate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(CustomObservable o, Object arg) {
		if (o instanceof SmartMeterAgentBrain) {
			handleBrainMessage(arg);
		} else if (o instanceof SmartMeterAgentSensor) {
			handleSensorMessage(arg);
		}
	}

	private Object getComponent(Class<?> c, List<?> l) {
		Iterator<?> iter = l.iterator();
		while (iter.hasNext()) {
			Object a = iter.next();
			if (c.isInstance(a)) {
				return a;
			}
		}
		return null;
	}

	public Sensor getSensor(Class<? extends Sensor> c) {
		return (Sensor) getComponent(c, getSensors());
	}

	public Actuator getActuator(Class<? extends Actuator> c) {
		return (Actuator) getComponent(c, getActuators());
	}

	public SmartMeterAgentActuator getSmartMeterActuator() {
		return (SmartMeterAgentActuator) getActuator(SmartMeterAgentActuator.class);
	}

	public IPCommunicationActuator getIPCommunicationActuator() {
		return (IPCommunicationActuator) getActuator(IPCommunicationActuator.class);
	}

	public SmartMeterAgentSensor getSmartMeterSensor() {
		return (SmartMeterAgentSensor) getSensor(SmartMeterAgentSensor.class);
	}

	public IPCommunicationSensor getIPCommunicationSensor() {
		return (IPCommunicationSensor) getSensor(IPCommunicationSensor.class);
	}

	private void handleBrainMessage(Object arg) {
		// create an event and give it to the actuator
		brainHandler.handle(arg, this);
	}

	private void handleSensorMessage(Object arg) {
		notifyObservers(arg, SmartMeterAgentBrain.class);
	}

	private interface BrainHandler {
		void handle(Object arg, SmartMeterAgentBody agent);
	}

	private class NormalBrainHandler implements BrainHandler {
		@Override
		public void handle(Object arg, SmartMeterAgentBody agent) {
			AbstractAction a = (AbstractAction) arg;
			HouseEvent e = new HouseEvent(a, System.currentTimeMillis(), agent);
			SmartMeterAgentBody.this.notifyObservers(e, SmartMeterAgentActuator.class);
		}
	}

	private class IPBrainHandler extends NormalBrainHandler {
		@Override
		public void handle(Object arg, SmartMeterAgentBody agent) {
			//create an event and give it to the actuator
			if(arg instanceof IPCommunicationAction) {
				SmartMeterAgentBody.this.notifyObservers(arg, IPCommunicationActuator.class);
			} else {
				super.handle(arg, agent);
			}
		}
	}
}
