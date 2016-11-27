package housemodels;

import java.util.Random;

/**
 * An instance of the Factory design pattern. This factory creates
 * {@link HouseModel}s.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseModelFactory {

  private static HouseModelFactory factory = new HouseModelFactory();
  private Random random = new Random();

  private HouseModelFactory() {
  }

  public CombinedNormalHouseModel createAffluentCombinedNormalHouseModel(
      Double error) {
    return createCombinedNormalHouseModel(HouseTypeCombinedNormal.AFFLUENT,
        error);
  }

  public CombinedNormalHouseModel createAdversityCombinedNormalHouseModel(
      Double error) {
    return createCombinedNormalHouseModel(HouseTypeCombinedNormal.ADVERSITY,
        error);
  }

  public CombinedNormalHouseModel createComfortableCombinedNormalHouseModel(
      Double error) {
    return createCombinedNormalHouseModel(HouseTypeCombinedNormal.COMFORTABLE,
        error);
  }

  public CombinedNormalHouseModel createAcornuCombinedNormalHouseModel(
      Double error) {
    return createCombinedNormalHouseModel(HouseTypeCombinedNormal.ACORNU, error);
  }

  private CombinedNormalHouseModel createCombinedNormalHouseModel(
      HouseTypeCombinedNormal type, Double error) {
    return new CombinedNormalHouseModel(type, error);
  }

  public ConstantHouseModel createAffluentConstantHouseModel() {
    return createConstantHouseModel(HouseTypeConstant.AFFLUENT);
  }

  public ConstantHouseModel createAdversityConstantHouseModel() {
    return createConstantHouseModel(HouseTypeConstant.ADVERSITY);
  }

  public ConstantHouseModel createComfortableConstantHouseModel() {
    return createConstantHouseModel(HouseTypeConstant.COMFORTABLE);
  }

  private ConstantHouseModel createConstantHouseModel(HouseTypeConstant type) {
    return new ConstantHouseModel(1 + type.getMean() + random.nextGaussian()
        * type.getStandardDeviation());
  }

  public static HouseModelFactory getFactory() {
    return factory;
  }
}
