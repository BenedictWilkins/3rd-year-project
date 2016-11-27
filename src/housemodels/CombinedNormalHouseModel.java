package housemodels;

import utilities.DateTime;
import utilities.DateTimeInterface;
import utilities.MathUtilities;

import java.util.Random;

/**
 * A house model that is representative of real data.
 * 
 * @author Benedict Wilkins
 *
 */
public class CombinedNormalHouseModel implements HouseModel {

  private HouseTypeCombinedNormal type;
  private Double error;
  private Double reducer;
  private Random rand = new Random();

  /**
   * Constructor.
   * 
   * @param type
   *          of house
   * @param error
   *          term to add when generating data
   */
  public CombinedNormalHouseModel(HouseTypeCombinedNormal type, Double error) {
    this.type = type;
    this.error = error;
    this.reducer = this.error * 0.05
        * MathUtilities.range(type.getDistribution());
  }

  @Override
  public Double getReading(DateTimeInterface dateTime) {
    DateTime dt = (DateTime) dateTime;
    // TODO add seasonality!
    Double err = reducer * rand.nextGaussian();
    return type.getDistribution()[dt.getTime()] + err;
  }
}
