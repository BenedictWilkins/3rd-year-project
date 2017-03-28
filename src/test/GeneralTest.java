package test;

import graph.SeriesPlot;
import utilities.MathUtilities;

/**
 * General test class, constantly altered to test different aspects of the
 * system.
 * 
 * @author Benedict Wilkins
 *
 */
public class GeneralTest {

  /**
   * Entry point for general test.
   * 
   * @param args
   *          none
   * @throws InterruptedException
   *           -
   */
  public static void main(String[] args) throws InterruptedException {
    SeriesPlot plot = new SeriesPlot("T1", MathUtilities.generateSequence(1.0,
        10.0, 10), "TEST SERIES");
    plot.addSeries("T2", MathUtilities.generateSequence(2.0, 5.0, 10), 5);

    for (int i = 0; i < 10; i++) {
      plot.addToSeries("T1", new Double[] { Math.random() * 10 });
      Thread.sleep(200);
    }

  }
}
