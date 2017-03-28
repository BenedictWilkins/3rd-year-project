package utilities.test;

import org.junit.Test;

import utilities.Clock;
import utilities.DateTime;

/**
 * JUnit test case for {@link Clock}.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestClock {

  @Test
  public void test() {
    Clock clock = new Clock(new DateTime("2016-01-01 00:00:00"), 30,
        Clock.MINUTE, 100);
    clock.run();
  }
}
