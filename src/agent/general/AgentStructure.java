package agent.general;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AgentStructure {

  private AgentType type;
  private final List<AgentStructure> subordinates;

  public AgentStructure(AgentType type, AgentStructure... subordinates) {
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
}
