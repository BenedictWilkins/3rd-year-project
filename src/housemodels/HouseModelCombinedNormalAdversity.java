package housemodels;

import utilities.CombinedNormalDistribution;
import utilities.NormalDistribution;

/**
 * Adversity house type whose underlying model is
 * {@link CombinedNormalDistribution}. See {@link HouseModelCombinedNormal}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseModelCombinedNormalAdversity extends HouseModelCombinedNormal {

  public HouseModelCombinedNormalAdversity() {
    super(new NormalDistribution(1.5, 1.5, 1.0, 0.06), new NormalDistribution(
        20.0, 14.0, 2.0, 0.06));
  }

  @Override
  public String toString() {
    return "Adversity";
  }
}
