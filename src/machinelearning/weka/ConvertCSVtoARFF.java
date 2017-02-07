package machinelearning.weka;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

/**
 * Converts a CSV file to an ARFF file for better Weka compatibility. Note that
 * the code was taken from: http://weka.wikispaces.com/Converting+CSV+to+ARFF
 * and is part of the example code given on the weka wiki.
 * 
 * @author Weka Wiki
 *
 */
public class ConvertCSVtoARFF implements FileConverter {

  /**
   * Converts the given inputFile - in csv format, to the given outputFile - in
   * arff format.
   * 
   * @param inputFile
   *          to convert
   * @param outputFile
   *          to be converted to
   */
  @Override
  public void convert(String inputFile, String outputFile) {
    try {
      CSVLoader loader = new CSVLoader();
      loader.setSource(new File(inputFile));
      Instances data = loader.getDataSet();

      ArffSaver saver = new ArffSaver();
      saver.setInstances(data);
      saver.setFile(new File(outputFile));
      saver.writeBatch();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
