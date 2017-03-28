package agent.general;

import agent.NeighbourhoodAgentMind;
import agent.PredictorAgentMind;
import agent.SmartMeterAgentMind;
import housemodels.House;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class that is the simple representation of an agent. This class is used to
 * define the agent hierarchy from a config file or otherwise. It also uses the
 * {@link AgentFactory} to automatically create the agent it represents.
 * 
 * @author Benedict Wilkins
 *
 */
public class AgentStructure {
  // type of the agent
  private AgentType type;
  // house that the agent will reside in (only for smartmeteragents)
  private House house = null;
  private final List<AgentStructure> subordinates;

  /**
   * Constructor for a {@link SmartMeterAgentMind SmartMeterAgent}. The type of
   * this agent will be {@link AgentType#SMARTMETER}.
   * 
   * @param house
   *          that the agent will reside in
   * @param subordinates
   *          any agents this agent may manage
   */
  public AgentStructure(House house, AgentStructure... subordinates) {
    this.subordinates = Collections.unmodifiableList(Arrays
        .asList(subordinates));
    this.type = AgentType.SMARTMETER;
    this.house = house;
  }

  /**
   * Constructor for {@link NeighbourhoodAgentMind NeighbourhoodAgents} and
   * {@link PredictorAgentMind PredictorAgents}.
   * 
   * @param type
   *          {@link AgentType#NEIGHBOURHOOD} or {@link AgentType#PREDICTOR}
   * @param subordinates
   *          any agents this agent may manage
   */
  public AgentStructure(AgentType type, AgentStructure... subordinates) {
    if (AgentType.SMARTMETER.equals(type)) {
      throw new IllegalArgumentException(AgentType.SMARTMETER
          + " requires a house. Use alternative constructor.");
    }
    this.subordinates = Collections.unmodifiableList(Arrays
        .asList(subordinates));
    this.type = type;
  }

  /**
   * Creates the agent represented by this {@link AgentStructure} using the
   * {@link AgentFactory}.
   * 
   * @param args
   *          for the constructor of the agent to be created
   * @return the new agent
   */
  public GeneralAgentBody create(Object... args) {
    try {
      return (GeneralAgentBody) type.getCreationMethod().invoke(
          AgentFactory.getInstance(), args);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public AgentType getType() {
    return this.type;
  }

  public List<AgentStructure> getSubordinates() {
    return subordinates;
  }

  public House getHouse() {
    return house;
  }

  public void setHouse(House house) {
    this.house = house;
  }
}
