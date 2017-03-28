package housemodel.threshold;

import utilities.MathematicalFunction;

/**
 * Should be implemented by any class whose {@link MathematicalFunction} is
 * modifiable.
 * 
 * 
 * @author Benedict Wilkins
 *
 */
public interface Modifiable {

  public void modifyModel(ModelModifier modifier);

}
