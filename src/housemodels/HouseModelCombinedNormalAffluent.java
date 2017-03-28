package housemodels;

import utilities.CombinedNormalDistribution;
import utilities.NormalDistribution;
/**
 * Affluent house type whose underlying model is {@link CombinedNormalDistribution}.
 * See {@link HouseModelCombinedNormal}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseModelCombinedNormalAffluent extends HouseModelCombinedNormal {


  public HouseModelCombinedNormalAffluent() {
    super(new NormalDistribution(48.0, 9.0, 6.0, 0.15), new NormalDistribution(
        24.0, 5.0, 1.5, 0.0));
  }

  @Override
  public String toString() {
    return "Affluent";
  }
}
