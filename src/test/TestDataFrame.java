package test;

import machinelearning.agent.AbstractDataFrameRow;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaData;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.DataFrameRow;

import org.junit.Test;

import utilities.DateTime;

/**
 * JUnit test case for the {@link DataFrame} class.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestDataFrame {

  // this is used as a trick to enable protected method testing
  private class DataFrameTestClass extends DataFrame {
    private static final long serialVersionUID = 1L;

    public DataFrameTestClass(DataFrameMetaData meta) {
      super(meta);
    }

    @Override
    protected void checkRowSize(DataFrameRow row)
        throws IllegalArgumentException {
      super.checkRowSize(row);
    }

    @Override
    protected void checkRowType(DataFrameRow row)
        throws IllegalArgumentException {
      super.checkRowType(row);
    }
  }

  private class DataFrameRowTest extends AbstractDataFrameRow {
    private static final long serialVersionUID = 1L;

    public DataFrameRowTest(Object... objs) {
      super(objs);
    }
  }

  @Test
  public void testCheckSizeSuccess() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame
        .checkRowSize(new DataFrameRowTest(new DateTime(0, 0, 0, 0, 0, 0), 1.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckSizeFail() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame.checkRowSize(new DataFrameRowTest(new DateTime(0, 0, 0, 0, 0, 0)));
  }

  @Test
  public void testCheckTypeSuccess() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame
        .checkRowType(new DataFrameRowTest(new DateTime(0, 0, 0, 0, 0, 0), 1.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckTypeFail() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame.checkRowType(new DataFrameRowTest(new DateTime(0, 0, 0, 0, 0, 0),
        "test"));
  }

  @Test
  public void testAddRowSuccess() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame.addRow(new DataFrameRowTest(new DateTime(0, 0, 0, 0, 0, 0), 1.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddRowFailType() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame.addRow(new DataFrameRowTest(new DateTime(0, 0, 0, 0, 0, 0), "test"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddRowFailSize() {
    DataFrameTestClass frame = new DataFrameTestClass(
        DataFrameMetaTimeValue.getInstance());
    frame.addRow(new DataFrameRowTest());
  }

}
