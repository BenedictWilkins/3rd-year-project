package graph;

import housemodels.HouseTypeCombinedNormal;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.ui.ApplicationFrame;

import utilities.MathUtilities;

import java.awt.Color;
import java.awt.Dimension;

public class DataFitter {

  /**
   * Entry point for fitting data.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    DataFitter df = new DataFitter();
    df.fit(DataGroup.ACORNU);
    // df.fit(DataGroup.ADVERSITY);
    // df.fit(DataGroup.AFFLUENT);
    // df.fit(DataGroup.COMFORTABLE);
  }

  private final Double[] timeSequence = MathUtilities.generateSequence(0.0,
      47.0, 48);
  private XYPlot plot;
  private FitterFrame frame;
  private int index;

  /**
   * Fits a curve to a particular data set both specified by the group
   * parameter.
   * 
   * @param group
   *          to fit
   */
  public void fit(DataGroup group) {
    frame = new FitterFrame();
    JFreeChart chart = ChartFactory.createXYLineChart("Data Fitting", "X", "Y",
        null);
    ChartPanel panel = new ChartPanel(chart);
    panel.setPreferredSize(new Dimension(1000, 500));

    this.plot = chart.getXYPlot();
    this.plot.setDataset(this.index, createDataset(read(group).getColumn(1)));
    this.plot.setRenderer(this.index, new StandardXYItemRenderer());

    this.plot.setDataset(this.index,
        createCombinedLoopedNormalDataset(group.type));
    this.plot.setRenderer(this.index, new StandardXYItemRenderer());

    plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.red);
    plot.getRendererForDataset(plot.getDataset(1))
        .setSeriesPaint(0, Color.blue);
    plot.setBackgroundPaint(Color.WHITE);
    NumberAxis domain = (NumberAxis) plot.getDomainAxis();
    domain.setRange(0, 47);
    frame.setContentPane(panel);
    frame.pack();
    frame.setVisible(true);
  }

  private DataFrame read(DataGroup group) {
    DataReader dr = new DataReader();
    return dr.readFile(group.getPath(), " ", true);
  }

  private GeneralXYDataset createDataset(Double[] yval) {
    GeneralXYDataset data = new GeneralXYDataset();
    data.addSeries(timeSequence, yval, "Dataset");
    index++;
    return data;
  }

  private GeneralXYDataset createCombinedLoopedNormalDataset(
      HouseTypeCombinedNormal type) {
    GeneralXYDataset gd = new GeneralXYDataset();
    gd.addSeries(type.getData()[0], type.getData()[1], "CombinedNormals");
    return gd;
  }

  private class FitterFrame extends ApplicationFrame {
    private static final long serialVersionUID = 1L;

    public FitterFrame() {
      super("FitterFrame");
    }
  }

  private enum DataGroup {
    ACORNU("./res/gmAcorn-u.txt", HouseTypeCombinedNormal.ACORNU), AFFLUENT(
        "./res/gmAffluent.txt", HouseTypeCombinedNormal.AFFLUENT), COMFORTABLE(
        "./res/gmComfortable.txt", HouseTypeCombinedNormal.COMFORTABLE), ADVERSITY(
        "./res/gmAdversity.txt", HouseTypeCombinedNormal.ADVERSITY);
    private String path;
    private HouseTypeCombinedNormal type;

    private DataGroup(String path, HouseTypeCombinedNormal type) {
      this.path = path;
      this.type = type;
    }

    public String getPath() {
      return this.path;
    }
  }
}
