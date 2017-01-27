package housemodels;

import utilities.NormalDistribution;

public class HouseModelCombinedNormalComfortable extends
    HouseModelCombinedNormal {

  /*
   * ACORNU(new Double[] { 40.0, 25.0 }, new Double[] { 3.0, 7.0 }, new Double[]
   * { 3.8, 6.0 }, 0.1), ADVERSITY(new Double[] { 1.5, 20.0 }, new Double[] {
   * 1.5, 14.0 }, new Double[] { 1.0, 2.0 }, 0.12), COMFORTABLE(new Double[] {
   * 40.0, 22.0 }, new Double[] { 5.0, 6.5 }, new Double[] { 5.0, 4.0 }, 0.1),
   * AFFLUENT( new Double[] { 48.0, 24.0 }, new Double[] { 9.0, 5.0 }, new
   * Double[] { 6.0, 1.5 }, 0.15);
   */

  public HouseModelCombinedNormalComfortable() {
    super(new NormalDistribution(40.0, 5.0, 5.0, 0.05), new NormalDistribution(
        22.0, 6.5, 4.0, 0.05));
  }

}
