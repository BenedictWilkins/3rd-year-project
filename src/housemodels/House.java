package housemodels;

import environment.HouseEnvironment;
import housemodel.threshold.ModelModifier;
import utilities.DateTime;

/**
 * This class holds all information about a households behaviour. The
 * {@link HouseModel} and {@link SeasonModel}. This is the class that should be
 * queried for energy readings. All {@link HouseEnvironment}s contain exactly
 * one {@link House}.
 * 
 * @author Benedict Wilkins
 *
 */
public class House implements HouseModel {

  private HouseModel house;
  private SeasonModel season;
  private Double error;

  /**
   * Constructor.
   * 
   * @param house
   *          house model to uses (daily)
   * @param season
   *          seasonal model to use (yearly)
   * @param error
   *          artificial error added to data takes the form: (random(0,1) * 2 *
   *          error) - error
   */
  public House(HouseModel house, SeasonModel season, Double error) {
    this.house = house;
    this.season = season;
    this.error = error;
  }

  public Double getReading(DateTime dateTime) {
    return house.getReading(dateTime) + season.getModiferValue(dateTime)
        + ((Math.random() * error * 2) - error);
  }

  public Double getError() {
    return error;
  }

  public void setError(Double error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return house.toString() + ":" + error;
  }

  @Override
  public Double[] compute(Double[] args) {
    return house.compute(args);
  }

  @Override
  public void modifyModel(ModelModifier modifier) {
    house.modifyModel(modifier);
  }
}
