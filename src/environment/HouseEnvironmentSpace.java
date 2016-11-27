package environment;

import housemodels.HouseModel;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import utilities.DateTime;

/**
 * The {@link Space} of the {@link HouseEnvironment}. Contains the
 * {@link HouseModel}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEnvironmentSpace extends CustomObservable implements Space {

  private HouseModel model;

  /**
   * Constructor.
   * 
   * @param model
   *          the house model for the house that this @ HouseEnvironmentSpace}
   *          represents.
   */
  public HouseEnvironmentSpace(HouseModel model) {
    this.model = model;
  }

  /**
   * Gets a reading from the {@link HouseModel}.
   * 
   * @param dateTime
   *          the time at which the reading should be taken
   * @return a reading
   */
  public Double getReading(DateTime dateTime) {
    return model.getReading(dateTime);
  }
}
