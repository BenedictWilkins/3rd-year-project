package demo;

import agent.CommunicationActuator;
import agent.CommunicationSensor;
import agent.IpCommunicationActuator;
import agent.IpCommunicationSensor;
import agent.NeighbourhoodAgentBody;
import agent.NeighbourhoodAgentBrain;
import agent.NeighbourhoodAgentMind;
import agent.SmartMeterAgentBody;
import agent.SmartMeterAgentBrain;
import agent.SmartMeterAgentMind;
import agent.actions.TakeReadingAction;
import environment.HouseEnvironment;
import environment.HouseEnvironmentAppearance;
import environment.HouseEnvironmentPhysics;
import environment.HouseEnvironmentSpace;
import environment.NationalGridUniverse;
import environment.NationalGridUniversePhysics;
import environment.NationalGridUniverseSpace;
import environment.communication.module.SimulationAddress;
import threading.AgentRunnable;
import threading.AgentThreadManager;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import housemodels.CombinedNormalHouseModel;
import housemodels.TimeDateTracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class simulates the entire program. Its job is to load all agents and
 * environments, define possible actions, Socket protocols/information etc.
 */
public class Simulator {

  /**
   * Possible {@link Action}s in the {@link HouseEnvironment}.
   * */
  public static final Set<Class<? extends AbstractAction>> HOUSEACTIONS = new HashSet<>();
  /**
   * Possible {@link Action}s in the {@link NationalGridUniverse}.
   * */
  public static final Set<Class<? extends AbstractAction>> UNIVERSEACTIONS = new HashSet<>();
  // number of HouseEnvironments in the NationalGridUniverse
  private int totalHouses = 0;
  // Details for IP communication (if applicable to the simulation)
  private static final int SERVERPORT = 8888;
  private static final String SERVERNAME = "localhost";
  private static final Boolean IPCommunication = false;
  // The time between cycles (every half hour) in ms
  private static final Integer TIMEGAP = 1000;

  /**
   * The {@link Set} of all {@link HouseEnvironment}s.
   */
  public Set<HouseEnvironment> houses = new HashSet<>();
  private final SimulationAddress manageraddress = new SimulationAddress(
      "manager");

  /**
   * Constructor.
   * 
   * @param models
   *          an array of {@link CombinedNormalHouseModel}s that should be used
   *          in the each {@link HouseEnvironment} to place in the universe
   */
  public Simulator(CombinedNormalHouseModel[] models) {
    HOUSEACTIONS.add(TakeReadingAction.class);
    AgentThreadManager manager = new AgentThreadManager();
    NationalGridUniverse universe = createNationalGridUniverse(models);
    for (HouseEnvironment h : universe.getHouseSubEnvironments()) {
      manager.addAgent(new AgentRunnable(h.getSmartMeterAgent().getMind()));
    }
    Thread thread = new Thread(TimeDateTracker.getInstance());
    TimeDateTracker.setInterval(TIMEGAP);
    thread.start();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    manager.start(TIMEGAP);
  }

  /**
   * Creates a new {@link NationalGridUniverse}.
   * 
   * @param models
   *          an array of {@link CombinedNormalHouseModel}s that should be used
   *          in the each {@link HouseEnvironment} to place in the universe
   * @return the new {@link NationalGridUniverse}
   */
  public NationalGridUniverse createNationalGridUniverse(
      CombinedNormalHouseModel[] models) {
    List<HouseEnvironment> houses = new ArrayList<HouseEnvironment>();

    int id = 0;
    for (CombinedNormalHouseModel m : models) {
      id++;
      houses.add(doHouseEnvironment(String.valueOf(id), m));
    }

    Set<Body> bodies = new HashSet<Body>();
    NeighbourhoodAgentBody agent = createNeighbourhoodAgent();
    bodies.add(agent);
    agent.setId(manageraddress.getAdress());

    NationalGridUniverse universe = new NationalGridUniverse(
        new NationalGridUniverseSpace(), UNIVERSEACTIONS, bodies,
        new NationalGridUniversePhysics(), houses);
    return universe;
  }

  private NeighbourhoodAgentBody createNeighbourhoodAgent() {
    List<Sensor> sensors = new ArrayList<Sensor>();
    sensors
        .add(new CommunicationSensor<NeighbourhoodAgentBody, NationalGridUniverse>(
            NeighbourhoodAgentBody.class, NationalGridUniverse.class));
    List<Actuator> actuators = new ArrayList<Actuator>();
    actuators
        .add(new CommunicationActuator<NeighbourhoodAgentBody, NationalGridUniverse>(
            NeighbourhoodAgentBody.class, NationalGridUniverse.class));
    if (IPCommunication) {
      sensors.add(new IpCommunicationSensor(SERVERNAME, SERVERPORT));
      actuators.add(new IpCommunicationActuator(SERVERNAME, SERVERPORT));
    }
    return new NeighbourhoodAgentBody(null, sensors, actuators,
        new NeighbourhoodAgentMind(HOUSEACTIONS), new NeighbourhoodAgentBrain());
  }

  /**
   * Sets up a {@link HouseEnvironment}.
   * 
   * @param name
   *          of the house
   * @param model
   *          the {@link CombinedNormalHouseModel} to be used in the
   *          {@link HouseEnvironment}
   * @return a new {@link HouseEnvironment}
   */
  public HouseEnvironment doHouseEnvironment(String name,
      CombinedNormalHouseModel model) {
    HouseEnvironment house = createHouse(model);
    SmartMeterAgentBody agent = house.getSmartMeterAgent();
    ((CommunicationActuator<?,?>) agent.getSmartMeterActuator()).addObserver(house);
    house.addObserver(agent.getSensors().get(0));
    house.getAppearance().setName(name);
    agent.setId(name);
    return house;
  }

  private HouseEnvironment createHouse(CombinedNormalHouseModel model) {
    Set<Body> agent = new HashSet<>();
    agent.add(createSmartMeterAgent());
    return new HouseEnvironment(new HouseEnvironmentSpace(model), HOUSEACTIONS,
        agent, new HouseEnvironmentPhysics(), true,
        new HouseEnvironmentAppearance(++totalHouses));
  }

  private SmartMeterAgentBody createSmartMeterAgent() {
    List<Sensor> sensors = new ArrayList<Sensor>();
    sensors.add(new CommunicationSensor<SmartMeterAgentBody, HouseEnvironment>(
        SmartMeterAgentBody.class, HouseEnvironment.class));
    List<Actuator> actuators = new ArrayList<Actuator>();
    actuators
        .add(new CommunicationActuator<SmartMeterAgentBody, HouseEnvironment>(
            SmartMeterAgentBody.class, HouseEnvironment.class));
    if (IPCommunication) {
      sensors.add(new IpCommunicationSensor(SERVERNAME, SERVERPORT));
      actuators.add(new IpCommunicationActuator(SERVERNAME, SERVERPORT));
    }
    return new SmartMeterAgentBody(sensors, actuators, new SmartMeterAgentMind(
        HOUSEACTIONS, manageraddress), new SmartMeterAgentBrain());
  }
}
