package threading;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Manager of the {@link AgentRunnable}s.
 * 
 * @author Benedict Wilkins
 *
 */
public class AgentThreadManager {
  protected final ThreadStateDecide threadStateDecide = new ThreadStateDecide();
  protected final ThreadStateExecute threadStateExecute = new ThreadStateExecute();
  protected final ThreadStatePerceive threadStatePerceive = new ThreadStatePerceive();

  protected boolean simulationStarted = false;

  protected Set<Thread> activeThreads;
  protected Set<AgentRunnable> runnables;

  public AgentThreadManager() {
    this.activeThreads = new HashSet<>();
    this.runnables = new HashSet<>();
  }

  /**
   * Starts the perceive decide execute process, all {@link AgentRunnable}s
   * start.
   * 
   * @param cycleTime
   *          how long should each cycle last (ms)
   */
  public void start(long cycleTime) {
    this.simulationStarted = true;
    System.out.println(Arrays.toString(runnables.toArray()));
    while (this.simulationStarted) {
      doPerceive();
      doDecide();
      doExecute();
      try {
        Thread.sleep(cycleTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  protected void doPerceive() {
    doPhase(this.threadStatePerceive);
  }

  protected void doDecide() {
    doPhase(this.threadStateDecide);
  }

  protected void doExecute() {
    doPhase(this.threadStateExecute);
  }

  private void doPhase(ThreadState state) {
    rebuildAllThreads();
    setNextPhase(state);
    startAllThreads();
    waitForAllThreads();
  }

  private void startAllThreads() {
    for (Thread t : this.activeThreads) {
      t.start();
    }
  }

  private void setNextPhase(ThreadState state) {
    Iterator<AgentRunnable> iter = runnables.iterator();
    while (iter.hasNext()) {
      iter.next().setState(state);
    }
  }

  private void rebuildAllThreads() {
    this.activeThreads.clear();

    for (AgentRunnable a : this.runnables) {
      this.activeThreads.add(new Thread(a));
    }
  }

  private void waitForAllThreads() {
    while (checkAlive()) {
      continue;
    }
  }

  private boolean checkAlive() {
    for (Thread t : this.activeThreads) {
      if (t.isAlive()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Adds a new {@link AgentRunnable}.
   * 
   * @param runnable
   *          to add
   */
  public void addAgent(AgentRunnable runnable) {
    if (this.simulationStarted) {
      throw new IllegalThreadStateException(
          "Cannot add a new agent at runtime.");
    }
    this.runnables.add(runnable);
  }
}