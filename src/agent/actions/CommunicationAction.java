package agent.actions;

import agent.CommunicationActuator;
import agent.communication.MessagePayload;
import agent.communication.NetworkObject;
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
 * An {@link Action} that that an agent mind may produces signifying that it
 * wishes to communicate with the outside environment. <br>
 * See: {@link MessagePayload} and {@link NetworkObject} for details on
 * communication message contents or payloads. An action of this kind should be
 * directed to a {@link CommunicationActuator} to be handled accordingly.
 * 
 * @author Benedict Wilkins
 *
 */
public class CommunicationAction<T> extends AbstractAction {

  private MessagePayload<T> payload;
  private List<Address> recipients;

  /**
   * Constructor.
   * 
   * @param payload
   *          that is to be sent
   * @param actor
   *          the sending actor
   * @param recipients
   *          the receiving addresses
   */
  public CommunicationAction(MessagePayload<T> payload, AbstractAgent actor,
      List<Address> recipients) {
    super(actor);
    this.payload = payload;
    this.setRecipients(recipients);
  }

  public MessagePayload<T> getPayload() {
    return payload;
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
