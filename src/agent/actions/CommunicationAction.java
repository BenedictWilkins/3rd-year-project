package agent.actions;

import environment.HouseEnvironment;
import environment.HouseEnvironmentPhysics;
import environment.NationalGridUniversePhysicsInterface;
import environment.communication.module.Address;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

import java.util.List;

/**
 * An {@link Action} that created any agent situated within a
 * {@link HouseEnvironment}. This action is specifically for taking a SmartMeter
 * reading. See: {@link HouseEnvironmentPhysics} for perform implementation
 * details.
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationAction extends AbstractAction {

  private String payload;
  private List<Address> recipients;

  /**
   * Constructor.
   * 
   * @param payload
   *          the message that should be communicated
   */
  public CommunicationAction(String payload, AbstractAgent actor, List<Address> recipients) {
    super(actor);
    this.payload = payload;
    this.setRecipients(recipients);
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + ":" + this.payload;
  }

  @Override
  public boolean isPossible(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).isPossible(this,
        context);
  }

  @Override
  public boolean isNecessary(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).isNecessary(this,
        context);
  }

  @Override
  public Result perform(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).perform(this,
        context);
  }

  @Override
  public boolean succeeded(Physics physics, Space context) {
    return ((NationalGridUniversePhysicsInterface) physics).succeeded(this,
        context);
  }

  public List<Address> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<Address> recipients) {
    this.recipients = recipients;
  }

}
