package utilities;

/**
 * Any class that represents a function should implement this interface.
 * 
 */
public interface MathFunctionClass<I extends Number> {

  public I[] compute(I[] args);

}
