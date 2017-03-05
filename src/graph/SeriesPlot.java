package graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import utilities.MathUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;

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
  private XYSeries series;
  private double max, min;

  /**
   * Plots the given data as a series.
   * 
   * @param name
   *          of the series
   * @param data
   *          to plot
   * @param title
   *          of the plot
   */
  public SeriesPlot(String name, Double[] data, String title) {
    super(title);
    final XYDataset dataset = createSeries(name, data, 0);
    final JFreeChart chart = ChartFactory.createXYLineChart(title, "Time",
        "Value", dataset);
    final ChartPanel panel = new ChartPanel(chart);
    panel.setPreferredSize(new Dimension(560, 270));
    this.plot = chart.getXYPlot();
    this.plot.setBackgroundPaint(Color.WHITE);

    if (data.length > 0) {
      NumberAxis domain = (NumberAxis) this.plot.getDomainAxis();
      domain.setRange(0, data.length);
      NumberAxis range = (NumberAxis) this.plot.getRangeAxis();
      range.setRange((min = MathUtilities.min(data)),
          (max = MathUtilities.max(data)));

    }

    setContentPane(panel);
    this.setVisible(true);
    this.pack();
  }

  public void addToSeries(String series, Double[] add) {
    int size = this.series.getItemCount();
    for (int i = 0; i < add.length; i++) {
      this.series.add(new XYDataItem(new Double(size + i), add[i]));
    }
    NumberAxis domain = (NumberAxis) this.plot.getDomainAxis();
    domain.setRange(0, size + add.length);
    NumberAxis range = (NumberAxis) this.plot.getRangeAxis();
    double min = MathUtilities.min(add);
    double max = MathUtilities.max(add);
    this.min = (min < this.min) ? min : this.min;
    this.max = (max > this.max) ? max : this.max;
    range.setRange(this.min, this.max);
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
    this.series = series;
    return new XYSeriesCollection(series);
  }
}
