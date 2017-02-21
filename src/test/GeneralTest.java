package test;

import agent.general.AgentStructure;
import agent.general.AgentType;

public class GeneralTest {

  public static void main(String[] args) {
    AgentStructure[] smarts = createSmartMeterGroup(3);
    AgentStructure neigh = new AgentStructure(AgentType.NEIGHBOURHOOD, smarts);
    AgentStructure top = new AgentStructure(AgentType.PREDICTOR, neigh);
    
    
    
    
    
  }

  public static AgentStructure[] createSmartMeterGroup(int size) {
    AgentStructure[] astructs = new AgentStructure[size];
    for (int i = 0; i < 3; i++) {
      astructs[i] = new AgentStructure(AgentType.SMARTMETER,
          new AgentStructure[] {});
    }
    return astructs;
  }
}
