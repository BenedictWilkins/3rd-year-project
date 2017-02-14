package machinelearning.test;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * A test case for a given learning algorithm should implement this Interface.
 * 
 * @author Benedict Wilkins
 *
 */
public interface LearningTestCase {

  /**
   * Getter for the {@link Classifier} under test.
   * 
   * @return the {@link Classifier}
   */
  public Classifier getClassifier();

  /**
   * Getter for the training set used for training.
   * 
   * @return training set
   */
  public Instances getTrainingSet();

  /**
   * Getter for the test set used in evaluation.
   * 
   * @return test set
   */
  public Instances getTestSet();

}
