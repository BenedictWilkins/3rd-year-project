package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.Appearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.ComplexEnvironmentAppearance;

import java.util.List;

/**
 * The {@link Appearance} of {@link NationalGridUniverse}. <br/>
 * Extends: {@link ComplexEnvironmentAppearance}.
 * 
 * @author Benedict Wilkins
 *
 */
public class NationalGridUniverseAppearance extends
    ComplexEnvironmentAppearance {

  /**
   * Constructor.
   * 
   * @param name
   *          of this {@link Appearance}
   * @param subAppearances
   *          the {@link Appearance}s of all {@link HouseEnvironment}s in the
   *          {@link NationalGridUniverse}
   */
  public NationalGridUniverseAppearance(String name,
      List<HouseEnvironmentAppearance> subAppearances) {
    super(name);
    subAppearances.forEach((HouseEnvironmentAppearance appearance) -> super
        .addSubEnvironmentAppearance(appearance));
  }

  @Override
  public String represent() {
    return super.getName();
  }
}
