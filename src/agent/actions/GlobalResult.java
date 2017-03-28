package agent.actions;

import agent.communication.MessagePayload;
import environment.communication.module.Address;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PhysicalBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A general implementation of {@link Result} that improves upon
 * {@link DefaultActionResult}. Should be used throughout the project as the
 * standard {@link Result}
 * 
 * @author Benedict Wilkins
 *
 */
public class GlobalResult extends DefaultActionResult {

  private MessagePayload<?> payload;

  /**
   * Constructor. See: {@link DefaultActionResult}.
   * 
   * @param payload
   *          a message to send with the result
   */
  public GlobalResult(MessagePayload<?> payload, Actor actor,
      ActionResult result, Exception failureReason, List<Address> recipients) {
    super(result, ((PhysicalBody) actor).getId().toString(), failureReason,
        convertAddressToString((recipients != null) ? recipients
            : new ArrayList<Address>()));
    this.payload = payload;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + ":" + this.getActionResult() + ":"
        + this.getActorId() + "  "
        + Arrays.toString(this.getRecipientsIds().toArray()) + "  " + payload;
  }

  public MessagePayload<?> getPayload() {
    return payload;
  }

  /**
   * Converts from a list of {@link Address}'s to a list of {@link String}s.
   * Using the {@link Address#getAdress()} method.
   * 
   * @param recipients
   *          to convert
   * @return the converted list
   */
  public static List<String> convertAddressToString(List<Address> recipients) {
    if (recipients == null) {
      return null;
    }
    List<String> result = new ArrayList<>();
    recipients.forEach((Address address) -> result.add(address.getAdress()));
    return result;
  }
}
