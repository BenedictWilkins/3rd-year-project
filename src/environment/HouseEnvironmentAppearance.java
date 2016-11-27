package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;

/**
 * The {@link Appearance} of a {@link HouseEnvironment}. <br>
 * Extends: {@link SimpleEnvironmentAppearance}.
 * 
 * @author Benedict Wilkins
 *
 */
public class HouseEnvironmentAppearance extends SimpleEnvironmentAppearance {

  private static final String CLASSOF = "House:";

  /**
   * Constructor.
   * 
   * @param id
   *          of the {@link HouseEnvironment}
   */
  public HouseEnvironmentAppearance(int id) {
    super(String.valueOf(id));
  }

  @Override
  public String represent() {
    return CLASSOF + this.getName();
  }
}
