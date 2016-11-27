package environment.communication.module;

import agent.actions.CommunicationAction;

/**
 * An a interface that should be implemented by a class that represents the
 * Address of a communication message. See: {@link CommunicationAction}.
 * 
 * @author Benedict Wilkins
 *
 */
public interface Address {
  
  public String getAdress();

}
