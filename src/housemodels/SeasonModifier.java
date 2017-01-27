package housemodels;

import utilities.DateTimeInterface;
import utilities.MathFunctionClass;
import utilities.NormalDistribution;

public class SeasonModifier implements MathFunctionClass<Double> {

  protected static final Double[] PARAMCONFIG = new Double[] { 210.0, 60.0,
      -24.0, 0.3 };
  protected static final Class<NormalDistribution> FUNCTIONCONFIG = NormalDistribution.class;

  private MathFunctionClass<Double> function;
  private Double[] data;
  private Double addition;
  private Double scale;

  /**
   * Constructor. Preset instance of this class based on the PARAMCONFIG and
   * FUNCTIONCONFIG static fields
   */
  public SeasonModifier() {
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
  public SeasonModifier(MathFunctionClass<Double> function, Double addition,
      Double scale) {
    this.function = function;
    this.addition = addition;
    this.scale = scale;
  }

  /**
   * Generates a set of y values according to this {@link SeasonModifier}s
   * function, addition and scale. The values produced by this modifiers
   * function will be between 0.0 and 1.0.
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

  public Double getModiferValue(DateTimeInterface dateTime) {
    // look at the time, check the model
    Integer date = dateTime.getDate().intValue();
    if (date < data.length) {
      return data[date];
    } else {
      System.err.println("INVALID DATE: " + this.getClass().getSimpleName()
          + " CANNOT HANDLE DATE VALUE GIVEN: " + dateTime);
      return null;
    }
  }
}
