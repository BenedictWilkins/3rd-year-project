package machinelearning.agent;

import utilities.DateTime;
import weka.classifiers.evaluation.NumericPrediction;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import housemodels.HalfHourClock;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that should be extends by any concrete implementation of
 * {@link ForecastingModel} that uses a prediction model from the Weka package.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class AbstractWekaForecastingModel implements ForecastingModel {

  protected static final String TRAINNAME = "Train";

  /**
   * Converts a {@link DataFrame} which is assumed be meta:
   * {@link DataFrameMetaTimeValue} into the Weka {@link Instances} format. Each
   * {@link Instance} is given weight 1.0. The returned {@link Instances}
   * contains only values and ignores time - the {@link DataFrame} is assumed to
   * be ordered by time.
   * 
   * @param data
   *          to convert
   * 
   * @return converted {@link DataFrame} to {@link Instances}
   */
  protected final Instances convertToTimeSeriesInstances(DataFrame data) {
    DataFrameMetaTimeValue meta = (DataFrameMetaTimeValue) data.getMetaData();
    ArrayList<Attribute> atts = new ArrayList<>();
    atts.add(new Attribute(meta.getHeaders()[DataFrameMetaTimeValue
        .getValueColumnIndex()]));
    Instances instances = new Instances(TRAINNAME, atts, data.getNumRows());
    List<?> values = data.getColumn(
        DataFrameMetaTimeValue.getValueColumnIndex(),
        DataFrameMetaTimeValue.getValueColumnType());
    for (Object v : values) {
      instances.add(new DenseInstance(1.0, new double[] { (double) v }));
    }
    return instances;
  }

  protected DataFrame toDataFrame(List<List<NumericPrediction>> forecasts,
      String endTrainingTime) {
    DataFrame frame = new DataFrame(DataFrameMetaTimeValue.getInstance());
    DateTime current = new DateTime(endTrainingTime);
    List<Double> data = new ArrayList<>();
    for (List<NumericPrediction> l : forecasts) {
      HalfHourClock.nextInPlace(current);
      frame.addRow(new DataFrameRowReading(current.toString(), l.get(0)
          .predicted()));

    }
    return frame;
  }
}
