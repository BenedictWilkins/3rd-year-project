package demo;

import housemodels.CombinedNormalHouseModel;
import housemodels.HouseModelFactory;

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
    double error = 1.0;
    CombinedNormalHouseModel[] models = new CombinedNormalHouseModel[2];
    models[0] = HouseModelFactory.getFactory().createAffluentCombinedNormalHouseModel(error);
    models[1] = HouseModelFactory.getFactory().createAffluentCombinedNormalHouseModel(error);
    new Simulator(models);
  }
}
