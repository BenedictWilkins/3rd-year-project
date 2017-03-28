package machinelearning.agent;

/**
 * An interface that should be implemented by any model that is to be used for
 * forecasting.
 * 
 * @author Benedict Wilkins
 *
 */
public interface ForecastingModel {

  /**
   * Trains this model on the data given in the {@link DataFrame}.
   * 
   * @param data
   *          to train on
   */
  public void trainModel(DataFrame data);

  /**
   * Gets a forecast made by this model for the number of time steps given.
   * 
   * @param numsteps
   *          number of time steps ahead to forecast
   * @return A {@link DataFrame} containing the forecast
   */
  public DataFrame getForecasts(int numsteps);

  /**
   * Gets the underlying model that is being used (if any).
   * 
   * @return the underlying model
   */
  public Object getModel();

}
