package agent.general;

import agent.CommunicationActuator;
import agent.CommunicationSensor;
import agent.IpCommunicationActuator;
import agent.IpCommunicationSensor;
import agent.SmartMeterAgentBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

import java.util.Iterator;
import java.util.List;

/**
 * An abstract class extending {@link AbstractAgent}. The need for the 'General'
 * abstract classes is because of the {@link CustomObservable}/
 * {@link CustomObserver} classes. They must know which class should be notified
 * of a message. All parts of agents must extends the 'General' agent parts if
 * the implemented method of message passing is to work properly. This will
 * likely be re-factored in the future, however the GAWL library forced this
 * work around for now.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class GeneralAgentBody extends AbstractAgent {

  // class of the brain this body is linked to
  private Class<?> brainclass;

  /**
   * Constructor.
   * 
   * @param appearance
   *          the {@link Appearance} of this agent
   * @param sensors
   *          A {@link List} of {@link Sensor}s that are to be used by this
   *          agent
   * @param actuators
   *          A {@link List} of {@link Actuator}s that are to be used by this
   *          agent
   * @param mind
   *          the {@link Mind} of this agent
   * @param brain
   *          the {@link Brain} of this agent
   */
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
