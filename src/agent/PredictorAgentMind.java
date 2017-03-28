package agent;

import agent.NeighbourhoodAgentBrain.PerceptionWrapper;
import agent.actions.CommunicationAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.communication.SmartMeterReadingNetworkObject;
import environment.communication.module.Address;
import graph.SeriesPlot;
import housemodel.combination.Combinator;
import housemodel.combination.ReadingCombinator;
import housemodel.threshold.ModelModifier;
import housemodel.threshold.Threshold;

import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.DataFrameRowReading;
import machinelearning.agent.ForecastingModel;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link Mind} implementation for a Predictor Agent. This mind will combine
 * readings, send and forward messages. Its main purpose is to provide forecasts
 * based on the data it receives, this is done using its
 * {@link ForecastingModel}. This mind also makes decisions about how to alter
 * the behaviour of its subordinate agents namely the
 * {@link SmartMeterAgentBody SmartMeterAgents}. This is done based on its
 * {@link Threshold} and the alterations are defined in its
 * {@link ModelModifier}. <br>
 * A predictor agent uses the parts of a Neighbourhood agent - see
 * {@link NeighbourhoodAgentBody}. Extends {@link NeighbourhoodAgentMind}, only
 * the mind differs.
 * 
 * @author Benedict Wilkins
 *
 */
public class PredictorAgentMind extends NeighbourhoodAgentMind {

  // should a gui of received readings and forecasts be displayed?
  public static Boolean GUI = true;
  // gui parameters
  private static final String CONSUMPTIONNAME = "Aggregated Consumption";
  private static final String PREDICTIONNAME = "Prediction";
  // number of cycles until a forecast should be made
  private static final int NUMSTEPS = 10 * 48;
  // private int predcount = 0;

  // variables for behaviour alteration
  protected ForecastingModel model;
  protected Threshold threshold;
  protected ModelModifier modifier;
  protected Boolean checkThreshold = false;

  // counters
  protected int predictedFrom = 0;
  protected int cycleCounter = 0;
  // how many cycles to predict
  protected final int predict = 20 * 48;

  protected SeriesPlot plot;
  // all of the forecasts this agent has made
  protected DataFrame forecasts;

  /**
   * Constructor. See
   * {@link NeighbourhoodAgentMind #NeighbourhoodAgentMind(Class, ReadingCombinator, Set, Address, Set)}
   * .
   * 
   * @param brainclass
   *          class of the {@link NeighbourhoodAgentBrain} that this
   *          {@link Mind} is linked to
   * @param combinator
   *          defines how to combine received readings, see {@link Combinator}
   * @param possibleActions
   *          the {@link Set} of possible {@link AbstractAction}s this
   *          {@link Mind} can take in each cycle
   * @param manager
   *          the {@link Address} of this agents superior
   * @param subordinates
   *          the {@link Address}s of this agents subordinates
   * @param model
   *          to use for forecasting
   * @param threshold
   *          decides when to attempt change subordinates behaviour
   * @param modifier
   *          defines how to change subordinates behaviour
   */
  public PredictorAgentMind(
      Class<? extends NeighbourhoodAgentBrain> brainclass,
      ReadingCombinator combinator,
      Set<Class<? extends AbstractAction>> possibleActions, Address manager,
      Set<Address> subordinates, ForecastingModel model, Threshold threshold,
      ModelModifier modifier) {
    super(brainclass, combinator, possibleActions, manager, subordinates);
    this.model = model;
    this.threshold = threshold;
    this.modifier = modifier;
    if (GUI) {
      plot = new SeriesPlot(CONSUMPTIONNAME, new Double[] {}, "AGGREGATED DATA");
    }
    this.forecasts = new DataFrame(DataFrameMetaTimeValue.getInstance());
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    super.update(observable, arg);
  }

  @Override
  protected void perceiveContinue(PerceptionWrapper perception) {
    List<SmartMeterReadingNetworkObject> readings = new ArrayList<>();
    Set<NetworkObject> other = new HashSet<>();
    this.recentPerception = perception.getMessages();
    this.recentPerception
        .forEach((NetworkObject no) -> {
          if (SmartMeterReadingNetworkObject.class.isAssignableFrom(no
              .getClass())) {
            readings.add((SmartMeterReadingNetworkObject) no);
          } else {
            other.add(no);
          }
        });
    List<DataFrameRowReading> combinedReadings = combinator.combine(readings);
    for (DataFrameRowReading r : combinedReadings) {
      data.addRow(r);
      if (GUI) {
        plot.addToSeries(CONSUMPTIONNAME, new Double[] { r.getReading() });
      }
    }
    if (GUI) {
      if (data.getNumRows() > 1) {
        plot.updateAxis(CONSUMPTIONNAME);
      }
    }
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    // decide if models should change, query the threshold
    if (data.getNumRows() > 0) {
      checkThreshold = this.threshold.checkThreshold(data);
      // System.out.println("THRESHOLD: " + checkThreshold);
    }
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    cycleCounter++;
    if (cycleCounter >= predict) {
      predictedFrom = predictedFrom + cycleCounter;
      predict();
      cycleCounter = 0;
    }
    if (checkThreshold) {
      executeModelModification();
    }
  }

  // forecasts
  private void predict() {
    // TODO make this more stream lined - real time training
    model.trainModel(data);
    DataFrame newForecasts = model.getForecasts(NUMSTEPS);
    forecasts.appendDataFrame(newForecasts);
    if (GUI) {
      plot.addSeries(PREDICTIONNAME, newForecasts.getColumn(1, Double.class)
          .toArray(new Double[] {}), predictedFrom);
    }
  }

  // modify subordinates
  private void executeModelModification() {
    notifyObservers(new CommunicationAction<>(
        new NetworkObjectPayload(modifier), this.getBody(),
        new ArrayList<Address>(subordinates)));
  }
}
