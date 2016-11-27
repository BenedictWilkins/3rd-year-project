package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Container;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Environment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

import java.util.HashSet;
import java.util.Set;

/**
 * An updated version of {@link AbstractEnvironment} from GAWL. In this project
 * an environment was required be an Observer/Observable pair. The GAWL
 * implementation prevented that.
 * 
 * @author Benedict Wilkins
 *
 */
public abstract class AbstractEnvironment extends CustomObservable implements
    Environment, Container, CustomObserver {

  private Space state;
  private Set<Class<? extends AbstractAction>> admissibleActions;
  private Set<Body> bodies;
  private Physics physics;
  private Boolean bounded;
  private Appearance appearance;

  /**
   * The default class constructor.
   * 
   * @param state
   *          : an {@link Space} instance.
   * @param admissibleActions
   *          : the {@link Set} of performable {@link AbstractAction} instances.
   * @param bodies
   *          : a {@link Set} of {@link Body} elements.
   * @param physics
   *          : the {@link Physics} of the environment.
   * @param bounded
   *          : a {@link Boolean} value indicating whether the environment is
   *          bounded or not.
   * @param appearance
   *          : the {@link Appearance} of the environment.
   */
  public AbstractEnvironment(Space state,
      Set<Class<? extends AbstractAction>> admissibleActions, Set<Body> bodies,
      Physics physics, Boolean bounded, Appearance appearance) {
    this.state = state;
    this.admissibleActions = admissibleActions != null ? admissibleActions
        : new HashSet<>();
    this.bodies = bodies != null ? bodies : new HashSet<>();
    this.physics = physics;
    this.bounded = bounded;
    this.appearance = appearance;
  }

  @Override
  public Space getState() {
    return this.state;
  }

  @Override
  public void setState(Space state) {
    this.state = state;
  }

  @Override
  public Set<Class<? extends AbstractAction>> getAdmissibleActions() {
    return this.admissibleActions;
  }

  @Override
  public void setAdmissibleActions(
      Set<Class<? extends AbstractAction>> admissibleActions) {
    this.admissibleActions = admissibleActions;
  }

  @Override
  public Set<Body> getBodies() {
    return this.bodies;
  }

  @Override
  public void setBodies(Set<Body> bodies) {
    this.bodies = bodies;
  }

  @Override
  public Physics getPhysics() {
    return this.physics;
  }

  @Override
  public void setPhysics(Physics physics) {
    this.physics = physics;
  }

  @Override
  public void setBounded(Boolean bounded) {
    this.bounded = bounded;
  }

  @Override
  public Appearance getAppearance() {
    return this.appearance;
  }

  @Override
  public void setAppearance(Appearance appearance) {
    this.appearance = appearance;
  }

  @Override
  public Boolean isBounded() {
    return this.bounded;
  }

}
