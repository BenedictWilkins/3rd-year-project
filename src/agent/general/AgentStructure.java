package agent.general;

import housemodels.House;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AgentStructure {

  private AgentType type;
  private House house = null;
  private final List<AgentStructure> subordinates;

  public AgentStructure(House house, AgentStructure... subordinates) {
    this.subordinates = Collections.unmodifiableList(Arrays
        .asList(subordinates));
    this.type = AgentType.SMARTMETER;
    this.house = house;
  }

  public AgentStructure(AgentType type, AgentStructure... subordinates) {
    if (AgentType.SMARTMETER.equals(type)) {
      throw new IllegalArgumentException(AgentType.SMARTMETER
          + " requires a house. Use alternative constructor.");
    }
    this.subordinates = Collections.unmodifiableList(Arrays
        .asList(subordinates));
    this.type = type;
  }

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
