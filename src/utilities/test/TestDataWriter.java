package utilities.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import utilities.datawriter.DataWriter;
import utilities.datawriter.FileFormatCSV;

/**
 * JUnit test case for the {@link DataWriter} class.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestDataWriter {

  private static final String[] TESTDATA = new String[] { "1", "2", "3", "4" };
  private static final String TESTFILE = "test.csv";

  @Test
  public void testCsvWriter() {
    DataWriter writer = new DataWriter(TESTFILE, new FileFormatCSV(1));
    writer.write(TESTDATA);

    writer = new DataWriter(TESTFILE, new FileFormatCSV(2));
    writer.write(TESTDATA);

    writer = new DataWriter(TESTFILE, new FileFormatCSV(4));
    writer.write(TESTDATA);

    try {
      writer = new DataWriter(TESTFILE, new FileFormatCSV(3));
      writer.write(TESTDATA);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

}
