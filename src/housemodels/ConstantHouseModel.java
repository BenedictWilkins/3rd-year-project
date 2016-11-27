package housemodels;

import utilities.DateTimeInterface;

/**
 * A basic {@link HouseModel} that provides a constant reading value.
 * 
 * @author Benedict Wilkins
 *
 */
public class ConstantHouseModel implements HouseModel {

  private Double value;

  /**
   * Constructor.
   * 
   * @param value
   *          constant for all readings
   */
  public ConstantHouseModel(Double value) {
    this.value = value;
  }

  @Override
  public Double getReading(DateTimeInterface dateTime) {
    return this.value;
  }
}
