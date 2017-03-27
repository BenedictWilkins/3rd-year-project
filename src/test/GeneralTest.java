package test;

import utilities.MathUtilities;
import graph.SeriesPlot;

public class GeneralTest {

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
