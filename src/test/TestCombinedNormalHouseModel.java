package test;

import graph.SeriesPlot;

import housemodels.CombinedNormalHouseModel;
import housemodels.HouseTypeCombinedNormal;

import org.junit.Test;

import utilities.DateTime;
import utilities.DateTimeInterface;


public class TestCombinedNormalHouseModel {

  @Test
  public void test() {
    CombinedNormalHouseModel model = new CombinedNormalHouseModel(
        HouseTypeCombinedNormal.ACORNU, 1.0);
    Double[] rs = new Double[48];
    for (int i = 0; i < 48; i++) {
      rs[i] = model.getReading((DateTimeInterface) new DateTime(i, 0));
      System.out.println(rs[i]);
    }
    new SeriesPlot(rs, "TestCombinedNormalHouseModel");
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
