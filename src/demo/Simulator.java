package demo;

import agent.CommunicationActuator;
import agent.CommunicationSensor;
import agent.IpCommunicationActuator;
import agent.IpCommunicationSensor;
import agent.NeighbourhoodAgentBody;
import agent.NeighbourhoodAgentBrain;
import agent.NeighbourhoodAgentMind;
import agent.PredictorAgentMind;
import agent.SmartMeterAgentBody;
import agent.SmartMeterAgentMind;
import agent.actions.CommunicationAction;
import agent.actions.TakeReadingAction;
import agent.general.AgentFactory;
import agent.general.AgentStructure;
import agent.general.AgentType;
import agent.general.EnvironmentFactory;
import agent.general.GeneralAgentBody;
import environment.HouseEnvironment;
import environment.HouseEnvironmentAppearance;
import environment.HouseEnvironmentPhysics;
import environment.HouseEnvironmentSpace;
import environment.NationalGridUniverse;
import environment.NationalGridUniversePhysics;
import environment.NationalGridUniverseSpace;
import environment.communication.module.Address;
import environment.communication.module.SimulationAddress;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.ForecastingModel;
import threading.AgentRunnable;
import threading.SimulationAgentThreadManager;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;
import utilities.IDFactory;
import housemodel.combination.AdditiveCombinator;
import housemodel.combination.Combinator;
import housemodel.combination.ReadingCombinator;
import housemodel.threshold.MaximumThreshold;
import housemodel.threshold.ModelModifier;
import housemodel.threshold.ModelModifierMagnitudeCombinedNormal;
import housemodel.threshold.Threshold;
import housemodels.HalfHourClock;
import housemodels.House;
import housemodels.HouseFactory;
import housemodels.HouseModel;
import housemodels.HouseModelCombinedNormal;
import housemodels.HouseModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class simulates the entire program. Its job is to load all agents and
 * environments, define possible actions, Socket protocols/information etc.
 */
public class Simulator {

  // Details for IP communication (if applicable to the simulation)
  private static final int SERVERPORT = 8888;
  private static final String SERVERNAME = "localhost";
  private static final Boolean IPCommunication = false;

  // The time between cycles (every half hour) in ms
  private static final Integer TIMEGAP = 1000;

  private final SimulationAddress PREDICTORADDRESS = new SimulationAddress(
      "PREDICTOR");

  private static final Map<Class<?>, String> RUNGROUPS;
  private static final List<String> ORDEREDRUNGROUPS;

  static {
    Map<Class<?>, String> rungroups = new HashMap<>();
    rungroups.put(SmartMeterAgentMind.class, "M");
    rungroups.put(NeighbourhoodAgentMind.class, "N");
    rungroups.put(PredictorAgentMind.class, "P");
    RUNGROUPS = Collections.unmodifiableMap(rungroups);
    List<String> orderedrungroups = new ArrayList<>();
    orderedrungroups.add("M");
    orderedrungroups.add("N");
    orderedrungroups.add("P");
    ORDEREDRUNGROUPS = Collections.unmodifiableList(orderedrungroups);
  }

  private final House DEFAULTHOUSE = HouseFactory.getFactory()
      .createAcornUHouse(0.5);
  private final ModelModifier DEFAULTMODELMODIFIER = new ModelModifierMagnitudeCombinedNormal();
  private final Threshold DEFAULTTHREASHOLD = new MaximumThreshold(1.0,
      DataFrameMetaTimeValue.getValueColumnIndex());
  private final Combinator<Double, Double> DEFAULTCOMBINATOR = new AdditiveCombinator();
  private final ForecastingModel DEFAULTFORECASTINGMODEL = new ForecastingModel() {
    @Override
    public void trainModel(DataFrame data) {
      // TODO Auto-generated method stub

    }

    @Override
    public DataFrame getForecasts() {
      // TODO Auto-generated method stub
      return null;
    }
  };

  private final SimulationAgentThreadManager threadManager;

  private Set<Body> highAgents = new HashSet<>();
  private List<HouseEnvironment> houseEnvironments = new ArrayList<>();

  public Simulator(AgentStructure structure) {
    AgentFactory.getInstance(); // this line is required!
    // create thread manager
    threadManager = new SimulationAgentThreadManager(ORDEREDRUNGROUPS);

    recurseStructure(structure, PREDICTORADDRESS);
    EnvironmentFactory.getInstance().createUniverse(houseEnvironments,
        highAgents);
    startSimulation();
  }

