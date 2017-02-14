package machinelearning.test;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

public class LearningTestCaseMultiLayerPerceptron extends
    AbstractLearningTestCase {

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
