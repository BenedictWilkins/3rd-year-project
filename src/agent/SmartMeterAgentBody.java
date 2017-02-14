package agent;

import agent.actions.CommunicationAction;
import agent.actions.HouseEvent;
import agent.actions.IpCommunicationAction;
import agent.general.GeneralAgentBody;
import environment.HouseEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.Iterator;
import java.util.List;

/**
 * The {@link Body} implementation for a Smart Meter Agent. <br>
 * Extends: {@link GeneralAgentBody}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentBody extends GeneralAgentBody<SmartMeterAgentBrain> {

  // changes depending what actuator/sensors are in use
  private BrainHandler brainHandler;

  /**
   * Constructor. See:
   * {@link AbstractAgent#AbstractAgent(AbstractAgentAppearance, List, List, AbstractAgentMind, AbstractAgentBrain)}
   * .
   */
  public SmartMeterAgentBody(List<Sensor> sensors, List<Actuator> actuators,
      SmartMeterAgentMind mind, SmartMeterAgentBrain brain) {
    super(null, sensors, actuators, mind, brain, SmartMeterAgentBrain.class);
    if (getIpCommunicationActuator() == null) {
      brainHandler = new NormalBrainHandler();
    } else {
      brainHandler = new IpBrainHandler();
    }
  }

  /**
   * Unused.
   */
  @Override
  public Object simulate() {
    return null;
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (this.getBrainclass().isAssignableFrom(observable.getClass())) {
      handleBrainMessage(arg);
    } else if (CommunicationSensor.class
        .isAssignableFrom(observable.getClass())) {
      handleSensorMessage(arg);
    }
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
  public CommunicationActuator<SmartMeterAgentBody, HouseEnvironment> getSmartMeterActuator() {
    return (CommunicationActuator<SmartMeterAgentBody, HouseEnvironment>) getActuator(CommunicationActuator.class);
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
  public CommunicationSensor<SmartMeterAgentBody, HouseEnvironment> getSmartMeterSensor() {
    return (CommunicationSensor<SmartMeterAgentBody, HouseEnvironment>) getSensor(CommunicationSensor.class);
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

  private void handleBrainMessage(Object arg) {
    brainHandler.handle(arg, this);
  }

  private void handleSensorMessage(Object arg) {
    notifyObservers(arg, this.getBrainclass());
  }

  /*
   * ************************************ NOTE *******************************
   * For simulation the BrainHandler will always be Normal. IPCommunication is
   * unnecessary for agent communication and will only be used if the system is
   * deployed in a real setting. IP Communication is not fully implemented - a
   * connection can be set up between two agents but no specific message can be
   * sent - this will be implemented at a later date. TODO
   */

  /**
   * A {@link BrainHandler} is used to decide how to process the messages sent
   * from the {@link Brain} to the {@link Body}. A {@link BrainHandler} should
   * be used as part of a state design pattern. <br/>
   * Known Subclasses: <br/>
   * {@link NormalBrainHandler}, {@link IpBrainHandler}.
   * 
   * @author Benedict Wilkins
   *
   */
  private interface BrainHandler {
    void handle(Object arg, SmartMeterAgentBody agent);
  }

  /**
   * An instance of {@link BrainHandler} that is used under normal
   * circumstances. <br/>
   * It will be used when the {@link Actuator} uses the
   * {@link AbstractActuator#update(CustomObservable, Object)} method in a
   * normal way - using a {@link HouseEvent}. <br/>
   * Extends: {@link BrainHandler}.
   * 
   * @author Benedict Wilkins
   *
   */
  private class NormalBrainHandler implements BrainHandler {
    @Override
    public void handle(Object arg, SmartMeterAgentBody agent) {
      SmartMeterAgentBody.this
          .notifyObservers(arg, CommunicationActuator.class);
    }
  }

  /**
   * An instance of {@link BrainHandler} that is used when the {@link Actuator}
   * is a {@link IpCommunicationActuator}. <br/>
   * Extends: {@link NormalBrainHandler}.
   * 
   * @author Benedict Wilkins
   *
   */
  private class IpBrainHandler extends NormalBrainHandler {
    @Override
    public void handle(Object arg, SmartMeterAgentBody agent) {
      // create an event and give it to the actuator
      if (arg instanceof IpCommunicationAction) {
        SmartMeterAgentBody.this.notifyObservers(arg,
            IpCommunicationActuator.class);
      } else {
        super.handle(arg, agent);
      }
    }
  }
}
