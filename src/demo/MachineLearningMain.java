package demo;

import machinelearning.weka.WekaDataLoader;
import utilities.MathUtilities;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instance;
import weka.core.Instances;
import graph.SeriesPlot;

import java.util.ArrayList;
import java.util.List;

public class MachineLearningMain {

  private static final int TRAINSIZE = 300;
  private static final int TESTSIZE = 10;

  /**
   * Entry point for testing MachineLearning algorithms and data visualisation.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    MachineLearningMain main = new MachineLearningMain();
    WekaDataLoader loader = new WekaDataLoader();
    Instances instances = loader.load("AcornUData.csv");
    Instances train = new Instances(instances, 0, 48 * TRAINSIZE);
    Instances test = new Instances(instances, 48 * TRAINSIZE, 48 * TESTSIZE);

    // test
    //SMOreg classifier = new SMOreg();
    MultilayerPerceptron classifier = new MultilayerPerceptron();
    classifier.setValidationSetSize(20);

    LearningExperimenter learner = main.new LearningExperimenter(classifier,
        train, test);
  }

  public class LearningExperimenter {

    public LearningExperimenter(Classifier classifier, Instances train,
        Instances test) {
      try {
        // split instances into train and test
        WekaForecaster forecaster = constructForecaster(classifier, train);
        List<List<NumericPrediction>> predictions = forecaster.forecast(
            test.size(), System.out);
        System.out.println(predictions.size());
        List<Double> ltrain = toList(train);
        List<Double> ltest = toList(test);
        List<Double> lpred = toList(predictions);
        plot(ltrain, ltest, lpred);

      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    private void plot(List<Double> train, List<Double> test,
        List<Double> predictions) {
      SeriesPlot plot = plotData(train, test);
      plotPredicted(predictions, train.size() - 1, plot);
    }

    private SeriesPlot plotData(List<Double> train, List<Double> test) {
      // store data
      List<Double> data = new ArrayList<>();
      // data.addAll(train);
      data.addAll(test);
      // plot
      return new SeriesPlot("Energy Usage",
          data.toArray(new Double[data.size()]), "PredictionPlot");
    }

    private void plotPredicted(List<Double> predictions, int start,
        SeriesPlot plot) {
      plot.addSeries("Predicted Energy Usage",
          predictions.toArray(new Double[predictions.size()]), 0);
    }

    private WekaForecaster constructForecaster(Classifier classifier,
        Instances instances) throws Exception {
      WekaForecaster forecaster = new WekaForecaster();
      forecaster.setFieldsToForecast(" Usage");
      forecaster.setBaseForecaster(new MultilayerPerceptron());
      forecaster.buildForecaster(instances, System.out);
      System.out.println("FORECASTER BUILT");
      forecaster.primeForecaster(instances);
      System.out.println("FORECASER PRIMED");
      return forecaster;
    }

    private Double MSE(List<Double> test, List<Double> predicted) {
      return MathUtilities.sum(MathUtilities.subtract(
          predicted.toArray(new Double[predicted.size()]),
          test.toArray(new Double[test.size()])));
    }

    private List<Double> toList(List<List<NumericPrediction>> predictions) {
      List<Double> data = new ArrayList<>();
      for (List<NumericPrediction> l : predictions) {
        data.add(l.get(0).predicted());
      }
      return data;
    }

    private List<Double> toList(Instances instances) {
      List<Double> data = new ArrayList<>();
      for (int i = 0; i < instances.size(); i++) {
        data.add(instances.get(i).value(1));
      }
      return data;
    }

  }

}
