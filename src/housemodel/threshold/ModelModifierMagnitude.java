package housemodel.threshold;

import housemodels.HouseModel;
import utilities.MathFunctionClass;

/**
 * An abstract implementation of {@link ModelModifier} that will shrink the
 * function used in a {@link HouseModel} by some amount. Extend with a concrete
 * class to alter a given {@link MathFunctionClass}.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class ModelModifierMagnitude implements ModelModifier {
  
  private static final long serialVersionUID = 1L;

}
