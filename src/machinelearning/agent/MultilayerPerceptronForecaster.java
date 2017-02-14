package machinelearning.agent;

import weka.classifiers.functions.MultilayerPerceptron;

public class MultilayerPerceptronForecaster extends MultilayerPerceptron
    implements ForecastingModel {

  private static final long serialVersionUID = 1L;

  @Override
  public void trainModel(DataFrame data) {
  }

  @Override
  public DataFrame getForecasts() {
    return null;
  }
}
