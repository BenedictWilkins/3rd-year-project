package threading;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;

/**
 * Runnable representing an {@link Agent}. Each agent runs in its own thread.
 * 
 * @author Benedict Wilkins
 *
 */
public class AgentRunnable implements Runnable {

  private Mind agent;
  private ThreadState state;

  public AgentRunnable(Mind agent) {
    this.agent = agent;
  }

  @Override
  public void run() {
    this.state.run(this);
  }

  public ThreadState getState() {
    return state;
  }

  public void setState(ThreadState state) {
    this.state = state;
  }

  public void setAgent(Mind agent) {
    this.agent = agent;
  }

  public Mind getAgent() {
    return agent;
  }
}
