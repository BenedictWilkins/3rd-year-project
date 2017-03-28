package machinelearning.test;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

/**
 * A concrete extension of {@link AbstractWekaLearningTestCase} specifically for
 * the {@link MultilayerPerceptron} classifier.
 * 
 * @author Benedict Wilkins
 *
 */
public class LearningTestCaseMultiLayerPerceptron extends
    AbstractWekaLearningTestCase {

  /**
   * Constructor. See {@link MultilayerPerceptron} for details on parameters.
   * 
   * @param train
   *          to train on
   * @param test
   *          to test performance with
   * @param learningRate
   *          {@link MultilayerPerceptron} parameter
   * @param momentum
   *          {@link MultilayerPerceptron} parameter
   * @param epochs
   *          {@link MultilayerPerceptron} parameter
   * @param validation
   *          {@link MultilayerPerceptron} parameter
   */
  public LearningTestCaseMultiLayerPerceptron(Instances train, Instances test,
      Double learningRate, Double momentum, Integer epochs, Integer validation) {
    super(new MultilayerPerceptron(), train, test);
    if (learningRate != null) {
      this.getClassifier().setLearningRate(learningRate);
    }
    if (momentum != null) {
      this.getClassifier().setMomentum(momentum);
    }
    if (epochs != null) {
      this.getClassifier().setTrainingTime(epochs);
    }
    if (validation != null) {
      this.getClassifier().setValidationThreshold(validation);
    }
  }

  public Double getLearningRate() {
    return this.getClassifier().getLearningRate();
  }

  public Double getMomentum() {
    return this.getClassifier().getMomentum();
  }

  public Integer getEpochs() {
    return this.getClassifier().getTrainingTime();
  }

  public Integer getValidationSetSize() {
    return this.getClassifier().getValidationSetSize();
  }

  @Override
  public MultilayerPerceptron getClassifier() {
    return (MultilayerPerceptron) super.getClassifier();
  }

  @Override
  public String toString() {
    return super.toString() + " LR: " + this.getLearningRate() + " M: "
        + this.getMomentum() + " EP: " + this.getEpochs() + " VS: "
        + this.getValidationSetSize();
  }

}
