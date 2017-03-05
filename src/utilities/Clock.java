package utilities;

import java.util.HashMap;

public class Clock implements Runnable {

  public final static int SECOND = 0, MINUTE = 1, HOUR = 2, DAY = 3, MONTH = 4,
      YEAR = 5;
  private static final int MAXTIMETYPES = 5;

  private static final HashMap<Integer, ClockRunnable> INCREMENTMAP;

  static {
    INCREMENTMAP = new HashMap<>();
    INCREMENTMAP.put(SECOND, new ClockRunnable() {
      @Override
      public void run(Clock clock) {
        clock.incrementSecond();
      }
    });
    INCREMENTMAP.put(MINUTE, new ClockRunnable() {
      @Override
      public void run(Clock clock) {
        clock.incrementMinute();
      }
    });
    INCREMENTMAP.put(HOUR, new ClockRunnable() {
      @Override
      public void run(Clock clock) {
        clock.incrementHour();
      }
    });
    INCREMENTMAP.put(DAY, new ClockRunnable() {
      @Override
      public void run(Clock clock) {
        clock.incrementDay();
      }
    });
    INCREMENTMAP.put(MONTH, new ClockRunnable() {
      @Override
      public void run(Clock clock) {
        clock.incrementMonth();
      }
    });
    INCREMENTMAP.put(YEAR, new ClockRunnable() {
      @Override
      public void run(Clock clock) {
        clock.incrementYear();
      }
    });
  }

  private DateTime currentDateTime;
  private int increment;
  private int real;
  private ClockRunnable run;
  private boolean stop = true;

  /**
   * Constructor. Initialises a new {@link Clock}. This clock will count in
   * increments of the increment after every length of time specified by
   * realTime has passed. Example: increment = 1, type = Clock.SECOND, real =
   * 10. Will increment curretDateTime by 1 second for every real 10 seconds
   * that pass. The clock will start when the run method is called.
   * 
   * @param currentDateTime
   * @param increment
   * @param type
   * @param real
   */
  public Clock(DateTime currentDateTime, int increment, int type, int real) {
    if (type >= MAXTIMETYPES) {
      throw new IllegalArgumentException("Illegal time type: see Clock");
    }
    this.currentDateTime = currentDateTime;
    this.increment = increment;
    this.run = INCREMENTMAP.get(type);
    this.real = real;
  }

  protected void setRealTimeDelay(int delay) {
    if (stop) {
      this.real = delay;
    }
  }

  public DateTime getCurrentDateTime() {
    return this.currentDateTime;
  }

  @Override
  public void run() {
    this.stop = false;
    while (!stop) {
      try {
        // System.out.println(currentDateTime);
        Thread.sleep(real);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      this.run.run(this);
      // System.out.println(this.getCurrentDateTime());
    }
  }

  /**
   * Forwards this clock by 1 tick (based on its increment and type). WARNING:
   * this method may cause the clock to become out of sync, it ignores the real
   * time update.
   */
  public void tick() {
    this.run.run(this);
  }

  protected void stop() {
    this.stop = true;
  }

  private void incrementSecond() {
    currentDateTime.setSecond(currentDateTime.getSecond() + increment);
  }

  private void incrementMinute() {
    currentDateTime.setMinute(currentDateTime.getMinute() + increment);
  }

  private void incrementHour() {
    currentDateTime.setHour(currentDateTime.getHour() + increment);
  }

  private void incrementDay() {
    currentDateTime.setHour(currentDateTime.getDay() + increment);
  }

  private void incrementMonth() {
    currentDateTime.setHour(currentDateTime.getMonth() + increment);
  }

  private void incrementYear() {
    currentDateTime.setHour(currentDateTime.getYear() + increment);
  }

  private interface ClockRunnable {
    public void run(Clock clock);
  }
}
