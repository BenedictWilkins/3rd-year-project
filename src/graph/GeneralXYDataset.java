package graph;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;

import utilities.ArgumentUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of the JChart {@link XYDataset} to be used for any numeric data.
 * 
 * @author Benedict Wilkins
 *
 */
public class GeneralXYDataset implements XYDataset {

  private List<Double[]> xs;
  private List<Double[]> ys;
  private Map<String, Integer> stringToInteger;
  private Map<Integer, String> integerToString;

  /**
   * Constructor.
   */
  public GeneralXYDataset() {
    xs = new ArrayList<>();
    ys = new ArrayList<>();
    integerToString = new HashMap<>();
    stringToInteger = new HashMap<>();
  }

  /**
   * Adds a new series to this {@link GeneralXYDataset}.
   * 
   * @param xvals
   *          of the series to add
   * @param yvals
   *          of the series to add
   * @param serieskey
   *          of the series to add
   */
  public void addSeries(Double[] xvals, Double[] yvals, String serieskey) {
    ArgumentUtilities.checkNullArgs(new Object[] { xvals, yvals });
    ArgumentUtilities.checkEmptyArray(xvals);
    ArgumentUtilities.checkEmptyArray(yvals);
    integerToString.put(xs.size(), serieskey);
    stringToInteger.put(serieskey, xs.size());
    xs.add(xvals);
    ys.add(yvals);

  }

  @Override
  public int getSeriesCount() {
    return xs.size();
  }

  @Override
  public String getSeriesKey(int index) {
    return integerToString.get(index);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public int indexOf(Comparable seriesKey) {
    if (!(seriesKey instanceof String)) {
      return -1;
    }
    return stringToInteger.get(seriesKey);
  }

  @Override
  public Number getX(int series, int item) {
    return xs.get(series)[item];
  }

  @Override
  public double getXValue(int series, int item) {
    return xs.get(series)[item];
  }

  @Override
  public Number getY(int series, int item) {
    return ys.get(series)[item];
  }

  @Override
  public double getYValue(int series, int item) {
    return ys.get(series)[item];
  }

  @Override
  public DomainOrder getDomainOrder() {
    return DomainOrder.NONE;
  }

  @Override
  public int getItemCount(int series) {
    return ys.get(series).length;
  }

  @Override
  public void addChangeListener(DatasetChangeListener listener) {
  }

  @Override
  public void removeChangeListener(DatasetChangeListener listener) {
  }

  @Override
  public DatasetGroup getGroup() {
    return null;
  }

  @Override
  public void setGroup(DatasetGroup group) {
  }
}
