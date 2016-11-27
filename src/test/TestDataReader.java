package test;

import graph.DataFrame;
import graph.DataReader;
import graph.SeriesPlot;

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
    DataFrame frame = dr.readFile(gmacornu, " ", true);
    Double[] data = frame.getColumn(1);
    System.out.println(data.length);
    SeriesPlot plot = new SeriesPlot(data, "");
    plot.pack();
    plot.setVisible(true);
  }
}
