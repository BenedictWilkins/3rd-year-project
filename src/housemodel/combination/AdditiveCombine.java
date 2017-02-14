package housemodel.combination;

import utilities.MathUtilities;

import java.util.List;

public class AdditiveCombine implements Combine {
  @Override
  public Double combine(List<Double> readings) {
    return MathUtilities.sum(readings.toArray(new Double[readings.size()]));
  }
}
