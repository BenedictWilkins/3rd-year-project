package utilities;

/**
 * A {@link MathematicalFunction} that is a normal (Gaussian) distribution.
 * 
 * @author Benedict Wilkins
 *
 */
public class NormalDistribution implements MathematicalFunction<Double> {

  public Double getScale() {
    return scale;
  }

  public void setScale(Double scale) {
    this.scale = scale;
  }

  public Double getAddition() {
    return addition;
  }

  public void setAddition(Double addition) {
    this.addition = addition;
  }

  private Double standardDeviation = 1.0;
  private Double mean = 0.0;

  private Double scale = 1.0;
  private Double addition = 0.0;

  private Pair<Double, Double> xlims = new Pair<>(Double.NEGATIVE_INFINITY,
      Double.POSITIVE_INFINITY);
  private Pair<Double, Double> ylims = new Pair<>(0.0, 1.0);

  /**
   * Constructor. A normal distribution with mean 0 and standard deviation 1.
   */
  public NormalDistribution() {
  }

  /**
   * Constructor. A normal distribution with given mean and standard deviation.
   * 
   * @param mean
   *          of this normal distribution
   * @param standardDeviation
   *          of this normal distribution
   */
  public NormalDistribution(Double mean, Double standardDeviation) {
    this.mean = mean;
    this.standardDeviation = standardDeviation;
  }

  /**
   * Constructor. A normal distribution with given mean, standard deviation,
   * scaled by scale and added constant addition on the Y axis. Y values are
   * scaled then the addition operation is applied.
   * 
   * @param mean
   *          of this normal distribution
   * @param standardDeviation
   *          of this normal distribution
   * @param scale
   *          of this normal distribution
   * @param addition
   *          of this normal distribution
   */
  public NormalDistribution(Double mean, Double standardDeviation,
      Double scale, Double addition) {
    this.mean = mean;
    this.standardDeviation = standardDeviation;
    this.addition = addition;
    this.scale = scale;
    this.ylims.setO1(this.ylims.getO1() * scale + addition);
    this.ylims.setO2(this.ylims.getO2() * scale + addition);
  }

  /**
   * Creates the corresponding normal distribution values from the given
   * arguments.
   * 
   * @param xvalues
   *          a collection of values whose corresponding normal values should be
   *          calculated
   * @return computed corresponding distribution values
   */
  @Override
  public Double[] compute(Double[] xvalues) {
    Double da = 2 * standardDeviation * standardDeviation;
    Double ca = 1 / Math.sqrt(Math.PI * da);
    Double[] yvals = new Double[xvalues.length];
    for (int i = 0; i < xvalues.length; i++) {
      yvals[i] = scale
          * (ca * Math.exp(-Math.pow((double) xvalues[i] - mean, 2) / da))
          + addition;

    }
    return yvals;
  }

  public Double getStandardDeviation() {
    return standardDeviation;
  }

  public void setStandardDeviation(Double standardDeviation) {
    this.standardDeviation = standardDeviation;
  }

  public Double getMean() {
    return mean;
  }

  public void setMean(Double mean) {
    this.mean = mean;
  }

  public Pair<Double, Double> getXlims() {
    return xlims;
  }

  public void setXlims(Pair<Double, Double> xlims) {
    this.xlims = xlims;
  }

  public Pair<Double, Double> getYlims() {
    return ylims;
  }

  public void setYlims(Pair<Double, Double> ylims) {
    this.ylims = ylims;
  }
}
