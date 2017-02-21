package utilities;

import java.util.Random;

public class IDFactory {

  private static IDFactory instance = new IDFactory();

  public String createID(Long seed) {
    Random rand = new Random();
    rand.setSeed(seed);
    return String.valueOf(rand.nextInt()) + String.valueOf(rand.nextInt());
  }

  public String createID() {
    Random rand = new Random();
    return String.valueOf(rand.nextInt()) + String.valueOf(rand.nextInt());
  }

  public static IDFactory getInstance() {
    return instance;
  }
}
