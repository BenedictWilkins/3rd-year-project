package test;

import graph.GeneralXYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.ui.ApplicationFrame;

import utilities.MathUtilities;

import java.awt.Dimension;

public class TestMultipleDatasetPlot {

  /**
   * Test entry point.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    final MultipleDataSetPlot demo = new MultipleDataSetPlot("Multi-plot");
    demo.testNormalPlot();
    demo.pack();
    demo.setVisible(true);
  }

  public static class MultipleDataSetPlot extends ApplicationFrame {

    private int index = 0;
    private XYPlot plot = null;

    private static final long serialVersionUID = 1L;

    public MultipleDataSetPlot(String title) {
      super(title);

    }

    private void testNormalPlot() {
      final JFreeChart chart = ChartFactory.createXYLineChart("Multi-Demo",
          "X", "Y", createNormalDataset(5.0, 1.0));
      final ChartPanel panel = new ChartPanel(chart);
      panel.setPreferredSize(new Dimension(500, 270));

      this.plot = chart.getXYPlot();
      this.plot.setDataset(this.index, createNormalDataset(3.0, 1.0));
      this.plot.setRenderer(this.index, new StandardXYItemRenderer());

      this.setContentPane(panel);
    }

    private GeneralXYDataset createNormalDataset(Double mean, Double sd) {
      Double[] xpos = MathUtilities.generateSequence(0.0, 10.0, 100);
      Double[] ypos = MathUtilities.NormalProbabilityDistributionFunction(xpos, mean,
          sd);
      GeneralXYDataset data = new GeneralXYDataset();
      data.addSeries(xpos, ypos, "Series: " + index);
      index++;
      return data;
    }
  }

}
