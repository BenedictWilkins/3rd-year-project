package test;

import static org.junit.Assert.assertFalse;

import housemodels.HalfHourClock;
import org.junit.Test;

/**
 * JUnit test case for the {@link DataHalfHourClockFrame} class.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestHalfHourClock {

  @Test
  public void testStop() {
    HalfHourClock tracker = HalfHourClock.getInstance();
    Thread thread = new Thread(tracker);
    thread.start();

    Thread stopper = new Thread(new Runnable() {
      @Override
      public void run() {
        // HalfHourClock.stop();
      }
    });
    try {
      Thread.sleep(1000);
      stopper.start();
      Thread.sleep(200); // give time for the thread to die
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertFalse(thread.isAlive());
  }

  @Test
  public void testSetInterval() {
    HalfHourClock tracker = HalfHourClock.getInstance();
    Thread thread = new Thread(tracker);
    // HalfHourClock.setInterval(500);
    // HalfHourClock.reset();
    thread.start();

    Thread stopper = new Thread(new Runnable() {
      @Override
      public void run() {
        // assertEquals(HalfHourClock.getTime(), 2);
        // HalfHourClock.stop();
      }
    });
    try {
      Thread.sleep(1050);
      stopper.start();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
