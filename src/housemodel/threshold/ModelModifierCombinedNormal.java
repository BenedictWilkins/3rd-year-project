package housemodel.threshold;

import housemodels.HouseModel;
import utilities.CombinedNormalDistribution;
import utilities.MathematicalFunction;

/**
 * A class that is capable of modifying a {@link CombinedNormalDistribution}
 * function should and must implement this Interface. A {@link HouseModel} will
 * only accept a modifier if it implements this Interface.
 * 
 * @author Benedict Wilkins
 *
 */
public interface ModelModifierCombinedNormal {

  /**
   * This function is specifically for modifying
   * {@link CombinedNormalDistribution}s. See: {@link ModelModifier}
   * {@link #modify(MathematicalFunction)}.
   * 
   * @param function
   *          to modify (should be a {@link CombinedNormalDistribution})
   * @return the modied {@link CombinedNormalDistribution}
   */
  public CombinedNormalDistribution modify(MathematicalFunction<?> function);

}
