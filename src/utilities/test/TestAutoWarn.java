package utilities.test;

import org.junit.Test;

import utilities.autowarn.AutoWarn;
import utilities.autowarn.AutoWarnContinue;

/**
 * JUnit test case for the {@link AutoWarn} classes. Addition classes under
 * test: {@link AutoWarnContinue}.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestAutoWarn {

  @Test
  public void testAutoWarn() {
    AutoWarn warn1 = new AutoWarn("WARNING!...");
    warn1.warn();
    AutoWarn warn2 = new AutoWarn("WARNING WITH EXECEPTION!...",
        new IllegalArgumentException());
    warn2.warn();
  }

  @Test
  public void testAutoWarnContinue() {
    AutoWarnContinue cwarn1 = new AutoWarnContinue("WARING CONTINUE!...", null);
    cwarn1.warn();
    System.out.println("CONTINUE!");
  }
}
