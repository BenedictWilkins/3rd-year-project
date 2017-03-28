package housemodels;

import utilities.MathUtilities;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * Factory class for {@link House}. Capable of creating each of the 4 house
 * types using the defined {@link CreateMethod}s.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseFactory {

  private static final HouseFactory INSTANCE = new HouseFactory();
  private static final HouseModelFactory MODELFACTORY = HouseModelFactory
      .getFactory();

  private static final SeasonModel SEASONMODIFIER = new SeasonModel();

  static {
    SEASONMODIFIER.compute(MathUtilities.generateSequence(0.0,
        HalfHourClock.YEARLENGTH - 1.0, HalfHourClock.YEARLENGTH));
  }

  /**
   * A Marker {@link Annotation} that indicates which {@link Method} in this
   * Factory are used to create different {@link House}s. Any {@link Method}
   * using this {@link Annotation} must have the {@link House} return type.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface CreateMethod {
  }

  private HouseFactory() {
  }

  @CreateMethod
  public House createAffluentHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalAffluent(),
        SEASONMODIFIER, error);
  }

  @CreateMethod
  public House createComfortableHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalComfortable(),
        SEASONMODIFIER, error);
  }

  @CreateMethod
  public House createAdversityHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalAdversity(),
        SEASONMODIFIER, error);
  }

  @CreateMethod
  public House createAcornUHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalAcornU(),
        SEASONMODIFIER, error);
  }

  public static HouseFactory getFactory() {
    return INSTANCE;
  }

}
