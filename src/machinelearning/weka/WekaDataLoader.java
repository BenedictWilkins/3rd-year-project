package machinelearning.weka;

import utilities.Pair;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for loading data files of different formats into Weka: see:
 * {@link Instances}. 
 * 
 * @author Benedict Wilkins
 *
 */
public class WekaDataLoader {

  /**
   * Map storing possible toARFF fileConverters. Converters added to this list
   * should only be of the form: SOMEFILE->ARFF e.g. {@link ConvertCSVtoARFF}.
   */
  public static final Map<String, Class<? extends FileConverter>> FILECONVERTMAP;
  private static final String ARFF = "arff";

  static {
    Map<String, Class<? extends FileConverter>> temp = new HashMap<>();
    temp.put("csv", ConvertCSVtoARFF.class);
    FILECONVERTMAP = Collections.unmodifiableMap(temp);
  }

  /**
   * Gets {@link Instances} from the given file. If the file is not an arff
   * file, an arff file will be created containing the data of the given file.
   * See: {@link #FILECONVERTMAP} for valid file types to load, arff files will
   * be loaded directly.
   * 
   * @param filePath
   *          of the file to load
   * @return {@link Instances} from the file
   */
  public Instances load(String filePath) {
    try {
      Pair<String, FileConverter> output = checkFileExtension(filePath);
      if (output.getO2() != null) {
        output.getO2().convert(filePath, output.getO1());
      }
      // load data from arff file
      return new Instances(new BufferedReader(new FileReader(output.getO1())));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // checks the extension to make sure a converter is available, returns the new
  // output file name and the FileConverter to be used
  private Pair<String, FileConverter> checkFileExtension(String filePath)
      throws InstantiationException, IllegalAccessException {
    String[] split = filePath.split("\\.");
    System.out.println(Arrays.toString(split));
    if (split[split.length - 1].equals(ARFF)) {
      return new Pair<String, FileConverter>(filePath, null);
    }
    Class<? extends FileConverter> converterclass = FILECONVERTMAP
        .get(split[split.length - 1]);
    if (converterclass == null) {
      throw new IllegalArgumentException(
          "No converter available for extension: " + split[split.length - 1]);
    }
    return new Pair<String, FileConverter>(split[0] + "." + ARFF,
        converterclass.newInstance());
  }
}
