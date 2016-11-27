package threading;

/**
 * State of a thread.
 * 
 * @author Benedict Wilkins
 *
 */
public interface ThreadState {
  public void run(AgentRunnable runnable);
}