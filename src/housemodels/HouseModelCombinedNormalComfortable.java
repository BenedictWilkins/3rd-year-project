package housemodels;

import utilities.CombinedNormalDistribution;
import utilities.NormalDistribution;

/**
 * Comfortable house type whose underlying model is
 * {@link CombinedNormalDistribution}. See {@link HouseModelCombinedNormal}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseModelCombinedNormalComfortable extends
    HouseModelCombinedNormal {

  public HouseModelCombinedNormalComfortable() {
    super(new NormalDistribution(40.0, 5.0, 5.0, 0.05), new NormalDistribution(
        22.0, 6.5, 4.0, 0.05));
  }

  @Override
  public String toString() {
    return "Comfortable";
  }

}
