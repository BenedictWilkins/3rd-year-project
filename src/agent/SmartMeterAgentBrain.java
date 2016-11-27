package agent;

import agent.actions.GlobalResult;
import agent.actions.PerceiveAction;
import agent.actions.TakeReadingResult;
import agent.general.GeneralAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import utilities.DateTimeInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

/**
 * The {@link Brain} implementation for a Smart Meter Agent. <br>
 * Extends: {@link AbstactAgentBrain}.
 * 
 * @author Benedict Wilkins
 *
 */
public class SmartMeterAgentBrain extends
    GeneralAgentBrain<SmartMeterAgentMind, SmartMeterAgentBody> {

  SmartMeterAgentPerceptionWrapper recentPerceptions = new SmartMeterAgentPerceptionWrapper();
  SmartMeterAgentPerceptionWrapper allPerceptions = new SmartMeterAgentPerceptionWrapper();

  public SmartMeterAgentBrain() {
    super(SmartMeterAgentMind.class, SmartMeterAgentBody.class);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    if (this.getBodyclass().isAssignableFrom(observable.getClass())) {
      handleBodyMessage(arg);
    } else if (this.getMindclass().isAssignableFrom(observable.getClass())) {
      handleMindMessage(arg);
    }
  }

  /**
   * Stores all {@link Result}s received from the {@link Body}.
   * 
   * @param arg
   *          Received from the body - to store
   */
  private void handleBodyMessage(Object arg) {
    //System.out.println("STORING DATA");
    if (!GlobalResult.class.isAssignableFrom(arg.getClass())) {
      return;
    }
    GlobalResult result = (GlobalResult) arg;
    if (result.getPayload() == null) {
      // potentially as TakeReadingResult
      if (TakeReadingResult.class.isAssignableFrom(arg.getClass())) {
        TakeReadingResult trr = (TakeReadingResult) arg;
        recentPerceptions.readings.put(trr.getDateTime(), trr.getReading());
      }
    } else {
      List<String> ms;
      if ((ms = recentPerceptions.messages.get(result.getActorId())) != null) {
        ms.add(result.getPayload());
      } else {
        ms = new ArrayList<>();
        ms.add(result.getPayload());
        recentPerceptions.messages.put(result.getActorId(), ms);
      }
    }
  }

  private void handleMindMessage(Object arg) {
    if (PerceiveAction.class.isAssignableFrom(arg.getClass())) {
      // the mind is requesting the perceptions
      notifyObservers(recentPerceptions, this.getMindclass());
    } else {
      notifyObservers(arg, this.getBodyclass());
    }
  }

  /**
   * A wrapper class for the data received from the {@link Body}.
   * 
   * @author Benedict Wilkins
   *
   */
  public class SmartMeterAgentPerceptionWrapper {
    private Map<DateTimeInterface, Double> readings = new HashMap<>();
    private Map<String, List<String>> messages = new HashMap<>();

    public SmartMeterAgentPerceptionWrapper() {
      this.readings = new HashMap<>();
      this.messages = new HashMap<>();
    }

    public SmartMeterAgentPerceptionWrapper(
        Map<DateTimeInterface, Double> readings,
        Map<String, List<String>> messages) {
      this.readings = readings;
      this.messages = messages;
    }

    public Map<DateTimeInterface, Double> getReadings() {
      return readings;
    }

    public void setReadings(Map<DateTimeInterface, Double> readings) {
      this.readings = readings;
    }

    public Map<String, List<String>> getMessages() {
      return messages;
    }

    public void setMessages(Map<String, List<String>> messages) {
      this.messages = messages;
    }
  }
}
