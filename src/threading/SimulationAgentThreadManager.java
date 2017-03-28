package threading;

import utilities.Clock;
import utilities.GeneralUtilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * An {@link AgentThreadManager} specifically for the simulation system. Has
 * additional functionality, in that it can run {@link Thread}s in arbitrary
 * groups. Generally we have a group for each type of agent.
 * 
 * @author Benedict Wilkins
 *
 */
public class SimulationAgentThreadManager extends AgentThreadManager {

  // private static Double RUNLENGTH = 50.0;
  private static final String LOGPATH = "logs/ThreadLog"
      + System.currentTimeMillis();
  private static boolean debug = false;
  private static Logger logger;
  
  static {
    if (debug) {
      logger = GeneralUtilities.getLogger(LOGPATH, "THREADLOGGER");
    }
  }

  private Map<String, Set<AgentRunnable>> runnablesMap;
  private List<String> executionGroups;
  private Clock clock;
  private boolean paused = false;

  /**
   * Constructor. execution groups are executed in order, each group will
   * complete its perceive decide execute cycle before then next begins.
   * 
   * @param executionGroups
   *          groups in which to execute (in order)
   * @param clock
   *          to use for simulation (it will tick after all groups have
   *          completed their cycles)
   */
  public SimulationAgentThreadManager(List<String> executionGroups, Clock clock) {
    super();
    this.runnablesMap = new HashMap<>();
    this.executionGroups = executionGroups;
    this.clock = clock;
    for (String c : executionGroups) {
      runnablesMap.put(c, new HashSet<>());
    }
  }

  @Override
  public synchronized void start(long cycleTime) {
    super.simulationStarted = true;
    System.out.println(Arrays.toString(runnables.toArray()));
    Long time = 0L;
    while (this.simulationStarted) {
      if (debug) {
        time = System.nanoTime();
      }
      for (String s : executionGroups) {
        super.runnables = runnablesMap.get(s);
        doPerceive();
        doDecide();
        doExecute();
      }
      if (debug) {
        logger.info(String.valueOf((System.nanoTime() - time)));
      }
      try {
        Thread.sleep(cycleTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // update clock
      clock.tick();
      checkPause();

    }
  }

  private synchronized void checkPause() {
    if (paused) {
      try {
        System.out.println("PAUSED");
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Adds an new {@link AgentRunnable} to the simulation.
   * 
   * @param runnable
   *          to add
   * @param group
   *          of the runnable
   */
  public void addAgent(AgentRunnable runnable, String group) {
    if (runnablesMap.containsKey(group)) {
      runnablesMap.get(group).add(runnable);
    } else {
      throw new IllegalStateException("INVALID RUNNABLE GROUP: " + group);
    }
  }
  
  /**
   * Does nothing, use {@link #addAgent(AgentRunnable, String)} instead.
   */
  @Override
  public void addAgent(AgentRunnable runnable) {
  }


  /**
   * Sets the pause flag.
   */
  public void pause() {
    if (!this.paused) {
      this.paused = true;
    }
  }

  /**
   * Unsets the pause flag.
   */
  public synchronized void unpause() {
    if (paused) {
      this.paused = false;
      this.notify();
    }
  }

 
}
