package threading;

import utilities.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimulationAgentThreadManager extends AgentThreadManager {

  private Map<String, Set<AgentRunnable>> runnablesMap;
  private List<String> executionGroups;

  public SimulationAgentThreadManager(List<String> executionGroups) {
    super();
    this.runnablesMap = new HashMap<>();
    this.executionGroups = executionGroups;
    for (String c : executionGroups) {
      runnablesMap.put(c, new HashSet<>());
    }
  }

  @Override
  public void start(long cycleTime) {
    super.simulationStarted = true;
    System.out.println(Arrays.toString(runnables.toArray()));
    while (this.simulationStarted) {
      for (String s : executionGroups) {
        System.out.println(System.lineSeparator() + "RUN GROUP: " + s);
        super.runnables = runnablesMap.get(s);
        doPerceive();
        doDecide();
        doExecute();
      }
      try {
        Thread.sleep(cycleTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void addAgent(AgentRunnable runnable, String group) {
    if (runnablesMap.containsKey(group)) {
      runnablesMap.get(group).add(runnable);
    } else {
      throw new IllegalStateException("INVALID RUNNABLE GROUP: " + group);
    }
  }

  /**
   * Does nothing, use {@link #addAgent(AgentRunnable, String)} instead.
   */
  @Override
  public void addAgent(AgentRunnable runnable) {
  }

}
