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
import java.util.HashMap;
import java.util.Map;

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
  private Map<String, XYSeries> series;
  private double max, min;
  private XYSeries baseSeries = null;
  private static final int FRAMEWIDTH = Integer.MAX_VALUE;

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
    series = new HashMap<>();
    final XYDataset dataset = createSeries(name, data, 0);
    final JFreeChart chart = ChartFactory.createXYLineChart(title, "Time",
        "Value", dataset);
    final ChartPanel panel = new ChartPanel(chart);
    panel.setPreferredSize(new Dimension(560, 270));
    this.plot = chart.getXYPlot();
    this.plot.setBackgroundPaint(Color.WHITE);

    NumberAxis range = (NumberAxis) this.plot.getRangeAxis();
    if (data.length > 0) {
      NumberAxis domain = (NumberAxis) this.plot.getDomainAxis();
      domain.setRange(0, data.length);
      range.setRange((min = MathUtilities.min(data)),
          (max = MathUtilities.max(data)));
    }
    baseSeries = this.series.get(name);
    setContentPane(panel);
    this.setVisible(true);
    this.pack();
  }

  public void setAddOffset(int offset) {

  }

  public void addToSeries(String name, Double[] add) {
    XYSeries series = this.series.get(name);
    int size = series.getItemCount();
    for (int i = 0; i < add.length; i++) {
      series.add(new XYDataItem(new Double(size + i), add[i]));
    }
  }

  public void updateAxis(String seriesName) {
    XYSeries series = this.series.get(seriesName);
    NumberAxis range = (NumberAxis) this.plot.getRangeAxis();
    range.setRange(series.getMinY(), series.getMaxY());
    NumberAxis domain = (NumberAxis) this.plot.getDomainAxis();
    domain.setRange(Math.max(0, series.getItemCount() - FRAMEWIDTH),
        series.getItemCount());
  }

  public void addSeries(String name, Double[] data, int start) {
    this.index++;
    XYDataset newseries = createSeries(name, data, start);
    this.plot.setDataset(this.index, newseries);
    StandardXYItemRenderer render = new StandardXYItemRenderer();
    render.setSeriesPaint(0, Color.BLUE);
    this.plot.setRenderer(this.index, render);

  }

  public XYDataset createSeries(String name, Double[] data, int start) {
    final XYSeries series = new XYSeries(name);
    for (int i = start; i < start + data.length; i++) {
      series.add(i, data[i - start]);
    }
    this.series.put(name, series);

    return new XYSeriesCollection(series);
  }
}
