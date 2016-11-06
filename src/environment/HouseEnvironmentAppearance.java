package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;

public class HouseEnvironmentAppearance extends SimpleEnvironmentAppearance {

	private static final String CLASSOF = "House:";
	public HouseEnvironmentAppearance(int id) {
		super(String.valueOf(id));
	}

	@Override
	public String represent() {
		return CLASSOF + this.getName();
	}
}
