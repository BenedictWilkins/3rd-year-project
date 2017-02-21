package housemodels;

import utilities.DateTime;
import utilities.MathFunctionClass;
import housemodel.threshold.Modifiable;

/**
 * Interface for a house model. A house model is used to generate data.
 * 
 * @author Benedict Wilkins
 *
 */
public interface HouseModel extends MathFunctionClass<Double>, Modifiable {

  /**
   * Gets a reading at a given {@link DateTime} dt.
   * 
   * @param dt
   *          the {@link DateTime} at which to take the reading
   * @return the reading
   */
  public Double getReading(DateTime dt);
}
