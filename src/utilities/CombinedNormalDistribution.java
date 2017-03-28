package utilities;

import java.util.Arrays;

/**
 * A {@link MathematicalFunction} consisting of two {@link NormalDistribution}s
 * combined together (may loop round boundaries or not).
 * 
 * @author Benedict Wilkins
 *
 */
public class CombinedNormalDistribution implements MathematicalFunction<Double> {

  private NormalDistribution normal1;
  private NormalDistribution normal2;
  private boolean looped;

  /**
   * Constructor. A function of two normal distributions, combined additively.
   * 
   * @param normal1
   *          one of the two normal distributions that make up this function
   * @param normal2
   *          one of the two normal distributions that make up this function
   */
  public CombinedNormalDistribution(NormalDistribution normal1,
      NormalDistribution normal2, boolean looped) {
    super();
    this.normal1 = normal1;
    this.normal2 = normal2;
    this.looped = looped;
  }

  @Override
  public Double[] compute(Double[] args) {
    return (looped) ? computeLoop(args) : computeNoLoop(args);
  }

  private Double[] computeNoLoop(Double[] args) {
    Double[] d1 = normal1.compute(args);
    Double[] d2 = normal2.compute(args);
    for (int i = 0; i < args.length; i++) {
      d1[i] += d2[i];
    }
    return d1;
  }

  private Double[] computeLoop(Double[] args) {
    // generate extra sequences
    double interval = args[1] - args[0];
    int half = args.length / 2;
    args = MathUtilities.generateSequence(args[0] - (half * interval),
        args[args.length - 1] + (half * interval), half * 4);
    Double[] com = new Double[args.length];
    Double[] d1 = new NormalDistribution(normal1.getMean(),
        normal1.getStandardDeviation(), normal1.getScale(), 0.0).compute(args);
    Double[] d2 = new NormalDistribution(normal2.getMean(),
        normal2.getStandardDeviation(), normal2.getScale(), 0.0).compute(args);

    for (int i = 0; i < com.length; i++) {
      com[i] = d1[i] + d2[i];
    }
    Double[] result = Arrays.copyOfRange(com, half, args.length - half);
    for (int j = 0; j < result.length / 2; j++) {
      result[j] = result[j] + com[half * 3 + j];
      result[half + j] = result[half + j] + com[j];
    }
    for (int j = 0; j < result.length; j++) {
      result[j] = result[j] + normal1.getAddition() + normal2.getAddition();
    }
    return result;
  }

  public NormalDistribution getNormal1() {
    return normal1;
  }

  public void setNormal1(NormalDistribution normal1) {
    this.normal1 = normal1;
  }

  public NormalDistribution getNormal2() {
    return normal2;
  }

  public void setNormal2(NormalDistribution normal2) {
    this.normal2 = normal2;
  }

  public boolean isLooped() {
    return this.looped;
  }
}
