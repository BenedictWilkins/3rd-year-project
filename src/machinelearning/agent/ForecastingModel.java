package machinelearning.agent;

/**
 * An interface that should be implemented by any model that is to be used for
 * forecasting.
 * 
 * @author Benedict Wilkins
 *
 */
public interface ForecastingModel {

  public void trainModel(DataFrame data);

  public DataFrame getForecasts();
  
}
