package housemodels;

import utilities.NormalDistribution;

public class HouseModelCombinedNormalAffluent extends HouseModelCombinedNormal {


  public HouseModelCombinedNormalAffluent() {
    super(new NormalDistribution(48.0, 9.0, 6.0, 0.15), new NormalDistribution(
        24.0, 5.0, 1.5, 0.0));
  }

}
