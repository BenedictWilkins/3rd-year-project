package housemodels;

import utilities.NormalDistribution;

public class HouseModelCombinedNormalAcornU extends HouseModelCombinedNormal {


  public HouseModelCombinedNormalAcornU() {
    super(new NormalDistribution(40.0, 3.0, 3.8, 0.05), new NormalDistribution(
        25.0, 7.0, 6.0, 0.05));
  }
  
  @Override
  public String toString() {
    return "AcornU";
  }
  
}
