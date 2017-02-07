package test;

import graph.SeriesPlot;

public class TestMultipleDatasetPlot {

  /**
   * Test entry point.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    final SeriesPlot demo = new SeriesPlot("Main", createRandomDataset(20),
        "TEST MULTIPLOT");
    demo.addSeries("Alt", createRandomDataset(10), 19);
  }

  private static Double[] createRandomDataset(int length) {
    Double[] data = new Double[length];
    for (int i = 0; i < length; i++) {
      data[i] = Math.random();
    }
    return data;
  }

}
