package environment;

import agent.NeighbourhoodAgentBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Environment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@link Environment} that contains all {@link HouseEnvironment}s. <br/>
 * Extends: {@link AbstractEnvironment}.
 * 
 * @author Benedict Wilkins
 *
 */
public class NationalGridUniverse extends AbstractEnvironment {
  // a list of all house environments
  private List<HouseEnvironment> houseSubEnvironments;

  private Map<String, CustomObserver> observables;

  /**
   * Constructor. See:
   * {@link AbstractEnvironment#AbstractEnvironment(Space, Set, Set, Physics, Boolean, Appearance)}
   * 
   * @param houseSubEnvironments
   *          a list of {@link HouseEnvironment}s.
   */
  public NationalGridUniverse(Space state,
      Set<Class<? extends AbstractAction>> admissibleActions, Set<Body> bodies,
      Physics physics, List<HouseEnvironment> houseSubEnvironments) {
    super(state, admissibleActions, bodies, physics, false,
        new NationalGridUniverseAppearance("UNIVERSE",
            getAppearances(houseSubEnvironments)));
    this.houseSubEnvironments = (houseSubEnvironments != null) ? houseSubEnvironments
        : new ArrayList<HouseEnvironment>();
    for (HouseEnvironment e : this.houseSubEnvironments) {
      ((HouseEnvironmentSpace) e.getState()).addObserver(this);
    }
    // set up the observable map for ease of message passing
    observables = new HashMap<>();
    this.houseSubEnvironments.forEach((HouseEnvironment house) -> observables
        .put(house.getAppearance().getName(), house));
    this.getBodies().forEach(
        (Body body) -> observables.put(body.getId().toString(),
            (AbstractAgent) body));
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    Event event = (Event) arg;
    Result result = event.attempt(getPhysics(), getState());
    System.out.println("SEND: " + result);
    CustomObserver obs = observables.get(result.getRecipientsIds().get(0));
    // check where the message is going
    if (HouseEnvironment.class.isAssignableFrom(obs.getClass())) {

    } else {
      // send it to a manager
      ((NeighbourhoodAgentBody)obs).getSensors().get(0).update(this, result);
    }
  }

  @Override
  public Boolean isSimple() {
    return false;
  }

  @Override
  public NationalGridUniverseSpace getState() {
    return (NationalGridUniverseSpace) super.getState();
  }

  /**
   * Getter for all {@link HouseEnvironment}s.
   * 
   * @return a {@link List} of {@link HouseEnvironment}s
   */
  public List<HouseEnvironment> getHouseSubEnvironments() {
    return houseSubEnvironments;
  }

  /**
   * Setter for all {@link HouseEnvironment}.
   * 
   * @param houseSubEnvironments
   *          a list of all {@link HouseEnvironment}s that this
   *          {@link NationalGridUniverse} will contain.
   */
  public void setHouseSubEnvironments(
      List<HouseEnvironment> houseSubEnvironments) {
    this.houseSubEnvironments = houseSubEnvironments;
  }

  /**
   * Getter for all {@link Appearance}s in the provided list.
   * 
   * @param houses
   *          to get {@link Appearance}s from
   * @return the {@link Appearance}s of the {@link HouseEnvironment}s provided
   *         by the houses parameter.
   */
  public static List<HouseEnvironmentAppearance> getAppearances(
      List<HouseEnvironment> houses) {
    if (houses == null) {
      return null;
    }

    List<HouseEnvironmentAppearance> appearances = new ArrayList<>();
    houses.forEach((HouseEnvironment house) -> appearances.add(house
        .getAppearance()));
    return appearances;
  }
}
