package agent.general;

import agent.CommunicationActuator;
import agent.SmartMeterAgentBody;
import environment.HouseEnvironment;
import environment.HouseEnvironmentAppearance;
import environment.HouseEnvironmentPhysics;
import environment.HouseEnvironmentSpace;
import environment.NationalGridUniverse;
import environment.NationalGridUniversePhysics;
import environment.NationalGridUniverseSpace;
import housemodels.House;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Environment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Factory class for creating {@link Environment}s. Namely:
 * {@link NationalGridUniverse} and {@link HouseEnvironment}.
 * 
 * @author Benedict Wilkins
 *
 */
public class EnvironmentFactory {

  // single instance of this class
  private static final EnvironmentFactory instance = new EnvironmentFactory();
  private int housecount = 0;

  // Singleton constructor
  private EnvironmentFactory() {
  }

  /**
   * Factory method for creating {@link HouseEnvironment}s.
   * 
   * @param model
   *          to use, see {@link House}
   * @param agent
   *          {@link SmartMeterAgentBody SmartMeterAgent} that reside in the
   *          {@link HouseEnvironment}.
   * @return a new instance of {@link HouseEnvironment}
   */
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

  /**
   * Factory method for creating a {@link NationalGridUniverse}.
   * 
   * @param houses
   *          that are contained
   * @param agents
   *          that are contained
   * @return a new instance of {@link NationalGridUniverse}
   */
  public NationalGridUniverse createUniverse(List<HouseEnvironment> houses,
      Set<Body> agents) {
    NationalGridUniverse universe = new NationalGridUniverse(
        new NationalGridUniverseSpace(), agents,
        new NationalGridUniversePhysics(), houses);
    agents.forEach((Body body) -> {
      // System.out.println(body);
        ((CommunicationActuator) ((GeneralAgentBody) body)
            .getSmartMeterActuator()).addObserver(universe);
      });
    return universe;
  }

  public static EnvironmentFactory getInstance() {
    return instance;
  }

}
