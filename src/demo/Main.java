package demo;

import agent.general.AgentStructure;
import agent.general.AgentType;

/**
 * Entry point into the system.
 * 
 * @author Benedict Wilkins
 *
 */
public class Main {

  /**
   * Simulation entry point.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    AgentStructure[] smarts = createSmartMeterGroup(4);
    AgentStructure neigh = new AgentStructure(AgentType.NEIGHBOURHOOD, smarts);
    AgentStructure top = new AgentStructure(AgentType.PREDICTOR, neigh);
    new Simulator(top);
  }

  public static AgentStructure[] createSmartMeterGroup(int size) {
    AgentStructure[] astructs = new AgentStructure[size];
    for (int i = 0; i < size; i++) {
      astructs[i] = new AgentStructure(AgentType.SMARTMETER,
          new AgentStructure[] {});
    }
    return astructs;
  }
}
