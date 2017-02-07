package graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.Color;
import java.awt.Dimension;

/**
 * A general plot for series data.
 * 
 * @author Benedict Wilkins
 *
 */
public class SeriesPlot extends ApplicationFrame {

  private static final long serialVersionUID = 1L;

  private XYPlot plot;
  private int index;

  /**
   * Constructor.
   * 
   * @param data
   *          to plot
   * @param title
   *          of the plot
   */
  public SeriesPlot(String name, Double[] data, String title) {
    super(title);
    final XYDataset dataset = createSeries(name, data, 0);
    final JFreeChart chart = ChartFactory.createXYLineChart("Test", "Time",
        "Value", dataset);
    final ChartPanel panel = new ChartPanel(chart);
    panel.setPreferredSize(new Dimension(560, 270));
    this.plot = chart.getXYPlot();
    this.plot.setBackgroundPaint(Color.WHITE);
    setContentPane(panel);
    this.setVisible(true);
    this.pack();
  }

  public void addSeries(String name, Double[] data, int start) {
    this.index++;
    XYDataset newseries = createSeries(name, data, start);
    this.plot.setDataset(this.index, newseries);
    this.plot.setRenderer(this.index, new StandardXYItemRenderer());
  }

  public XYDataset createSeries(String name, Double[] data, int start) {
    final XYSeries series = new XYSeries(name);
    for (int i = start; i < start + data.length; i++) {
      series.add(i, data[i - start]);
    }
    return new XYSeriesCollection(series);
  }
}
