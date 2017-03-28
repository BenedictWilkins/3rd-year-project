package housemodel.threshold;

import machinelearning.agent.DataFrame;

import utilities.MathUtilities;

import java.util.List;

/**
 * A concrete implementation of a {@link Threshold}. Whose threshold is a single
 * value.
 * 
 * @author Benedict Wilkins
 *
 */
public class MaximumThreshold implements Threshold {

  private Double threshold = null;
  private Integer columnIndex = null;

  /**
   * Constructor.
   * 
   * @param threshold
   *          that will be used
   * @param columnIndex
   *          of the column to check
   * @param columnType
   *          of the column to check
   */
  public MaximumThreshold(Double threshold, Integer columnIndex) {
    this.threshold = threshold;
    this.columnIndex = columnIndex;
  }

  /**
   * Check the threshold represented by this {@link Threshold}. Checking is as
   * follows: The max value from the column given by the columnIndex argument
   * supplied in this classes constructor will be taken and compared to the
   * threshold value. If it is greater, the threshold has been met, if not; it
   * has not. The type of the column is assumed to be {@link Double}.
   * 
   */
  @Override
  public boolean checkThreshold(DataFrame data) {
    List<Double> check = data.getColumn(columnIndex, Double.class);
    return this.threshold < MathUtilities.max(check.toArray(new Double[] {}));
  }
}
