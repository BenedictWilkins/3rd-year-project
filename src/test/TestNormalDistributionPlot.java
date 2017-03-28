package test;

import graph.GeneralXYDataset;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

import utilities.MathUtilities;

import java.awt.Dimension;

/**
 * Test class for the plotting a normal distribution using the
 * {@link MathUtilities} class.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestNormalDistributionPlot {

  /**
   * Test entry point.
   * 
   * @param args
   *          none.
   */
  public static void main(String[] args) {
    Double[] xpos = MathUtilities.generateSequence(0.0, 10.0, 100);
    // REFACTOR TODO
    // Double[] ypos = MathUtilities.normalProbabilityDistributionFunction(xpos,
    // 5.0, 1.0);
    for (int i = 0; i < xpos.length; i++) {
      // System.out.println(xpos[i] + "," + ypos[i]);
    }

    GeneralXYDataset data = new GeneralXYDataset();
    // data.addSeries(xpos, ypos, "NormalSeries");
    final ApplicationFrame frame = new ApplicationFrame("Test Normal");
    final JFreeChart chart = ChartFactory.createXYLineChart("Normal", "X", "Y",
        data);
    final ChartPanel panel = new ChartPanel(chart);
    panel.setPreferredSize(new Dimension(500, 270));

    frame.setContentPane(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
