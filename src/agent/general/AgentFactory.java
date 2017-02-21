package agent.general;

import agent.CommunicationActuator;
import agent.CommunicationSensor;
import agent.NeighbourhoodAgentBody;
import agent.NeighbourhoodAgentBrain;
import agent.NeighbourhoodAgentMind;
import agent.PredictorAgentMind;
import agent.SmartMeterAgentBody;
import agent.SmartMeterAgentBrain;
import agent.SmartMeterAgentMind;
import environment.AbstractEnvironment;
import environment.HouseEnvironment;
import environment.NationalGridUniverse;
import environment.communication.module.Address;
import machinelearning.agent.ForecastingModel;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import housemodel.combination.Combinator;
import housemodel.combination.ReadingCombinator;
import housemodel.threshold.ModelModifier;
import housemodel.threshold.Threshold;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AgentFactory {

  /**
   * A Marker {@link Annotation} that indicates which {@link Method} in this
   * Factory are used to create different {@link Agent}s. Any {@link Method}
   * using this {@link Annotation} must have the {@link GeneralAgentBody} return
   * type.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface AgentCreationMethod {
    /**
     * The name of the {@link Agent} that will be created by the method.
     */
    public AgentType name();
  }

  // singleton instance
  private static final AgentFactory instance = new AgentFactory();
  // set up creation methods in AgentType
  static {
    Method[] methods = AgentFactory.class.getMethods();
    for (Method m : methods) {
      Annotation[] annotations = m.getAnnotations();
      for (Annotation a : annotations) {
        if (AgentCreationMethod.class.isAssignableFrom(a.getClass())) {
          ((AgentCreationMethod) a).name().setCreationMethod(m);
        }
      }
    }
  }

  // singleton constructor
  private AgentFactory() {

  }

  /**
   * Creates a {@link NeighbourhoodAgentBody PredictorAgent} whose parts
   * include: {@link NeighbourhoodAgentBody}, {@link NeighbourhoodAgentBrain},
   * {@link PredictorAgentMind}, {@link CommunicationSensor},
   * {@link CommunicationActuator}. A PredictorAgent is defined by its
   * {@link PredictorAgentMind}.
   * 
   * @param address
   *          the {@link Address} of the {@link Agent}
   * @param managerAddress
   *          the {@link Address} of the {@link Agent}s manager
   * @param subordinateAddresses
   *          the {@link Address}es of the {@link Agent}s this {@link Agent}
   *          manages
   * @param combinator
   *          the {@link Combinator} used to combine readings received from
   *          lower level {@link Agent}s
   * @param forecastingModel
   *          the {@link ForecastingModel} that will be used to compute
   *          forecasts
   * @param threshold
   *          the {@link Threshold} that will be used to decide the behaviour of
   *          the lower level {@link Agent}s
   * @param modelModifier
   *          the {@link ModelModifier} that will used to specify how lower
   *          level {@link Agent}s behaviour should change
   * @return a new instance of {@link NeighbourhoodAgentBody}
   */
  @AgentCreationMethod(name = AgentType.PREDICTOR)
  public NeighbourhoodAgentBody createPredictorAgent(Address address,
      Address managerAddress, Set<Address> subordinateAddresses,
      Combinator<Double, Double> combinator, ForecastingModel forecastingModel,
      Threshold threshold, ModelModifier modelModifier) {
    List<Sensor> sensors = createCommunicationSensor(NeighbourhoodAgentBody.class,
        NationalGridUniverse.class);
    List<Actuator> actuators = createCommunicationActuator(
        NeighbourhoodAgentBody.class, NationalGridUniverse.class);
    PredictorAgentMind mind = new PredictorAgentMind(
        NeighbourhoodAgentBrain.class, new ReadingCombinator(combinator),
        NationalGridUniverse.UNIVERSEACTIONS, managerAddress,
        subordinateAddresses, forecastingModel, threshold, modelModifier);
    NeighbourhoodAgentBrain brain = new NeighbourhoodAgentBrain(
        PredictorAgentMind.class, NeighbourhoodAgentBody.class);
    NeighbourhoodAgentBody body = new NeighbourhoodAgentBody(null, sensors,
        actuators, mind, brain);
    body.setId(address.getAdress());
    return body;
  }

  /**
   * Creates a {@link SmartMeterAgentBody SmartMeterAgent} whose parts include:
   * {@link SmartMeterAgentBody}, {@link SmartMeterAgentBrain},
   * {@link SmartMeterAgentMind}, {@link CommunicationSensor},
   * {@link CommunicationActuator}.
   * 
   * @param address
   *          the {@link Address} of the {@link Agent}
   * @param managerAddress
   *          the {@link Address} of the {@link Agent}s manager
   * @return a new instance of {@link SmartMeterAgentBody}
   */
  @AgentCreationMethod(name = AgentType.SMARTMETER)
  public SmartMeterAgentBody createBasicSmartMeterAgent(Address address,
      Address managerAddress) {
    List<Sensor> sensors = createCommunicationSensor(SmartMeterAgentBody.class,
        HouseEnvironment.class);
    List<Actuator> actuators = createCommunicationActuator(
        SmartMeterAgentBody.class, HouseEnvironment.class);
    SmartMeterAgentMind mind = new SmartMeterAgentMind(
        SmartMeterAgentBrain.class, HouseEnvironment.HOUSEACTIONS,
        managerAddress);
    SmartMeterAgentBrain brain = new SmartMeterAgentBrain(
        SmartMeterAgentMind.class, SmartMeterAgentBody.class);
    SmartMeterAgentBody body = new SmartMeterAgentBody(sensors, actuators,
        mind, brain);
    body.setId(address.getAdress());
    return body;
  }

  /**
   * Creates a {@link NeighbourhoodAgentBody NeighbourhoodAgent} whose parts
   * include: {@link NeighbourhoodAgentBody}, {@link NeighbourhoodAgentBrain},
   * {@link NeighbourhoodAgentMind}, {@link CommunicationSensor},
   * {@link CommunicationActuator}.
   * 
   * @param address
   *          the {@link Address} of the {@link Agent}
   * @param managerAddress
   *          the {@link Address} of the {@link Agent}s manager
   * @param subordinateAddresses
   *          the {@link Address}es of the {@link Agent}s this {@link Agent}
   *          manages
   * @param combinator
   *          the {@link Combinator} used to combine readings received from
   *          lower level {@link Agent}s
   * @return a new instance of {@link NeighbourhoodAgentBody}
   */
  @AgentCreationMethod(name = AgentType.NEIGHBOURHOOD)
  public NeighbourhoodAgentBody createNeighbourhoodAgent(Address address,
      Address managerAddress, Set<Address> subordinateAddresses,
      Combinator<Double, Double> combinator) {
    List<Sensor> sensors = createCommunicationSensor(
        NeighbourhoodAgentBody.class, NationalGridUniverse.class);
    List<Actuator> actuators = createCommunicationActuator(
        NeighbourhoodAgentBody.class, NationalGridUniverse.class);

    NeighbourhoodAgentMind mind = new NeighbourhoodAgentMind(
        NeighbourhoodAgentBrain.class, new ReadingCombinator(combinator),
        NationalGridUniverse.UNIVERSEACTIONS, managerAddress,
        subordinateAddresses);
    NeighbourhoodAgentBrain brain = new NeighbourhoodAgentBrain(
        NeighbourhoodAgentMind.class, NeighbourhoodAgentBody.class);
    NeighbourhoodAgentBody body = new NeighbourhoodAgentBody(null, sensors,
        actuators, mind, brain);
    body.setId(address.getAdress());
    return body;
  }

  /**
   * Creates a new {@link CommunicationSensor} and returns it as a single
   * element {@link List}.
   * 
   * @param body
   *          the class of the body that the {@link Sensor} will be attached to
   * @param environment
   *          the class of the environment that the {@link Sensor} will be
   *          sensing.
   * @return a {@link List} containing a single {@link CommunicationSensor}.
   */
  public List<Sensor> createCommunicationSensor(
      Class<? extends GeneralAgentBody> body,
      Class<? extends AbstractEnvironment> environment) {
    List<Sensor> sensors = new ArrayList<Sensor>();
    sensors.add(new CommunicationSensor(body, environment));
    return sensors;
  }

  /**
   * Creates a new {@link CommunicationActuator} and returns it as a single
   * element {@link List}.
   * 
   * @param body
   *          the class of the body that the {@link Actuator} will be attached
   *          to
   * @param environment
   *          the class of the environment that the {@link Actuator} will be
   *          affecting.
   * @return a {@link List} containing a single {@link CommunicationActuator}.
   */
  public List<Actuator> createCommunicationActuator(
      Class<? extends GeneralAgentBody> body,
      Class<? extends AbstractEnvironment> environment) {
    List<Actuator> actuators = new ArrayList<Actuator>();
    actuators.add(new CommunicationActuator(body, environment));
    return actuators;
  }

  /**
   * Getter for the single instances of {@link AgentFactory}.
   * 
   * @return the instance
   */
  public static AgentFactory getInstance() {
    return instance;
  }
}
