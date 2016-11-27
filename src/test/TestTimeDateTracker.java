package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import housemodels.TimeDateTracker;
import org.junit.Test;

public class TestTimeDateTracker {

  @Test
  public void testStop() {
    TimeDateTracker tracker = TimeDateTracker.getInstance();
    Thread thread = new Thread(tracker);
    thread.start();

    Thread stopper = new Thread(new Runnable() {
      @Override
      public void run() {
        TimeDateTracker.stop();
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
    TimeDateTracker tracker = TimeDateTracker.getInstance();
    Thread thread = new Thread(tracker);
    TimeDateTracker.setInterval(500);
    TimeDateTracker.reset();
    thread.start();

    Thread stopper = new Thread(new Runnable() {
      @Override
      public void run() {
        assertEquals(TimeDateTracker.getTime(), 2);
        TimeDateTracker.stop();
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
