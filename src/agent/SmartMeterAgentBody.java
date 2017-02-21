package agent;

import agent.actions.HouseEvent;
import agent.actions.IpCommunicationAction;
import agent.general.GeneralAgentBody;
import environment.HouseEnvironment;
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
public class SmartMeterAgentBody extends GeneralAgentBody {

  // changes depending what actuator/sensors are in use
  private BrainHandler brainHandler;

  /**
   * Constructor. See:
   * {@link AbstractAgent#AbstractAgent(AbstractAgentAppearance, List, List, AbstractAgentMind, AbstractAgentBrain)}
   * .
   */
  public SmartMeterAgentBody(List<Sensor> sensors, List<Actuator> actuators,
      SmartMeterAgentMind mind, SmartMeterAgentBrain brain) {
    super(null, sensors, actuators, mind, brain);
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
