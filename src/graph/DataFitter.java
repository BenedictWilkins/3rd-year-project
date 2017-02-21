package graph;

import housemodels.HalfHourClock;
import housemodels.HouseModelCombinedNormalAcornU;
import housemodels.HouseModelCombinedNormalAdversity;
import housemodels.HouseModelCombinedNormalAffluent;
import housemodels.HouseModelCombinedNormalComfortable;
import housemodels.SeasonModel;
import machinelearning.agent.DataFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.ui.ApplicationFrame;

import utilities.DateTime;
import utilities.MathFunctionClass;
import utilities.MathUtilities;

import java.awt.Color;
import java.awt.Dimension;

/**
 * This class allows manual fitting of a house model. Specifically a
 * {@link CombinedNormalHouseModel}.
 * 
 * @author Benedict Wilkins
 *
 */
public class DataFitter {

  /**
   * Entry point for fitting data.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    DataFitter df = new DataFitter();
    df.fit(DataGroup.SEASON);
    // df.fit(DataGroup.ACORNU);
    // df.fit(DataGroup.ADVERSITY);
    // df.fit(DataGroup.AFFLUENT);
    // df.fit(DataGroup.COMFORTABLE);
  }

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
    this.plot.setDataset(
        this.index,
        createDataset(MathUtilities.generateSequence(0.0,
            (double) group.size - 1, group.size.intValue()), read(group)
            .getColumn(1, Double.class).toArray(new Double[] {})));
    this.plot.setRenderer(this.index, new StandardXYItemRenderer());

    this.plot.setDataset(this.index, group.getData());
    this.plot.setRenderer(this.index, new StandardXYItemRenderer());

    plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.RED);
    plot.getRendererForDataset(plot.getDataset(1)).setSeriesPaint(0,
        Color.BLACK);
    plot.setBackgroundPaint(Color.WHITE);
    NumberAxis domain = (NumberAxis) plot.getDomainAxis();
    domain.setRange(0.0, group.size - 1);
    frame.setContentPane(panel);
    frame.pack();
    frame.setVisible(true);
  }

  private DataFrame read(DataGroup group) {
    DataReader dr = new DataReader();
    return dr.readFile(group.getPath(), " ", true, new Class<?>[] {
        String.class, Double.class, Double.class });
  }

  private GeneralXYDataset createDataset(Double[] xval, Double[] yval) {
    GeneralXYDataset data = new GeneralXYDataset();
    data.addSeries(xval, yval, "Dataset");
    index++;
    return data;
  }

  private class FitterFrame extends ApplicationFrame {
    private static final long serialVersionUID = 1L;

    public FitterFrame() {
      super("FitterFrame");
    }
  }

  private enum DataGroup {

    AFFLUENT("./res/gmAffluent.txt", HouseModelCombinedNormalAffluent.class,
        HalfHourClock.DAYLENGTH), COMFORTABLE("./res/gmComfortable.txt",
        HouseModelCombinedNormalComfortable.class, HalfHourClock.DAYLENGTH), ADVERSITY(
        "./res/gmAdversity.txt", HouseModelCombinedNormalAdversity.class,
        HalfHourClock.DAYLENGTH), ACORNU("./res/gmAcorn-u.txt",
        HouseModelCombinedNormalAcornU.class, HalfHourClock.DAYLENGTH), SEASON(
        "./res/gmSeasonality.txt", SeasonModel.class,
        HalfHourClock.YEARLENGTH);

    private String path;
    private MathFunctionClass<Double> function;
    private Integer size;

    private DataGroup(String path,
        Class<? extends MathFunctionClass<Double>> function, Integer size) {
      this.path = path;
      try {
        this.function = function.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
      this.size = size;
    }

    public GeneralXYDataset getData() {
      GeneralXYDataset gd = new GeneralXYDataset();
      Double[] xvalues = MathUtilities.generateSequence(0.0, (double) size - 1,
          size * 2);
      Double[] yvalues = function.compute(xvalues);
      gd.addSeries(xvalues, yvalues, this.name());
      return gd;
    }

    public String getPath() {
      return this.path;
    }
  }
}
