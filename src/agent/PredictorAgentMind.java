package agent;

import agent.general.GeneralAgentMind;
import machinelearning.agent.ForecastingModel;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.Set;

public class PredictorAgentMind extends
    GeneralAgentMind<NeighbourhoodAgentBrain> {

  protected PredictorAgentMind(ForecastingModel model,
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
  public void update(CustomObservable o, Object arg) {
    // TODO Auto-generated method stub

  }

}
