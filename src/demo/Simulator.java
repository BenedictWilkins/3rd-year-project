package demo;

import agent.NeighbourhoodAgentBody;
import agent.NeighbourhoodAgentMind;
import agent.PredictorAgentMind;
import agent.SmartMeterAgentBody;
import agent.SmartMeterAgentMind;
import agent.general.AgentFactory;
import agent.general.AgentStructure;
import agent.general.AgentType;
import agent.general.EnvironmentFactory;
import agent.general.GeneralAgentBody;
import environment.HouseEnvironment;
import environment.communication.module.Address;
import environment.communication.module.SimulationAddress;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.ForecastingModel;
import threading.AgentRunnable;
import threading.SimulationAgentThreadManager;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import utilities.IDFactory;
import housemodel.combination.AdditiveCombinator;
import housemodel.combination.Combinator;
import housemodel.threshold.MaximumThreshold;
import housemodel.threshold.ModelModifier;
import housemodel.threshold.ModelModifierMagnitudeCombinedNormal;
import housemodel.threshold.Threshold;
import housemodels.HalfHourClock;
import housemodels.House;
import housemodels.HouseFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
  private static final Integer TIMEGAP = 100;

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
      .createAcornUHouse(0.0);
  private final ModelModifier DEFAULTMODELMODIFIER = new ModelModifierMagnitudeCombinedNormal(
      0.99);
  private final Threshold DEFAULTTHREASHOLD = new MaximumThreshold(0.0,
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
    threadManager = new SimulationAgentThreadManager(ORDEREDRUNGROUPS,
        HalfHourClock.getInstance());

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
    Thread inputThread = new Thread(new Runnable() {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          System.in));

      @Override
      public void run() {
        try {
          while (true) {
            String line = reader.readLine();
            char[] chars = line.toLowerCase().toCharArray();
            if(chars[0] == 'p') {
              //pause the simulation
              threadManager.pause();
            } else if(chars[0] == 's') {
              //restart the simulation
              threadManager.unpause();
            }
          }
        } catch (IOException e) {

          e.printStackTrace();
        }
      }
    });
    inputThread.start();
    threadManager.start(TIMEGAP);
  }
}
