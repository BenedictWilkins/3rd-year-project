package machinelearning.test;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Abstract implementation of {@link LearningTestCase} specifically for Weka
 * {@link Classifier}s.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class AbstractWekaLearningTestCase implements LearningTestCase {

  private Classifier classifier;
  private Instances train;
  private Instances test;

  private Double mse = -1.0;

  /**
   * Constructor.
   * 
   * @param classifier
   *          to test
   * @param train
   *          to train on
   * @param test
   *          to test performance with
   */
  protected AbstractWekaLearningTestCase(Classifier classifier,
      Instances train, Instances test) {
    this.test = test;
    this.train = train;
    this.classifier = classifier;
  }

  @Override
  public Classifier getClassifier() {
    return this.classifier;
  }

  @Override
  public Instances getTestSet() {
    return train;
  }

  @Override
  public Instances getTrainingSet() {
    return test;
  }

  public Double getMse() {
    return mse;
  }

  public void setMse(Double mse) {
    this.mse = mse;
  }

  @Override
  public String toString() {
    return "TEST CASE: " + this.getClassifier().getClass().getSimpleName()
        + "{" + train.size() + "," + test.size() + "}";
  }
}
