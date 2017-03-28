package threading;

/**
 * The {@link ThreadState} execute.
 * 
 * @author Benedict Wilkins
 *
 */
public class ThreadStateExecute implements ThreadState {
  @Override
  public void run(AgentRunnable runnable) {
    runnable.getAgent().execute(null);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}