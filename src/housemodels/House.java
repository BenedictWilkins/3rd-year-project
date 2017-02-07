package housemodels;

import utilities.DateTime;

public class House implements HouseModel {

  private HouseModel model;
  private SeasonModifier modifier;
  private Double error;

  /**
   * Constructor.
   * 
   * @param model
   *          house model to uses (daily)
   * @param modifier
   *          seasonal model to use (yearly)
   * @param error
   *          artificial error added to data takes the form: (random(0,1) * 2 *
   *          error) - error
   */
  public House(HouseModel model, SeasonModifier modifier, Double error) {
    this.model = model;
    this.modifier = modifier;
    this.error = error;
  }

  public Double getReading(DateTime dateTime) {
    return model.getReading(dateTime) + modifier.getModiferValue(dateTime)
        + ((Math.random() * error * 2) - error);
  }

  public Double getError() {
    return error;
  }

  public void setError(Double error) {
    this.error = error;
  }

}
