package housemodel.threshold;

import utilities.MathematicalFunction;

/**
 * Should be implemented by any class whos {@link MathematicalFunction} is
 * modifiable.
 * 
 * @see
 * 
 * @author Benedict Wilkins
 *
 */
public interface Modifiable {

  public void modifyModel(ModelModifier modifier);

}
