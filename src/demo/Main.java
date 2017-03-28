package demo;

import agent.general.AgentStructure;
import agent.general.AgentType;
import housemodels.House;
import housemodels.HouseFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Entry point into the system.
 * 
 * @author Benedict Wilkins
 *
 */
public class Main {

  private static final String CONFIGPATH = "config.txt";
  private static final String CONFIGERRORMESSAGE = "SOMETHING WENT WRONG READING CONFIG FILE, CONTINUING WITH DEFAULT SETTINGS";
  private static final Integer DEFAULTTIMEGAP = 0;
  private static final Double DEFAULTERROR = 0.05;
  private static final Random RANDOM = new Random();

  private static House[] houses1 = new House[] {
      HouseFactory.getFactory().createAcornUHouse(DEFAULTERROR),
      HouseFactory.getFactory().createAdversityHouse(DEFAULTERROR),
      HouseFactory.getFactory().createAffluentHouse(DEFAULTERROR),
      HouseFactory.getFactory().createComfortableHouse(DEFAULTERROR) };

  /*
   * private static House[] houses2 = new House[] {
   * HouseFactory.getFactory().createAcornUHouse(DEFAULTERROR),
   * HouseFactory.getFactory().createAcornUHouse(DEFAULTERROR),
   * HouseFactory.getFactory().createAcornUHouse(DEFAULTERROR) };
   * 
   * private static final int HRSIZE = 10; private static House[] housesRandom =
   * new House[HRSIZE];
   * 
   * private static final int BIG = 10000; private static House[] housesBIG =
   * new House[BIG];
   * 
   * static { for (int i = 0; i < HRSIZE; i++) { housesRandom[i] =
   * getRandomHouse(); }
   * 
   * for (int i = 0; i < BIG; i++) { housesBIG[i] = getRandomHouse(); } }
   */

  /**
   * Simulation entry point.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {

    Integer timegap = null;
    AgentStructure[] agentstructure = null;
    try {
      ConfigReader cr = new ConfigReader(CONFIGPATH);
      timegap = cr.getTimeGap();
      agentstructure = cr.getAgentStructure();
    } catch (IOException e) {
      System.err.println(CONFIGERRORMESSAGE);
      e.printStackTrace();
      agentstructure = createDefaultAgentStructure();
      timegap = DEFAULTTIMEGAP;
    }
    new Simulator(agentstructure, timegap);
  }

  /**
   * Creates the default agent structure, a single predictor and group agent and
   * using {@link Main#houses1} for the smart meter agents.
   * 
   * @return agent structure
   */
  public static AgentStructure[] createDefaultAgentStructure() {
    AgentStructure[] smarts = createSmartMeterGroup(houses1);
    AgentStructure neigh = new AgentStructure(AgentType.NEIGHBOURHOOD, smarts);
    return new AgentStructure[] { new AgentStructure(AgentType.PREDICTOR, neigh) };
  }

  /**
   * Creates a group of {@link AgentStructure} of {@link AgentType#SMARTMETER}.
   * 
   * @param houses
   *          that the agents are contained in
   * @return the new group of {@link AgentStructure}s.
   */
  public static AgentStructure[] createSmartMeterGroup(House[] houses) {
    System.out.println(Arrays.toString(houses));
    AgentStructure[] astructs = new AgentStructure[houses.length];
    for (int i = 0; i < houses.length; i++) {
      astructs[i] = new AgentStructure(houses[i], new AgentStructure[] {});
    }
    return astructs;
  }

  public static House getRandomHouse() {
    Integer r = RANDOM.nextInt(4);
    switch (r) {
    case (0):
      return HouseFactory.getFactory().createAcornUHouse(DEFAULTERROR);
    case (1):
      return HouseFactory.getFactory().createAdversityHouse(DEFAULTERROR);
    case (2):
      return HouseFactory.getFactory().createAffluentHouse(DEFAULTERROR);
    case (3):
      return HouseFactory.getFactory().createComfortableHouse(DEFAULTERROR);
    default:
      return null;
    }
  }
}
