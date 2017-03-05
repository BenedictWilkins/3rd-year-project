package housemodel.threshold;

import agent.communication.NetworkObject;
import utilities.MathematicalFunction;
import housemodels.HouseModel;

/**
 * A concrete implementation of {@link ModelModifier} should define how a
 * {@link HouseModel} is modified after a {@link Threshold} has been met, which
 * {@link ModelModifier} to be used will be up to the top level agent who is
 * managing its group of subordinates.
 * 
 * @see ModelModifierMagnitude
 * @see ModelModifierShift
 * 
 * @author Benedict Wilkins
 *
 */
public interface ModelModifier extends NetworkObject {

  /**
   * The method used to modify a function. In general no guarantee can be made
   * that no alteration of the input function will occur.
   * 
   * @param function
   *          to modify
   * @return the modified function
   */
  public MathematicalFunction<?> modify(MathematicalFunction<?> function);

}
