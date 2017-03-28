package agent.communication;

import java.io.Serializable;

/**
 * This interface should be implemented by any class that will be (potentially)
 * serialised and sent over network or through the simulation. See
 * {@link Serializable}.
 * 
 * @author Benedict Wilkins
 *
 */
public interface NetworkObject extends Serializable {

}
