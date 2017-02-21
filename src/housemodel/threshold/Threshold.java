package housemodel.threshold;

import housemodels.HouseModel;
import machinelearning.agent.DataFrame;

/**
 * A class implementing {@link Threshold} should be queried by a top level
 * agent. The {@link Threshold} defines the point at which the
 * {@link HouseModel}s in use should change their behaviour. This will simulate
 * the real consumer behaviour change (Demand-side Management).
 * 
 * @author Benedict Wilkins
 *
 */
public interface Threshold {

  /**
   * Check the given data against the threshold represented by this
   * {@link Threshold}.
   * 
   * @param data
   *          to check
   * @return true if threshold is met, false otherwise
   */
  public boolean checkThreshold(DataFrame data);

}
