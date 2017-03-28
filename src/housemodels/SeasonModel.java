package housemodels;

import utilities.DateTime;
import utilities.MathematicalFunction;
import utilities.NormalDistribution;

/**
 * A class similar to {@link HouseModel} that represents the yearly trend
 * (rather than daily). <br>
 * Implements {@link MathematicalFunction}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SeasonModel implements MathematicalFunction<Double> {

  protected static final Double[] PARAMCONFIG = new Double[] { 210.0, 60.0,
      -24.0, 0.3 };
  protected static final Class<NormalDistribution> FUNCTIONCONFIG = NormalDistribution.class;

  private MathematicalFunction<Double> function;
  private Double[] data;
  private Double addition;
  private Double scale;

  /**
   * Constructor. Preset instance of this class based on the
   * {@link SeasonModel#PARAMCONFIG} and {@link SeasonModel#FUNCTIONCONFIG}
   * static fields.
   */
  public SeasonModel() {
    try {
      NormalDistribution function = FUNCTIONCONFIG.newInstance();
      function.setMean(PARAMCONFIG[0]);
      function.setStandardDeviation(PARAMCONFIG[1]);
      this.function = function;
      this.scale = PARAMCONFIG[2];
      this.addition = PARAMCONFIG[3];
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * Constructor.
   * 
   * @param function
   *          that will produce the modifier values
   * @param addition
   *          to the modifier values
   * @param scale
   *          the modifier values
   */
  public SeasonModel(MathematicalFunction<Double> function, Double addition,
      Double scale) {
    this.function = function;
    this.addition = addition;
    this.scale = scale;
  }

  /**
   * Generates a set of y values according to this {@link SeasonModel}s
   * function, addition and scale.
   * 
   * @param xvalues
   *          to compute from
   * @return computed y values
   */
  @Override
  public Double[] compute(Double[] xvalues) {
    Double[] yvalues = new Double[xvalues.length];
    Number[] fvalues = function.compute(xvalues);
    for (int i = 0; i < xvalues.length; i++) {
      yvalues[i] = (scale * (double) fvalues[i]) + addition;
    }
    data = yvalues;
    return data;
  }

  /**
   * Get the value that will be used when adding yearly seasonality to some data
   * (each time step has its own value).
   * 
   * @param dateTime
   *          to get yearly seasonal modifying value
   * @return the modifying value
   */
  public Double getModiferValue(DateTime dateTime) {
    Integer date = dateTime.getYearDay() - 1;
    if (date < data.length) {
      return data[date];
    } else {
      System.err.println("INVALID DATE: " + this.getClass().getSimpleName()
          + " CANNOT HANDLE DATE VALUE GIVEN: " + dateTime);
      return null;
    }
  }
}
