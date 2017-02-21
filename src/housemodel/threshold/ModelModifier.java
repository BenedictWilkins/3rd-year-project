package housemodel.threshold;

import agent.communication.NetworkObject;
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
}
