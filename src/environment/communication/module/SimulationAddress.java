package environment.communication.module;

import agent.actions.CommunicationAction;
import environment.HouseEnvironment;

/**
 * An implementation of {@link Address} specifically for use in the simulation.
 * Contains only the id of the agent/ {@link HouseEnvironment} that the message
 * should be sent to. See: {@link CommunicationAction}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SimulationAddress implements Address {

  private String id;

  /**
   * Constructor.
   * 
   * @param id
   *          of the agent that the {@link CommunicationAction} message should
   *          be sent to
   */
  public SimulationAddress(String id) {
    this.id = id;
  }

  @Override
  public String getAdress() {
    return this.id;
  }

  @Override
  public String toString() {
    return this.id;
  }

}
