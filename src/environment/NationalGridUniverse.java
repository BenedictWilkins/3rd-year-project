package environment;

import agent.actions.CommunicationAction;
import agent.actions.CommunicationEvent;
import agent.actions.TakeReadingAction;
import agent.general.GeneralAgentBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Environment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

  public static final Set<Class<? extends AbstractAction>> UNIVERSEACTIONS;

  static {
    Set<Class<? extends AbstractAction>> actions = new HashSet<>();
    actions.add(TakeReadingAction.class);
    actions.add(CommunicationAction.class);
    UNIVERSEACTIONS = Collections.unmodifiableSet(actions);
  }

  /**
   * Constructor. See:
   * {@link AbstractEnvironment#AbstractEnvironment(Space, Set, Set, Physics, Boolean, Appearance)}
   * 
   * @param houseSubEnvironments
   *          a list of {@link HouseEnvironment}s.
   */
  public NationalGridUniverse(Space state, Set<Body> bodies,
      NationalGridUniversePhysics physics,
      List<HouseEnvironment> houseSubEnvironments) {
    super(state, UNIVERSEACTIONS, bodies, physics, false,
        new NationalGridUniverseAppearance("UNIVERSE",
            getAppearances(houseSubEnvironments)));
    this.houseSubEnvironments = (houseSubEnvironments != null) ? houseSubEnvironments
        : new ArrayList<HouseEnvironment>();
    for (HouseEnvironment e : this.houseSubEnvironments) {
      ((HouseEnvironmentSpace) e.getState()).addObserver(this);
    }
    // set up the observable map for ease of message passing
    physics.setUpCommunication(houseSubEnvironments, bodies);
  }

  @Override
  public void update(CustomObservable observable, Object arg) {
    Event event = (Event) arg;
    event.attempt(getPhysics(), getState());
    // TODO?
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
