package test;

import static org.junit.Assert.*;
import agent.communication.SmartMeterReadingNetworkObject;
import machinelearning.agent.DataFrameRow;
import machinelearning.agent.DataFrameRowReading;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import housemodel.combination.AdditiveCombinator;
import housemodel.combination.ReadingCombinator;

public class TestReadingCombinator {

  @Test
  public void test() {
    ReadingCombinator com = new ReadingCombinator(new AdditiveCombinator());
    Set<SmartMeterReadingNetworkObject> nos = new HashSet<>();
    for (int i = 0; i < 3; i++) {
      List<DataFrameRow> readings = new ArrayList<>();
      for (int j = 1; j < 4; j++) {
        readings.add(new DataFrameRowReading("TIME:"+j, new Double(j)));
      }
      nos.add(new SmartMeterReadingNetworkObject(readings, "ID" + i));
    }
    System.out.println(Arrays.toString(nos.toArray()));
    System.out.println(Arrays.toString(com.combine(nos).toArray()));
    
    
    
  }

}
