package demo;

import graph.SeriesPlot;
import machinelearning.test.LearningTestCase;
import machinelearning.test.LearningTestCaseMultiLayerPerceptron;
import machinelearning.weka.WekaDataLoader;

import utilities.MathUtilities;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is used for testing forecasting algorithms automatically. To do
 * this create a new {@link LearningTestCase} set up to contain all the user
 * defined parameters (i.e. what should be tested). Then define a method that
 * generates test cases. The algorithm will then be trained using each test case
 * on some generated data (see {@link ExperimentDataGenerator}). <br>
 * NOTE: Only the {@link LearningTestCaseMultiLayerPerceptron} currently works!
 * some changes to this class still need to be made for full testing automation.
 * 
 * @author Benedict Wilkins
 *
 */
public class MachineLearningMain {

  // size of the training set
  private static final int TRAINSIZE = 100;
  // size of the test set
  private static final int TESTSIZE = 5;
  // path to data file
  private static final String DATAPATH = "RandomHouses.csv";

  // logger used to display results of the test.
  private static Logger logger = Logger.getLogger("AlgoLogger");
  private static final String LOGPATH = "logs/algotest";
  private static FileHandler fileHandler;

  private static Map<Class<? extends Classifier>, InfoLogFormat> LOGMAP;

  static {
    LOGMAP = new HashMap<>();
    LOGMAP.put(MultilayerPerceptron.class, new InfoLogFormat() {
      @Override
      public String getInfo(LearningTestCase testcase) {
        LearningTestCaseMultiLayerPerceptron testCaseMlp = (LearningTestCaseMultiLayerPerceptron) testcase;
        StringBuilder builder = new StringBuilder();
        builder.append("MSE:" + testCaseMlp.getMse());
        builder.append(", LR:" + testCaseMlp.getLearningRate());
        builder.append(", M:" + testCaseMlp.getMomentum());
        builder.append(", EP:" + testCaseMlp.getEpochs());
        builder.append(", VS:" + testCaseMlp.getValidationSetSize());
        return builder.toString();
      }
    });
  }

  /**
   * Entry point for testing MachineLearning algorithms and data visualisation.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {

    // set up logger
    try {
      fileHandler = new FileHandler(LOGPATH + System.currentTimeMillis());
      SimpleFormatter formatter = new SimpleFormatter() {
        @Override
        public synchronized String format(LogRecord record) {
          if (record.getLevel() == Level.INFO) {
            return record.getMessage() + System.lineSeparator();
          } else {
            return super.format(record);
          }
        }
      };
      fileHandler.setFormatter(formatter);
      logger.addHandler(fileHandler);
    } catch (SecurityException | IOException e) {
      e.printStackTrace();
    }

    // test different algorithms/params
    metaLearn(DATAPATH);
    for (Handler h : logger.getHandlers()) {
      h.close();
    }

  }

  /**
   * Trains the {@link LearningTestCaseMultiLayerPerceptron}s. (this method will
   * be altered to support any {@link LearningTestCase}.
   * 
   * @param fileName
   *          of the data file to use
   */
  public static void metaLearn(String fileName) {
    MachineLearningMain main = new MachineLearningMain();
    WekaDataLoader loader = new WekaDataLoader();
    Instances instances = loader.load(fileName);

    Instances train = new Instances(instances, 0, 48 * TRAINSIZE);
    Instances test = new Instances(instances, 48 * TRAINSIZE, 48 * TESTSIZE);
    // log meta data:
    logMetaData(fileName, instances.size(), train.size(), test.size());

    // generate testCases
    LearningTestCaseMultiLayerPerceptron[] tests = generateMultilayerPerceptronTestCases(
        train, test, 1);
    System.out.println("GENERATED TEST CASES: ");
    for (LearningTestCaseMultiLayerPerceptron t : tests) {
      System.out.println(t);
    }

    LearningExperimenter best = null;
    for (LearningTestCaseMultiLayerPerceptron t : tests) {
      t.getClassifier().setGUI(true);
      System.out
          .println("HIDDEN LAYERS:" + t.getClassifier().getHiddenLayers());
      train = new Instances(instances, 0, 48 * TRAINSIZE);
      test = new Instances(instances, 48 * TRAINSIZE, 48 * TESTSIZE);
      LearningExperimenter learner = main.new LearningExperimenter(
          t.getClassifier(), train, test, true);
      if (best != null) {
        if (best.mse > learner.mse) {
          best = learner;
        }
      } else {
        best = learner;
      }
      t.setMse(learner.mse);
      String toLog = LOGMAP.get(t.getClassifier().getClass()).getInfo(t);
      logger.info(toLog);
      System.gc();
    }
    System.out.println("DONE: log file saved to: " + LOGPATH + fileName);
    // best.plot(best.toList(train), best.toList(test), best.lpred);
  }

  private static LearningTestCaseMultiLayerPerceptron[] generateMultilayerPerceptronTestCases(
      Instances train, Instances test, int numtests) {
    LearningTestCaseMultiLayerPerceptron[] tests = new LearningTestCaseMultiLayerPerceptron[numtests];
    for (int i = 1; i <= numtests; i++) {
      tests[i - 1] = new LearningTestCaseMultiLayerPerceptron(train, test,
          null, null, i * 1000, null);
    }
    return tests;
  }

  private static void logMetaData(String fileName, Integer fileSize,
      Integer trainSize, Integer testSize) {
    StringBuilder builder = new StringBuilder();
    builder.append("FILE LOADED: " + fileName + " SIZE: " + fileSize
        + System.lineSeparator());
    builder.append("TRAIN: " + trainSize + " TEST: " + testSize
        + System.lineSeparator());
    logger.info(builder.toString());
  }

  /**
   * Class that does the forecast testing. Currently only {@link WekaForecaster}
   * are supported.
   * 
   * @author Benedict Wilkins
   *
   */
  public class LearningExperimenter {

    private Double mse = -1.0;
    private List<Double> lpred;

    /**
     * Constructor.
     * 
     * @param classifier
     *          to test
     * @param train
     *          data
     * @param test
     *          data
     * @param plot
     *          true to plot the result, false otherwise
     */
    public LearningExperimenter(Classifier classifier, Instances train,
        Instances test, boolean plot) {
      try {
        // split instances into train and test
        WekaForecaster forecaster = constructForecaster(classifier, train);
        List<List<NumericPrediction>> predictions = forecaster.forecast(
            test.size(), System.out);
        // System.out.println(predictions.size());
        List<Double> ltest = toList(test);
        lpred = toList(predictions);
        if (plot) {
          List<Double> ltrain = toList(train);
          plot(ltrain, ltest, lpred);
        }
        mse = meanSquaredError(ltest, lpred);
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
      forecaster.setFieldsToForecast("EnergyUsage");
      forecaster.setBaseForecaster(classifier);
      forecaster.buildForecaster(instances, System.out);
      System.out.println("FORECASTER BUILT");
      forecaster.primeForecaster(instances);
      System.out.println("FORECASER PRIMED");
      return forecaster;
    }

    private Double meanSquaredError(List<Double> test, List<Double> predicted) {
      Double[] sub = MathUtilities.subtract(
          predicted.toArray(new Double[predicted.size()]),
          test.toArray(new Double[test.size()]));
      return MathUtilities.sum(MathUtilities.product(sub, sub)) / test.size();
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

  public interface InfoLogFormat {
    public String getInfo(LearningTestCase testCase);
  }
}
