package agent;

import agent.SmartMeterAgentBrain.SmartMeterAgentPerceptionWrapper;
import agent.actions.PerceiveAction;
import agent.actions.TakeReadingAction;
import agent.general.GeneralAgentMind;
import environment.communication.module.Address;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import utilities.DateTimeInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The {@link Mind} implementation for a Smart Meter Agent. <br>
 * Extends: {@link AbstactAgent}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentMind extends GeneralAgentMind<SmartMeterAgentBrain> {

  private Address manager;

  /**
   * Constructor.
   * 
   * @param possibleActions
   *          a list of all possible {@link AbstractAction}s this agent can
   *          perform.
   */
  public SmartMeterAgentMind(
      Set<Class<? extends AbstractAction>> possibleActions, Address manager) {
    super(SmartMeterAgentBrain.class, possibleActions);
    this.manager = manager;
  }

  @Override
  public void perceive(Object perceptionWrapper) {
    // System.out.println("I AM PERCEIVING...");
    notifyObservers(new PerceiveAction(), this.getBrainClass());
  }

  private void perceiveContinue(SmartMeterAgentPerceptionWrapper perception) {
    perception.getMessages();
    perception
        .getReadings()
        .entrySet()
        .forEach(
            (Entry<DateTimeInterface, Double> ent) -> System.out
                .println("READING: " + ent.getValue() + " AT: " + ent.getKey()));
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    // System.out.println("I AM DECIDING...");
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    // System.out.println("I AM EXECUTING...");
    List<Address> recipients = new ArrayList<>();
    recipients.add(manager);
    notifyObservers(new TakeReadingAction());
    // notifyObservers(new CommunicationAction("Hello my name is: ", null,
    // recipients), this.getBrainClass());
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (SmartMeterAgentPerceptionWrapper.class.isAssignableFrom(arg.getClass())) {
      perceiveContinue((SmartMeterAgentPerceptionWrapper) arg);
    }
  }
}
