package agent.general;

import agent.CommunicationActuator;
import agent.CommunicationSensor;
import agent.IpCommunicationActuator;
import agent.IpCommunicationSensor;
import agent.SmartMeterAgentBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

import java.util.Iterator;
import java.util.List;

public abstract class GeneralAgentBody extends AbstractAgent {

  private Class<?> brainclass;

  public GeneralAgentBody(AbstractAgentAppearance appearance,
      List<Sensor> sensors, List<Actuator> actuators, GeneralAgentMind mind,
      GeneralAgentBrain brain) {
    super(appearance, sensors, actuators, mind, brain);
    this.brainclass = brain.getClass();
    mind.setBody(this);
  }

  public final Class<?> getBrainclass() {
    return brainclass;
  }
  

  /**
   * Gets a specific component of the agent from the given list of components.
   * 
   * @param type
   *          class of the component to find
   * @param components
   *          to search
   * @return the component if found, null if not
   */
  private Object getComponent(Class<?> type, List<?> components) {
    Iterator<?> iter = components.iterator();
    while (iter.hasNext()) {
      Object obj = iter.next();
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }

  /**
   * Getter for a {@link Sensor}. See
   * {@link SmartMeterAgentBody#getComponent(Class, List)}
   * 
   * @param sensorClass
   *          the runtime class of the {@link Sensor}
   * @return the {@link Sensor} if found, null if not
   */
  public Sensor getSensor(Class<? extends Sensor> sensorClass) {
    return (Sensor) getComponent(sensorClass, getSensors());
  }

  /**
   * Getter for a {@link Actuator}. See
   * {@link SmartMeterAgentBody#getComponent(Class, List)}
   * 
   * @param actuatorClass
   *          the runtime class of the {@link Actuator}
   * @return the {@link Actuator} if found, null if not
   */
  public Actuator getActuator(Class<? extends Actuator> actuatorClass) {
    return (Actuator) getComponent(actuatorClass, getActuators());
  }

  /**
   * Getter for a {@link CommunicationActuator}. See
   * {@link SmartMeterAgentBody#getActuator(Class)}
   * 
   * @return the {@link CommunicationActuator} if found, null if not
   */
  @SuppressWarnings("unchecked")
  public CommunicationActuator getSmartMeterActuator() {
    return (CommunicationActuator) getActuator(CommunicationActuator.class);
  }

  /**
   * Getter for a {@link IpCommunicationActuator}. See
   * {@link SmartMeterAgentBody#getActuator(Class)}
   * 
   * @return the {@link IpCommunicationActuator} if found, null if not
   */
  public IpCommunicationActuator getIpCommunicationActuator() {
    return (IpCommunicationActuator) getActuator(IpCommunicationActuator.class);
  }

  /**
   * Getter for a {@link CommunicationSensor}. See
   * {@link SmartMeterAgentBody#getSensor(Class)}
   * 
   * @return the {@link CommunicationSensor} if found, null if not
   */
  @SuppressWarnings("unchecked")
  public CommunicationSensor getSmartMeterSensor() {
    return (CommunicationSensor) getSensor(CommunicationSensor.class);
  }

  /**
   * Getter for a {@link IpCommunicationSensor}. See
   * {@link SmartMeterAgentBody#getSensor(Class)}
   * 
   * @return the {@link IpCommunicationSensor} if found, null if not
   */
  public IpCommunicationSensor getIpCommunicationSensor() {
    return (IpCommunicationSensor) getSensor(IpCommunicationSensor.class);
  }
  
  @Override
  public String toString() {
    return this.getId().toString();
  }
}
