package agent;

import agent.SmartMeterAgentBrain.PerceptionWrapper;
import agent.actions.GlobalResult;
import agent.actions.PerceiveAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.communication.ReadingPayload;
import agent.communication.SmartMeterReadingNetworkObject;
import agent.general.GeneralAgentMind;
import machinelearning.agent.AbstractDataFrameRow;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.DataFrameRow;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import housemodel.combination.Combine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NeighbourhoodAgentMind extends
    GeneralAgentMind<NeighbourhoodAgentBrain> {

  private DataFrame data = new DataFrame(DataFrameMetaTimeValue.getInstance());
  private Set<NetworkObject> recentPerception;
  private Combine combine;

  public NeighbourhoodAgentMind(Combine combine,
      Set<Class<? extends AbstractAction>> possibleActions) {
    super(NeighbourhoodAgentBrain.class, possibleActions);
    this.combine = combine;
    this.recentPerception = new HashSet<>();
  }

  @Override
  public void perceive(Object perceptionWrapper) {
    notifyObservers(new PerceiveAction(), this.getBrainClass());
  }

  private void perceiveContinue(Set<?> perception) {
    perception.forEach((Object obj) -> recentPerception
        .add((NetworkObject) obj));
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    recentPerception.forEach((NetworkObject no) -> {
      ((SmartMeterReadingNetworkObject)no).getData().forEach((DataFrameRow dfr) -> {
        data.addRow(dfr);
      });
    });
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (PerceptionWrapper.class.isAssignableFrom(arg.getClass())) {
      perceiveContinue((Set<?>) arg);
    }
  }

  // unpack a message that has been received
  private void unpackMessage(NetworkObjectPayload result) {
    // try {
    // ByteArrayPayload payload = (ByteArrayPayload) result.getPayload();
    // ByteArrayInputStream instream = new ByteArrayInputStream(
    // payload.getPayload());
    // ObjectInputStream input;
    // input = new ObjectInputStream(instream);
    // List<?> newdata = (List<?>) input.readObject();
    // System.out.println(Arrays.toString(newdata.toArray()));
    // addRecievedData(newdata);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
  }

  private void addRecievedData(List<?> newdata) {
    for (Object o : newdata) {
      data.addRow((AbstractDataFrameRow) o);
    }
  }
}
