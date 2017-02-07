package housemodels;

import utilities.CombinedNormalDistribution;
import utilities.DateTime;
import utilities.MathUtilities;

public class HouseModelFactory {

  private static final HouseModelFactory instance = new HouseModelFactory();

  
  
  
  
  
  /**
   * Creates a new AcornU house model based on
   * {@link CombinedNormalDistribution}. The house model has default 48
   * intervals (1 for each half hour in 24 hours).
   * 
   * @return created AcornU house model.
   */
  public HouseModelCombinedNormalAcornU createHouseModelCombinedNormalAcornU() {
    HouseModelCombinedNormalAcornU model = new HouseModelCombinedNormalAcornU();
    dayLengthCompute(model);
    return model;
  }

  /**
   * Creates a new Adversity house model based on
   * {@link CombinedNormalDistribution}. The house model has default 48
   * intervals (1 for each half hour in 24 hours).
   * 
   * @return created Adversity house model.
   */
  public HouseModelCombinedNormalAdversity createHouseModelCombinedNormalAdversity() {
    HouseModelCombinedNormalAdversity model = new HouseModelCombinedNormalAdversity();
    dayLengthCompute(model);
    return model;
  }

  /**
   * Creates a new Affluent house model based on
   * {@link CombinedNormalDistribution}. The house model has default 48
   * intervals (1 for each half hour in 24 hours).
   * 
   * @return created Affluent house model.
   */
  public HouseModelCombinedNormalAffluent createHouseModelCombinedNormalAffluent() {
    HouseModelCombinedNormalAffluent model = new HouseModelCombinedNormalAffluent();
    dayLengthCompute(model);
    return model;
  }

  /**
   * Creates a new Comfortable house model based on
   * {@link CombinedNormalDistribution}. The house model has default 48
   * intervals (1 for each half hour in 24 hours).
   * 
   * @return created Comfortable house model.
   */
  public HouseModelCombinedNormalComfortable createHouseModelCombinedNormalComfortable() {
    HouseModelCombinedNormalComfortable model = new HouseModelCombinedNormalComfortable();
    dayLengthCompute(model);
    return model;
  }

  private void dayLengthCompute(HouseModelCombinedNormal model) {
    model.compute(MathUtilities.generateSequence(0.0,
        HalfHourClock.DAYLENGTH - 1.0, HalfHourClock.DAYLENGTH));
  }

  public static HouseModelFactory getFactory() {
    return instance;
  }

}
