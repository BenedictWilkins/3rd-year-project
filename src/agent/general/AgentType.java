package agent.general;

import housemodels.House;

import java.lang.reflect.Method;

public enum AgentType {

  SMARTMETER, NEIGHBOURHOOD, PREDICTOR;

  private Method creationMethod;

  public void setCreationMethod(Method creationMethod) {
    this.creationMethod = creationMethod;
  }

  public Method getCreationMethod() {
    return creationMethod;
  }

}
