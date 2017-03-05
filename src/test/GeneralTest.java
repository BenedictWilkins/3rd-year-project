package test;

import graph.SeriesPlot;
import agent.general.AgentStructure;
import agent.general.AgentType;

public class GeneralTest {

  public static void main(String[] args) throws InterruptedException {
    SeriesPlot plot = new SeriesPlot("test", new Double[] {}, "TEST");
    while (true) {
      Thread.sleep(200);
      plot.addToSeries("test", new Double[] { Math.random() });

    }
  }
}
