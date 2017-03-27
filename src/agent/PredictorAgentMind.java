package agent;

import agent.NeighbourhoodAgentBrain.PerceptionWrapper;
import agent.actions.CommunicationAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.communication.SmartMeterReadingNetworkObject;
import environment.communication.module.Address;
import machinelearning.agent.DataFrame;
import machinelearning.agent.DataFrameMetaTimeValue;
import machinelearning.agent.DataFrameRowReading;
import machinelearning.agent.ForecastingModel;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import graph.SeriesPlot;
import housemodel.combination.ReadingCombinator;
import housemodel.threshold.ModelModifier;
import housemodel.threshold.Threshold;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PredictorAgentMind extends NeighbourhoodAgentMind {

  public static Boolean GUI = true;
  private static final String CONSUMPTIONNAME = "Aggregated Consumption";
  private static final String PREDICTIONNAME = "Prediction";
  private static final int NUMSTEPS = 10 * 48;
  private int predcount = 0;

  protected ForecastingModel model;
  protected Threshold threshold;
  protected ModelModifier modifier;
  protected Boolean checkThreshold = false;

  protected int predictedFrom = 0;
  protected int cycleCounter = 0;
  protected final int PREDICT = 20 * 48;

  protected SeriesPlot plot;
  protected DataFrame forecasts;

  public PredictorAgentMind(
      Class<? extends NeighbourhoodAgentBrain> brainclass,
      ReadingCombinator combinator,
      Set<Class<? extends AbstractAction>> possibleActions,
      Address managerAddress, Set<Address> subordinates,
      ForecastingModel model, Threshold threshold, ModelModifier modifier) {
    super(brainclass, combinator, possibleActions, managerAddress, subordinates);
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
    if (cycleCounter >= PREDICT) {
      predictedFrom = predictedFrom + cycleCounter;
      predict();
      cycleCounter = 0;
    }
    if (checkThreshold) {
      executeModelModification();
    }
  }

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

  private void executeModelModification() {
    notifyObservers(new CommunicationAction<>(
        new NetworkObjectPayload(modifier), this.getBody(),
        new ArrayList<Address>(subordinates)));
  }
}
