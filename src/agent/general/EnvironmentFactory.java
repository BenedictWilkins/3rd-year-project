package agent.general;

import agent.CommunicationActuator;
import agent.NeighbourhoodAgentBody;
import agent.SmartMeterAgentBody;
import environment.HouseEnvironment;
import environment.HouseEnvironmentAppearance;
import environment.HouseEnvironmentPhysics;
import environment.HouseEnvironmentSpace;
import environment.NationalGridUniverse;
import environment.NationalGridUniversePhysics;
import environment.NationalGridUniverseSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import housemodel.combination.AdditiveCombinator;
import housemodels.House;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnvironmentFactory {

  private static final EnvironmentFactory instance = new EnvironmentFactory();
  private static final String HOUSENAME = "HOUSE:";
  private int housecount = 0;

  public HouseEnvironment createHouseEnvironment(House model,
      SmartMeterAgentBody agent) {
    Set<Body> agents = new HashSet<>();
    agents.add(agent);
    HouseEnvironment environment = new HouseEnvironment(
        new HouseEnvironmentSpace(model), agents,
        new HouseEnvironmentPhysics(), true, new HouseEnvironmentAppearance(
            ++housecount));
    ((CommunicationActuator) agent.getSmartMeterActuator())
        .addObserver(environment);
    environment.addObserver(agent.getSensors().get(0));
    environment.getAppearance().setName(agent.getId().toString());
    return environment;
  }

  public NationalGridUniverse createUniverse(List<HouseEnvironment> houses,
      Set<Body> agents) {
    NationalGridUniverse universe = new NationalGridUniverse(
        new NationalGridUniverseSpace(), agents,
        new NationalGridUniversePhysics(), houses);
    agents.forEach((Body body) -> {
      System.out.println(body);
      ((CommunicationActuator) ((GeneralAgentBody) body)
          .getSmartMeterActuator()).addObserver(universe);
    });
    return universe;
  }

  public static EnvironmentFactory getInstance() {
    return instance;
  }

}
