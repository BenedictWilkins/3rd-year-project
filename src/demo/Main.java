package demo;

import housemodels.House;
import housemodels.HouseFactory;

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
    double error = 0.05;
    House[] models = new House[2];
    models[0] = HouseFactory.getFactory().createAffluentHouse(error);
    models[1] = HouseFactory.getFactory().createAdversityHouse(error);
    new Simulator(models);
  }
}
