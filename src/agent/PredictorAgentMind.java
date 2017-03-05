package agent;

import agent.NeighbourhoodAgentBrain.PerceptionWrapper;
import agent.actions.CommunicationAction;
import agent.communication.NetworkObject;
import agent.communication.NetworkObjectPayload;
import agent.communication.SmartMeterReadingNetworkObject;
import environment.communication.module.Address;
import machinelearning.agent.DataFrameRow;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PredictorAgentMind extends NeighbourhoodAgentMind {

  public static Boolean GUI = true;
  private static String SERIESNAME = "Aggregated Consumption";

  protected ForecastingModel model;
  protected Threshold threshold;
  protected ModelModifier modifier;
  protected Boolean checkThreshold = false;

  protected SeriesPlot plot;

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
      plot = new SeriesPlot(SERIESNAME, new Double[] {}, "AGGREGATED DATA");
    }
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    super.update(observable, arg);
  }

  @Override
  protected void perceiveContinue(PerceptionWrapper perception) {
    Set<DataFrameRowReading> readings = new HashSet<>();
    Set<NetworkObject> other = new HashSet<>();
    this.recentPerception = perception.getMessages();
    perception.getMessages().forEach(
        (NetworkObject no) -> {
          if (SmartMeterReadingNetworkObject.class.isAssignableFrom(no
              .getClass())) {
            ((SmartMeterReadingNetworkObject) no).getData().forEach(
                (DataFrameRow row) -> {
                  readings.add((DataFrameRowReading) row);
                });
          } else {
            other.add(no);
          }
        });
    for (DataFrameRowReading r : readings) {
      data.addRow(r);
      plot.addToSeries(SERIESNAME, new Double[] { r.getReading() });
    }
    System.out.println(Arrays.toString(readings.toArray()));
  }

  @Override
  public EnvironmentalAction decide(Object... parameters) {
    // decide if models should change, query the threshold
    if (data.getNumRows() > 0) {
      checkThreshold = this.threshold.checkThreshold(data);
      //System.out.println("THRESHOLD: " + checkThreshold);
    }
    return null;
  }

  @Override
  public void execute(EnvironmentalAction action) {
    // forecast
    // model.trainModel(data);
    // model.getForecasts();
    // thresholding
    if (checkThreshold) {
      executeModelModification();
    }
  }

  private void executeModelModification() {
    notifyObservers(new CommunicationAction<>(
        new NetworkObjectPayload(modifier), this.getBody(),
        new ArrayList<Address>(subordinates)));
  }
}
