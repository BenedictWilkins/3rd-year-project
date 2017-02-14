package test;

import static org.junit.Assert.assertEquals;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaData;
import machinelearning.agent.AbstractDataFrameRow;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Random;

public class TestSerializable {

  private final String testString = "TEST SERIALISABLE!";

  
  private class DataFrameRowTest extends AbstractDataFrameRow {
    public DataFrameRowTest(Object... objs) {
      super(objs);
    }
  }
  
  @Test
  public void testByteArrayInOut() {
    Object result = testSerializable(testString);
    assertEquals("Test: String is Serializable", testString, result);
  }
  

  @Test
  public void testDataFrameRow() {
    AbstractDataFrameRow test = new DataFrameRowTest("Test", 1, 2.0, '3');
    Object result = testSerializable(test);
    assertEquals("Test: DataFrameRow is Serializable", test, result);
  }

  @Test
  public void testDataFrameMetaData() {
    DataFrameMetaData test = new DataFrameMetaData(new String[] { "X", "Y" },
        new Class<?>[] { String.class, Double.class });
    Object result = testSerializable(test);
    assertEquals("Test: DataFrameMetaData is Serializable", test, result);
  }

  @Test
  public void testDataFrame() {
    DataFrame test = new DataFrame(new DataFrameMetaData(new String[] { "X",
        "Y" }, new Class<?>[] { String.class, Double.class }));
    Random random = new Random();
    int size = random.nextInt(20);
    for (int i = 0; i < size; i++) {
      test.addRow(new DataFrameRowTest(String.valueOf(random.nextInt()), random
          .nextDouble()));
    }
    Object result = testSerializable(test.getData());
    List<AbstractDataFrameRow> resultFrame = (List<AbstractDataFrameRow>)result;
    assertEquals("Test: DataFrameRow is Serializable", test.getData(), resultFrame);
  }

  private Object testSerializable(Object test) {
    try {
      // try output
      ByteArrayOutputStream outstream = new ByteArrayOutputStream();
      ObjectOutputStream output = new ObjectOutputStream(outstream);
      output.writeObject(test);
      output.flush();

      // try input
      ByteArrayInputStream instream = new ByteArrayInputStream(
          outstream.toByteArray());
      ObjectInputStream input = new ObjectInputStream(instream);
      return input.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
