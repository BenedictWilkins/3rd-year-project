package threading;

/**
 * The {@link ThreadState} perceive.
 * 
 * @author Benedict Wilkins
 *
 */
public class ThreadStatePerceive implements ThreadState {

  @Override
  public void run(AgentRunnable runnable) {
    runnable.getAgent().perceive(null);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
