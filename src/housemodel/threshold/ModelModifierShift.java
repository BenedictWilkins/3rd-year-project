package housemodel.threshold;

import housemodels.HouseModel;

import utilities.MathematicalFunction;

/**
 * An abstract implementation of {@link ModelModifier} that will shift the
 * function used in a {@link HouseModel} left or right by some amount. Extend
 * with a concrete class to alter a given {@link MathematicalFunction}.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class ModelModifierShift implements ModelModifier {

  private static final long serialVersionUID = 1L;

}
