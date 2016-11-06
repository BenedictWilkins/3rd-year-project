package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.ComplexEnvironmentAppearance;

public class NationalGridUniverseAppearance extends ComplexEnvironmentAppearance {

	public NationalGridUniverseAppearance(String name) {
		super(name);
	}

	@Override
	public String represent() {
		return super.getName();
	}
}
