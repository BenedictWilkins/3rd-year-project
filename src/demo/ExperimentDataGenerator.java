package demo;

import housemodels.House;
import housemodels.HouseFactory;
import utilities.DateTime;
import utilities.datawriter.DataWriter;
import utilities.datawriter.FileFormatCSV;

import java.util.ArrayList;

public class ExperimentDataGenerator {

  /**
   * Entry point for generating experimental data sets.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    double error = 0.01;
    DateTime dateTime = new DateTime(0, 100); // 100 days
    ExperimentDataGenerator gen = new ExperimentDataGenerator();

    gen.new Experimenter(
        new DataWriter("AcornUData.csv", new FileFormatCSV(1)), HouseFactory
            .getFactory().createAcornUHouse(error), dateTime);

    gen.new Experimenter(new DataWriter("AffluentData.csv",
        new FileFormatCSV(1)), HouseFactory.getFactory().createAdversityHouse(
        error), dateTime);

    gen.new Experimenter(new DataWriter("ComfortableData.csv",
        new FileFormatCSV(1)), HouseFactory.getFactory().createAffluentHouse(
        error), dateTime);

    gen.new Experimenter(new DataWriter("AdversityData.csv", new FileFormatCSV(
        1)), HouseFactory.getFactory().createComfortableHouse(error), dateTime);
  }

  public class Experimenter {

    /**
     * Constructor. Performs an experiment with the given {@link House}, writes
     * it to the file supplied by the given {@link DataWriter}. The readings are
     * generated from {@link DateTime}(0,0) up to and including the
     * {@link DateTime} provided.
     * 
     * @param writer
     *          to write the readings to a file
     * @param house
     *          to experiment with
     * @param dateTime
     *          number of readings to take
     */
    public Experimenter(DataWriter writer, House house, DateTime dateTime) {
      ArrayList<String> readings = new ArrayList<>();
      readings.add(writer.getFile().getName());
      // do days starting at day 0 time 0.
      for (int i = 0; i < dateTime.getDate(); i++) {
        for (int j = 0; j < DateTime.DAYLENGTH; j++) {
          Double reading = house.getReading(new DateTime(j, i));
          readings.add(String.valueOf(reading));
        }
      }
      // do hours starting at the last day
      for (int i = 0; i < dateTime.getTime(); i++) {
        Double reading = house.getReading(new DateTime(i, dateTime.getDate()));
        readings.add(String.valueOf(reading));
      }
      writer.write(readings.toArray(new String[readings.size()]));
    }
  }
}
