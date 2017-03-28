package utilities.test;

import org.junit.Test;

import utilities.DateTime;

/**
 * JUnit test case for {@link DateTime}.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestDateTime {

  @Test
  public void test() {
    DateTime dt = new DateTime("2016-01-27 12:47:13");
    System.out.println(dt.toString());

    dt = new DateTime(2016, 1, 27, 12, 47, 13);
    System.out.println(dt.toString());

    dt = new DateTime(0, 0, 0, 0, 0, 0);
    System.out.println(dt);

    dt = new DateTime(2015, 14, 1, 0, 0, 0);
    System.out.println(dt);
  }

}
