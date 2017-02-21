package housemodel.combination;

import utilities.MathUtilities;

import java.util.Collection;

/**
 * A basic {@link Combinator} that will combine a {@link Collection} of
 * {@link Double}s by computing and returning their sum.
 * 
 * @see {@link MathUtilities#sum(Double[])}.
 * 
 * @author Benedict Wilkins
 *
 */
public class AdditiveCombinator implements Combinator<Double, Double> {

  @Override
  public Double combine(Collection<Double> readings) {
    return MathUtilities.sum(readings.toArray(new Double[] {}));
  }
}
