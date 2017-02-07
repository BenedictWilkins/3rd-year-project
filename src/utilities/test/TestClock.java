package utilities.test;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.Clock;
import utilities.DateTime;

public class TestClock {

  @Test
  public void test() {
    Clock clock = new Clock(new DateTime("2016-01-01 00:00:00"), 30,
        Clock.MINUTE, 100);
    clock.run();
  }
}
