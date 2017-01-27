package housemodels;

import utilities.DateTime;
import utilities.MathUtilities;

public class HouseFactory {

  private static final HouseFactory INSTANCE = new HouseFactory();
  private static final HouseModelFactory MODELFACTORY = HouseModelFactory
      .getFactory();

  private static final SeasonModifier SEASONMODIFIER = new SeasonModifier();

  static {
    SEASONMODIFIER.compute(MathUtilities.generateSequence(0.0,
        DateTime.YEARLENGTH - 1.0, DateTime.YEARLENGTH));
  }

  private HouseFactory() {
  }

  public House createAffluentHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalAffluent(),
        SEASONMODIFIER, error);
  }

  public House createComfortableHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalComfortable(),
        SEASONMODIFIER, error);
  }

  public House createAdversityHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalAdversity(),
        SEASONMODIFIER, error);
  }

  public House createAcornUHouse(Double error) {
    return new House(MODELFACTORY.createHouseModelCombinedNormalAcornU(),
        SEASONMODIFIER, error);
  }

  public static HouseFactory getFactory() {
    return INSTANCE;
  }

}
