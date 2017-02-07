package demo;

import housemodel.combination.Combine;
import housemodels.HalfHourClock;
import housemodels.House;
import housemodels.HouseFactory;
import utilities.DateTime;
import utilities.Pair;
import utilities.datawriter.DataWriter;
import utilities.datawriter.FileFormatCSV;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExperimentDataGenerator {

  private static final Map<Integer, Method> CREATEMETHODS;

  static {
    Map<Integer, Method> methodMap = new HashMap<>();
    Method[] methods = HouseFactory.class.getMethods();
    Class<? extends Annotation> annotation = HouseFactory.CreateMethod.class;
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].isAnnotationPresent(annotation)) {
        // this method is used to create a house
        methodMap.put(i, methods[i]);
      }
    }
    CREATEMETHODS = Collections.unmodifiableMap(methodMap);
    //System.out.println(Arrays.toString(CREATEMETHODS.values().toArray()));
  }

  /**
   * Entry point for generating experimental data sets.
   * 
   * @param args
   *          none
   */
  public static void main(String[] args) {
    double error = 0;
    DateTime start = new DateTime("2010-01-01 00:00:00");
    DateTime end = new DateTime("2011-01-01 00:00:00");

    ExperimentDataGenerator gen = new ExperimentDataGenerator();
    // generateAcornUDataset(gen, start, end, error);
    generateRandomHouseCombination(5, error);
  }

  /**
   * Generates a random {@link Collection} of {@link House}s.
   * 
   * @param numHouses
   *          size of the returned {@link Collection}
   * @return a {@link Collection} of random {@link House}s
   */
  private static Collection<House> generateRandomHouseCombination(
      int numHouses, double error) {
    try {
      Random rand = new Random();
      Collection<House> houses = new ArrayList<>();
      for (int i = 0; i < numHouses; i++) {
        Method next = CREATEMETHODS.get(rand.nextInt(CREATEMETHODS.size()));
        System.out.println(next);
        //houses.add((House) next.invoke(HouseFactory.getFactory(), error));
      }
      return houses;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static void generateAcornUDataset(ExperimentDataGenerator gen,
      DateTime start, DateTime end, Double error) {
    // acorn u generation
    Experimenter acornu = gen.new Experimenter();
    List<House> acornuhouse = new ArrayList<>();
    acornuhouse.add(HouseFactory.getFactory().createAcornUHouse(error));
    acornu.generateDataToFile(new DataWriter("AcornUData.csv",
        new FileFormatCSV(2)), acornuhouse, start, end, null);
    // combined experiment
    gen.new Experimenter();
  }

  public class Experimenter {

    public void generateDataToFile(DataWriter writer, List<House> houses,
        DateTime start, DateTime end, Combine com) {
      Pair<List<String>, List<Double>> data = generateData(houses, start, end,
          com);
      ArrayList<String> readings = new ArrayList<>();
      readings.add("Date Time");
      readings.add("Usage");
      for (int i = 0; i < data.getO1().size(); i++) {
        readings.add(data.getO1().get(i));
        readings.add(String.valueOf(data.getO2().get(i)));
        start.setMinute(start.getMinute() + HalfHourClock.TIMEINCREMENT);
      }
      writer.write(readings.toArray(new String[readings.size()]));
    }

    /**
     * Generates data from each {@link House} given by houses from the start
     * {@link DateTime} (inclusive) to the end {@link DateTime} (exclusive),
     * combining the reading from each house using the given
     * {@link Combine#combined(List)} method. If a single house is desired, com
     * should be null.
     * 
     * @param houses
     *          to take readings from
     * @param start
     *          to start taking readings (inclusive)
     * @param end
     *          to stop taking readings (exclusive)
     * @param com
     *          to combine readings (null if single house)
     * @return a {@link Pair} containing a {@link List} of dates and a
     *         {@link List} of combined readings mapped by index.
     */
    public Pair<List<String>, List<Double>> generateData(List<House> houses,
        DateTime start, DateTime end, Combine com) {
      List<Double> combinedReadings = new ArrayList<>();
      List<String> dates = new ArrayList<>();
      if (com == null) {
        com = new NullCombine();
      }

      while (start.isBefore(end)) {
        List<Double> singleReadings = new ArrayList<>();
        for (House h : houses) {
          singleReadings.add(h.getReading(start));
        }
        combinedReadings.add(com.combined(singleReadings));
        dates.add(start.toString());
        start.setMinute(start.getMinute() + HalfHourClock.TIMEINCREMENT);
      }
      return new Pair<List<String>, List<Double>>(dates, combinedReadings);
    }
  }

  class NullCombine implements Combine {
    @Override
    public Double combined(List<Double> readings) {
      return readings.get(0);
    }
  }
}
