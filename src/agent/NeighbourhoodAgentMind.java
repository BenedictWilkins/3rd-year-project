package agent;

import agent.NeighbourhoodAgentBrain.PerceptionWrapper;
import agent.actions.CommunicationAction;
import agent.actions.PerceiveAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.communication.SmartMeterReadingNetworkObject;
import agent.general.GeneralAgentMind;
import environment.communication.module.Address;
import housemodel.combination.ReadingCombinator;
import housemodel.threshold.ModelModifier;

import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.DataFrameRowReading;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link Mind} implementation for a Neighbourhood Agent. See
 * {@link NeighbourhoodAgentBody}. This mind will combine readings, send and
 * forward messages. <br>
 * Extends {@link GeneralAgentMind}.
 * 
 * @author Benedict Wilkins
 *
 */
public class NeighbourhoodAgentMind extends GeneralAgentMind {

  // a store of the readings this mind has received/is processing
  protected DataFrame data = new DataFrame(DataFrameMetaTimeValue.getInstance());
  protected Set<NetworkObject> recentPerception;
  protected Set<NetworkObject> toForward;
  protected ReadingCombinator combinator;
  protected Address manager;
  protected Set<Address> subordinates;
  protected int sentIndex = 0;
  protected int readingCount = 0;

  /**
   * Constructor. See {@link GeneralAgentMind}.
   * 
   * @param brainclass
   *          class of the {@link NeighbourhoodAgentBrain} this {@link Mind} is
   *          linked to
   * @param combinator
   *          use to combine the readings received by this {@link Mind}
   * @param possibleActions
   *          the {@link Set} of possible {@link AbstractAction}s this
   *          {@link Mind} can take in each cycle
   * @param manager
   *          the {@link Address} of this agents superior
   * @param subordinates
   *          the {@link Address}s of this agents subordinates
   */
  public NeighbourhoodAgentMind(
      Class<? extends NeighbourhoodAgentBrain> brainclass,
      ReadingCombinator combinator,
      Set<Class<? extends AbstractAction>> possibleActions, Address manager,
      Set<Address> subordinates) {
    super(brainclass, possibleActions);
    this.combinator = combinator;
    this.recentPerception = new HashSet<>();
    this.toForward = new HashSet<>();
    this.manager = manager;
    this.subordinates = subordinates;
    this.combinator.setExpectedReadings(subordinates.size());
  }

  @Override
  public void perceive(Object perceptionWrapper) {
    notifyObservers(new PerceiveAction(), this.getBrainClass());
  }

  // Called by this agent brain after it has received a perceive action
  protected void perceiveContinue(PerceptionWrapper perception) {
    recentPerception = perception.getMessages();
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    // combine the most recent perceptions and add them to the store, also
    // decide
    List<SmartMeterReadingNetworkObject> readings = new ArrayList<>();
    Set<NetworkObject> other = new HashSet<>();
    this.recentPerception
        .forEach((NetworkObject no) -> {
          if (SmartMeterReadingNetworkObject.class.isAssignableFrom(no
              .getClass())) {
            readings.add((SmartMeterReadingNetworkObject) no);
          } else {
            other.add(no);
          }
        });
    // System.out.println("READINGS: " + Arrays.toString(readings.toArray()));

    // combine the readings
    List<DataFrameRowReading> combinedReadings = combinator.combine(readings);
    // System.out.println(Arrays.toString(combinedReadings.toArray()));
    for (DataFrameRowReading r : combinedReadings) {
      data.addRow(r);
      readingCount++;
    }
    // look at the list of other perceptions, check if there are any that should
    // be forwarded
    other.forEach((NetworkObject no) -> {
      if (ModelModifier.class.isAssignableFrom(no.getClass())) {
        toForward.add(no);
      }
    });
    recentPerception.clear();
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    // System.out.println(Arrays.toString(data.getData().toArray()));
    forwardReadings();
    forwardMessages();
  }

  // forwards any messages that have been received by a manager
  private void forwardMessages() {
    toForward.forEach((NetworkObject no) -> {
      notifyObservers(new CommunicationAction<>(new NetworkObjectPayload(no),
          this.getBody(), new ArrayList<Address>(subordinates)));
    });
  }

  // forwards the combined readings to its manager
  private void forwardReadings() {
    List<Address> recipients = new ArrayList<>();
    recipients.add(manager);
    notifyObservers(new CommunicationAction<NetworkObject>(
        new NetworkObjectPayload(new SmartMeterReadingNetworkObject(data
            .getData().subList(sentIndex, data.getData().size()), (String) this
            .getBody().getId())), this.getBody(), recipients));
    sentIndex += readingCount;
    readingCount = 0;
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (PerceptionWrapper.class.isAssignableFrom(arg.getClass())) {
      perceiveContinue((PerceptionWrapper) arg);
    }
  }

  /*
   * // unpack a message that has been received private void
   * unpackMessage(NetworkObjectPayload result) { try { ByteArrayPayload payload
   * = (ByteArrayPayload) result.getPayload(); ByteArrayInputStream instream =
   * new ByteArrayInputStream( payload.getPayload()); ObjectInputStream input;
   * input = new ObjectInputStream(instream); List<?> newdata = (List<?>)
   * input.readObject(); System.out.println(Arrays.toString(newdata.toArray()));
   * addRecievedData(newdata); } catch (Exception e) { e.printStackTrace(); } }
   */
}
