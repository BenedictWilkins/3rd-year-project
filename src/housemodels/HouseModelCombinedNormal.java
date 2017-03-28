package housemodels;

import demo.DataFitter;
import housemodel.threshold.ModelModifier;
import housemodel.threshold.ModelModifierCombinedNormal;

import utilities.CombinedNormalDistribution;
import utilities.DateTime;
import utilities.NormalDistribution;

/**
 * Abstract class that represents a {@link HouseModel} that uses a
 * {@link CombinedNormalDistribution} as its underlying model. There are four
 * house types: {@link HouseModelCombinedNormalAcornU AcornU},
 * {@link HouseModelCombinedNormalAdversity Adversity},
 * {@link HouseModelCombinedNormalAffluent Affluent} and
 * {@link HouseModelCombinedNormalComfortable Comfortable}. The parameters that
 * define these types come from fitting 2 normal curves to the mean KWH per half
 * hour during 1 day. This was done using {@link DataFitter}.
 */
public abstract class HouseModelCombinedNormal implements HouseModel {

  private CombinedNormalDistribution function;
  private Double[] data;

  /**
   * Constructor.
   * 
   * @param n1
   *          first {@link NormalDistribution}
   * @param n2
   *          second {@link NormalDistribution}
   */
  protected HouseModelCombinedNormal(NormalDistribution n1,
      NormalDistribution n2) {
    function = new CombinedNormalDistribution(n1, n2, true);
  }

  @Override
  public Double[] compute(Double[] args) {
    data = function.compute(args);
    return data;
  }

  @Override
  public Double getReading(DateTime dateTime) {
    // look at the time, check the model
    Integer time = HalfHourClock.getInstance().getHalfHourInDay(dateTime);
    if (time < data.length) {
      return data[time];
    } else {
      System.err.println("INVALID TIME: " + this.getClass().getSimpleName()
          + " CANNOT HANDLE TIME VALUE GIVEN: " + dateTime);
      return null;
    }
  }

  @Override
  public void modifyModel(ModelModifier modifier) {
    // System.out.println("MODIFYING MODEL WITH: " + modifier);
    if (ModelModifierCombinedNormal.class.isAssignableFrom(modifier.getClass())) {
      // System.out.println("BEFORE: " + function.getNormal1().getScale() + ","
      // + function.getNormal2().getScale());
      this.function = ((ModelModifierCombinedNormal) modifier)
          .modify(this.function);
      // System.out.println("AFTER: " + function.getNormal1().getScale() + ","
      // + function.getNormal2().getScale());
      HouseModelFactory.getFactory().dayLengthCompute(this);
    } else {
      System.err.println("INVALID MODIFIER: " + modifier);
    }

  }
}
