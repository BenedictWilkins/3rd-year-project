package machinelearning.agent;

import utilities.DateTime;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;
import housemodels.HalfHourClock;

import java.util.ArrayList;
import java.util.List;

public class MultilayerPerceptronForecastingModel extends
    AbstractWekaForecastingModel {

  private MultilayerPerceptron model;
  private WekaForecaster forecaster;
  private String endTrainingTime = null;

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
