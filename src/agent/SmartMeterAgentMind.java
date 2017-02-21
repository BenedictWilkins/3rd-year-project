package agent;

import agent.SmartMeterAgentBrain.PerceptionWrapper;
import agent.actions.CommunicationAction;
import agent.actions.GlobalResult;
import agent.actions.PerceiveAction;
import agent.actions.TakeReadingAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.communication.ReadingPayload;
import agent.communication.SmartMeterReadingNetworkObject;
import agent.general.GeneralAgentMind;
import environment.communication.module.Address;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.DataFrameRow;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * The {@link Mind} implementation for a Smart Meter Agent. <br>
 * Extends: {@link AbstactAgent}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentMind extends GeneralAgentMind {

  private DataFrame recentPerception = new DataFrame(
      DataFrameMetaTimeValue.getInstance());

  // the address of this agents manager
  private Address manager;

  /**
   * Constructor.
   * 
   * @param possibleActions
   *          a list of all possible {@link AbstractAction}s this agent can
   *          perform.
   */
  public SmartMeterAgentMind(Class<? extends SmartMeterAgentBrain> brainclass,
      Set<Class<? extends AbstractAction>> possibleActions, Address manager) {
    super(brainclass, possibleActions);
    this.manager = manager;
  }

  @Override
  public void perceive(Object perceptionWrapper) {
    notifyObservers(new PerceiveAction(), this.getBrainClass());
  }

  private void perceiveContinue(PerceptionWrapper perception) {
    perception.getActionResults().forEach(
        (GlobalResult gr) -> recentPerception.addRow(((ReadingPayload) gr
            .getPayload()).getPayload()));
    // System.out.println(Arrays.toString(recentPerception.getData().toArray()));
    System.out.println("COM: "
        + Arrays.toString(perception.communicationPerceptions.toArray()));
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    List<Address> recipients = new ArrayList<>();
    recipients.add(manager);
    notifyObservers(new TakeReadingAction(this.getBody()));
    // System.out.println(this.getBody().getId().toString() + ":"
    // + Arrays.toString(recentPerception.getData().toArray()));
    if (!recentPerception.getData().isEmpty()) {
      List<DataFrameRow> toSend = new ArrayList<>();
      toSend.addAll(recentPerception.getData());
      notifyObservers(new CommunicationAction<NetworkObject>(
          new NetworkObjectPayload(new SmartMeterReadingNetworkObject(toSend,
              (String) this.getBody().getId())), this.getBody(), recipients));
    }
    recentPerception.clear();

    // try {
    // ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
    // ObjectOutputStream objectstream = new ObjectOutputStream(bytestream);
    // objectstream.writeObject(new
    // SmartMeterReadingNetworkObject(recentPerception.getData(), ));
    // objectstream.flush();
    // notifyObservers(new CommunicationAction<byte[]>(new ByteArrayPayload(
    // bytestream.toByteArray()), null, recipients));
    // recentPerception.clear();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (PerceptionWrapper.class.isAssignableFrom(arg.getClass())) {
      perceiveContinue((PerceptionWrapper) arg);
    }
  }
}
