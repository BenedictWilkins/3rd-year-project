package housemodels;

import graph.DataFitter;

import utilities.DateTime;
import utilities.MathUtilities;

public enum HouseTypeCombinedNormal {

  /**
   * The values supplied to these housing groups come from fitting 2 normal
   * curves to the mean KWH per half hour during 1 day for each group. This was
   * done using {@link DataFitter}.
   */
  // structure of array: {mean, sd, multiplier}
  ACORNU(new Double[] { 40.0, 25.0 }, new Double[] { 3.0, 7.0 }, new Double[] {
      3.8, 6.0 }, 0.1), ADVERSITY(new Double[] { 1.5, 20.0 }, new Double[] {
      1.5, 14.0 }, new Double[] { 1.0, 2.0 }, 0.12), COMFORTABLE(new Double[] {
      40.0, 22.0 }, new Double[] { 5.0, 6.5 }, new Double[] { 5.0, 4.0 }, 0.1), AFFLUENT(
      new Double[] { 48.0, 24.0 }, new Double[] { 9.0, 5.0 }, new Double[] {
          6.0, 1.5 }, 0.15);

  private Double[][] distribution;
  private Double range;

  private HouseTypeCombinedNormal(Double[] means, Double[] sds, Double[] mult,
      Double constant) {
    this.distribution = MathUtilities.createCombinedLoopedNormalDataset(means,
        sds, mult, constant, new Double[] { 0.0,
            (double) (DateTime.DAYLENGTH - 1) }, DateTime.DAYLENGTH);
    this.range = MathUtilities.range(getDistribution());
  }

  /**
   * Getter for the range of the distribution values.
   * 
   * @return range of the distribution values
   */
  public Double getRange() {
    return this.range;
  }

  /**
   * Getter for the distribution represented by this HouseType.
   * 
   * @return the distribution
   */
  public Double[] getDistribution() {
    return distribution[1];
  }

  /**
   * Getter for the series and distribution represented by this HouseType.
   * 
   * @return series and distribution array
   */
  public Double[][] getData() {
    return distribution;
  }
}
