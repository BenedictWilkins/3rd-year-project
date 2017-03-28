package agent.general;

import agent.NeighbourhoodAgentMind;
import agent.PredictorAgentMind;
import agent.SmartMeterAgentMind;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * An {@link Enum} that represents a type of agent. Valid agents are:
 * {@link SmartMeterAgentMind SmartMeterAgent}, {@link NeighbourhoodAgentMind
 * NeighbourhoodAgent} and {@link PredictorAgentMind PredictorAgent}
 * 
 * @author Benedict Wilkins
 *
 */
public enum AgentType {

  SMARTMETER, NEIGHBOURHOOD, PREDICTOR;

  // method used to create it
  private Method creationMethod;

  /**
   * Sets the creation method used to create a given agent. This method should
   * be called once for each agent type. The method should reside in the
   * {@link AgentFactory} and be properly {@link Annotation annotated}.
   * 
   * @param creationMethod
   *          factory method to create the given agent
   */
  public void setCreationMethod(Method creationMethod) {
    this.creationMethod = creationMethod;
  }

  /**
   * Getter for creation method, see {@link #setCreationMethod(Method)}.
   * 
   * @return the creation method
   */
  public Method getCreationMethod() {
    return creationMethod;
  }

}
