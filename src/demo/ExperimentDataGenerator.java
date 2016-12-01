package demo;

import housemodels.CombinedNormalHouseModel;
import housemodels.HouseTypeCombinedNormal;

import utilities.DateTime;
import utilities.DateTimeInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExperimentDataGenerator {

  /**
   * Entry point for generating experimental data sets.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    Double error = 2.0;
    Integer days = 365;
    ExperimentDataGenerator gen = new ExperimentDataGenerator();
    gen.new DataWriter("AcornUData.txt", HouseTypeCombinedNormal.ACORNU, error,
        days);
    gen.new DataWriter("AffluentData.txt", HouseTypeCombinedNormal.AFFLUENT,
        error, days);
    gen.new DataWriter("ComfortableData.txt",
        HouseTypeCombinedNormal.COMFORTABLE, error, days);
    gen.new DataWriter("AdversityData.txt", HouseTypeCombinedNormal.ADVERSITY,
        error, days);
  }

  public class DataWriter {

    private BufferedWriter writer;
    private String nl = System.lineSeparator();

    /**
     * Constructor.
     * 
     * @param filename
     *          to write to
     * @param type
     *          of house model to use
     * @param error
     *          of the model
     * @param numDays
     *          to write
     */
    public DataWriter(String filename, HouseTypeCombinedNormal type,
        Double error, int numDays) {
      writer = createWriter(filename);
      Double newError = null;
      for (int i = 0; i < numDays; i++) {
        newError = error + (Math.random() - error / 2) / 5;
        writeReadingsFor1Day(new CombinedNormalHouseModel(type, newError));
      }
      try {
        writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void writeReadingsFor1Day(CombinedNormalHouseModel model) {
      try {
        for (int i = 0; i < 48; i++) {
          this.writer.append(model.getReading((DateTimeInterface) new DateTime(
              i, 0)) + nl);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private BufferedWriter createWriter(String filename) {
      try {
        File file = new File(filename);
        // if the file doesn't exist create it
        file.createNewFile();
        return new BufferedWriter(new PrintWriter(file));
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }

    }

  }
}
