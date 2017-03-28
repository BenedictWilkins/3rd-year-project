package test;

import graph.DataReader;
import graph.SeriesPlot;
import machinelearning.agent.DataFrame;

/**
 * Test class for {@link DataReader}.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestDataReader {

  private static String gmacornu = "./res/gmAcorn-u.txt";

  /**
   * Entry point for testing a {@link DataReader}.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    DataReader dr = new DataReader();
    DataFrame frame = dr.readFile(gmacornu, " ", true,
        new Class<?>[] { Double.class });
    Double[] data = (Double[]) frame.getColumn(1, Double.class).toArray();
    System.out.println(data.length);
    SeriesPlot plot = new SeriesPlot("", data, "");
    plot.pack();
    plot.setVisible(true);
  }
}
