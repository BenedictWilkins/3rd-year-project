package housemodel.combination;

import java.util.Collection;

/**
 * An interface that should be implemented by any class that wishes to 'combine'
 * a {@link Collection} of {@link Object}s into a single {@link Object}. This
 * 'combine' should be performed in the {@link #combine(Collection)} method.
 *
 * @see {@link AdditiveCombinator}, {@link ReadingCombinator}
 * 
 * @author Benedict Wilkins
 *
 * @param <C>
 *          the type of the elements in the {@link Collection} to be combined
 *          using the{@link #combine(Collection)} method.
 * @param <R>
 *          the type of the {@link Object} to be returned by the
 *          {@link #combine(Collection)} method.
 */
public interface Combinator<C, R> {

  /**
   * The function that performs the combination of each element in the data
   * {@link Collection}.
   * 
   * @param data
   *          to combine
   * @return result fo combination
   */
  public R combine(Collection<C> data);
}
