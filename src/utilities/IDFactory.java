package utilities;

import java.util.Random;

/**
 * Factory class that generates random ID. Note that collisions are VERY
 * unlikely but possible.
 * 
 * @author Benedict Wilkins
 *
 */
public class IDFactory {

  private static IDFactory instance = new IDFactory();

  /**
   * Create a new ID given the random seed.
   * 
   * @param seed
   *          see.
   * @return new ID
   */
  public String createID(Long seed) {
    Random rand = new Random();
    rand.setSeed(seed);
    return String.valueOf(rand.nextInt()) + String.valueOf(rand.nextInt());
  }

  /**
   * Create a new ID.
   * 
   * @return new ID
   */
  public String createID() {
    Random rand = new Random();
    return String.valueOf(rand.nextInt()) + String.valueOf(rand.nextInt());
  }

  public static IDFactory getInstance() {
    return instance;
  }
}
