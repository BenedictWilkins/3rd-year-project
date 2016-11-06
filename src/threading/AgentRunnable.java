package threading;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;

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