  private SimulationAddress recurseStructure(AgentStructure structure,
      Address manager) {
    SimulationAddress id = new SimulationAddress(IDFactory.getInstance()
        .createID());
    Set<Address> subaddresses = new HashSet<>();
    for (AgentStructure as : structure.getSubordinates()) {
      subaddresses.add(recurseStructure(as, id));
    }
    System.out.println(structure.getType() + ":" + id.getAdress());
    if (structure.getType().equals(AgentType.SMARTMETER)) {
      houseEnvironments.add(EnvironmentFactory.getInstance()
          .createHouseEnvironment(DEFAULTHOUSE,
              createSmartMeterAgentFromStruct(structure, id, manager)));
    } else if (structure.getType().equals(AgentType.NEIGHBOURHOOD)) {
      highAgents.add(createNeighbourhoodAgentFromStruct(structure, id, manager,
          subaddresses, DEFAULTCOMBINATOR));
    } else if (structure.getType().equals(AgentType.PREDICTOR)) {
      highAgents.add(createPredictorAgentFromStruct(structure, id, manager,
          subaddresses, DEFAULTCOMBINATOR, DEFAULTFORECASTINGMODEL,
          DEFAULTTHREASHOLD, DEFAULTMODELMODIFIER));
    } else {
      System.err.println("UNSUPPORTED AGENT TYPE: " + structure.getType());
      return null;
    }
    return id;
  }

  private NeighbourhoodAgentBody createPredictorAgentFromStruct(
      AgentStructure structure, Address address, Address managerAddress,
      Set<Address> subordinateAddresses, Combinator<Double, Double> combinator,
      ForecastingModel forecastingModel, Threshold threshold,
      ModelModifier modelModifier) {
    GeneralAgentBody body = structure.create(address, managerAddress,
        subordinateAddresses, combinator, forecastingModel, threshold,
        modelModifier);
    addAgentToRunnable(body.getMind());
    return (NeighbourhoodAgentBody) body;
  }

  private NeighbourhoodAgentBody createNeighbourhoodAgentFromStruct(
      AgentStructure structure, Address address, Address manageraddress,
      Set<Address> subordinateAddresses, Combinator<Double, Double> combinator) {
    GeneralAgentBody body = structure.create(address, manageraddress,
        subordinateAddresses, combinator);
    addAgentToRunnable(body.getMind());
    return (NeighbourhoodAgentBody) body;
  }

  private SmartMeterAgentBody createSmartMeterAgentFromStruct(
      AgentStructure structure, Address address, Address manageraddress) {
    GeneralAgentBody body = structure.create(address, manageraddress);
    addAgentToRunnable(body.getMind());
    return (SmartMeterAgentBody) body;
  }

  private void addAgentToRunnable(Mind mind) {
    threadManager.addAgent(new AgentRunnable(mind),
        RUNGROUPS.get(mind.getClass()));
  }

  private void startSimulation() {
    Thread thread = new Thread(HalfHourClock.getInstance());
    HalfHourClock.getInstance().setRealTimeDelay(TIMEGAP);
    thread.start();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    threadManager.start(TIMEGAP);
  }

  // /**
  // * Creates a new {@link NationalGridUniverse}.
  // *
  // * @param models
  // * an array of {@link HouseModelCombinedNormal}s that should be used
  // * in the each {@link HouseEnvironment} to place in the universe
  // * @return the new {@link NationalGridUniverse}
  // */
  // public NationalGridUniverse createNationalGridUniverse(House[] models) {
  // List<HouseEnvironment> houses = new ArrayList<HouseEnvironment>();
  //
  // int id = 0;
  // for (House m : models) {
  // id++;
  // houses.add(doHouseEnvironment(String.valueOf(id), m));
  // }
  //
  // Set<Body> bodies = new HashSet<Body>();
  // // create a manager agent
  // NeighbourhoodAgentBody manager = AgentFactory.getInstance()
  // .createNeighbourhoodAgent(PREDICTORADDRESS, null,
  // new AdditiveCombinator());
  // bodies.add(manager);
  // manager.setId(MANAGERADDRESS1.getAdress());
  // // create a predictor agent
  // NeighbourhoodAgentBody predictor = createPredictorAgent();
  // bodies.add(predictor);
  // predictor.setId(PREDICTORADDRESS.getAdress());
  //
  // NationalGridUniverse universe = new NationalGridUniverse(
  // new NationalGridUniverseSpace(), UNIVERSEACTIONS, bodies,
  // new NationalGridUniversePhysics(), houses);
  // ((CommunicationActuator) manager.getSmartMeterActuator())
  // .addObserver(universe);
  // ((CommunicationActuator) predictor.getSmartMeterActuator())
  // .addObserver(universe);
  // return universe;
  // }
}
