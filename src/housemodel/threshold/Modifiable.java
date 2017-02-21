package housemodel.threshold;

import utilities.MathFunctionClass;

/**
 * Should be implemented by any class whos {@link MathFunctionClass} is
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
