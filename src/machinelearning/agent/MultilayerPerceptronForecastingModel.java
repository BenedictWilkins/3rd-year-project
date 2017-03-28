package machinelearning.agent;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;

import java.util.List;

/**
 * A concrete implementation of {@link AbstractWekaForecastingModel}
 * specifically for the {@link WekaForecaster} used with the
 * {@link MultilayerPerceptron} classifier.
 * 
 * @author Benedict Wilkins
 *
 */
public class MultilayerPerceptronForecastingModel extends
    AbstractWekaForecastingModel {

  private MultilayerPerceptron model;
  private WekaForecaster forecaster;
  private String endTrainingTime = null;

  /**
   * Constructor. See {@link MultilayerPerceptron} for details on parameters.
   * 
   * @param learningRate
   *          {@link MultilayerPerceptron} parameter.
   * @param momentum
   *          {@link MultilayerPerceptron} parameter.
   * @param epochs
   *          {@link MultilayerPerceptron} parameter.
   * @param validation
   *          {@link MultilayerPerceptron} parameter.
   */
  public MultilayerPerceptronForecastingModel(Double learningRate,
      Double momentum, Integer epochs, Integer validation) {
    this.model = new MultilayerPerceptron();
    this.model.setGUI(true);
    if (learningRate != null) {
      this.getModel().setLearningRate(learningRate);
    }
    if (momentum != null) {
      this.getModel().setMomentum(momentum);
    }
    if (epochs != null) {
      this.getModel().setTrainingTime(epochs);
    }
    if (validation != null) {
      this.getModel().setValidationThreshold(validation);
    }
    try {
      forecaster = new WekaForecaster();
      forecaster.setFieldsToForecast(DataFrameMetaTimeValue.getInstance()
          .getHeaders()[DataFrameMetaTimeValue.getValueColumnIndex()]);
      forecaster.setBaseForecaster(model);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void trainModel(DataFrame data) {
    try {
      endTrainingTime = data.getRow(data.getNumRows() - 1).getValue(0,
          String.class);
      Instances instances = convertToTimeSeriesInstances(data);

      forecaster.buildForecaster(instances, System.out);
      forecaster.primeForecaster(instances);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public DataFrame getForecasts(int numSteps) {
    try {
      List<List<NumericPrediction>> forecasts = forecaster.forecast(numSteps,
          System.out);
      return toDataFrame(forecasts, endTrainingTime);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public MultilayerPerceptron getModel() {
    return this.model;
  }

}
