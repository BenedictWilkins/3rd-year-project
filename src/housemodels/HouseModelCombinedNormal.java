package housemodels;

import utilities.CombinedNormalDistribution;
import utilities.DateTime;
import utilities.NormalDistribution;
import graph.DataFitter;
import housemodel.threshold.ModelModifier;

public abstract class HouseModelCombinedNormal implements
    HouseModel {

  /**
   * The values supplied to these housing groups come from fitting 2 normal
   * curves to the mean KWH per half hour during 1 day for each group. This was
   * done using {@link DataFitter}.
   */

  private CombinedNormalDistribution function;
  private Double[] data;

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
    // TODO Auto-generated method stub
  }
}
