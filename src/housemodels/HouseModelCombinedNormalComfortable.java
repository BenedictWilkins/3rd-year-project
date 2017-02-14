package housemodels;

import utilities.NormalDistribution;

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
