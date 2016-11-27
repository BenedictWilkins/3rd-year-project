package threading;

/**
 * The {@link ThreadState} decide.
 * @author Benedict Wilkins
 *
 */
public class ThreadStateDecide implements ThreadState {

  @Override
  public void run(AgentRunnable runnable) {
    runnable.getAgent().decide();
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}