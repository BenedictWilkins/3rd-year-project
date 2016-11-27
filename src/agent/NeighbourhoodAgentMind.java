package agent;

import agent.general.GeneralAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.Set;

public class NeighbourhoodAgentMind extends
    GeneralAgentMind<NeighbourhoodAgentBrain> {

  public NeighbourhoodAgentMind(
      Set<Class<? extends AbstractAction>> possibleActions) {
    super(NeighbourhoodAgentBrain.class, possibleActions);
  }

  @Override
  public void perceive(Object perceptionWrapper) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    System.out.println("MANAGER RECIEVED:" + arg);
  }

}
