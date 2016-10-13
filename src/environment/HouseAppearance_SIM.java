package environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;

public class HouseAppearance_SIM extends SimpleEnvironmentAppearance{

	private static final String CLASSOF = "House:";
	public HouseAppearance_SIM(int id) {
		super(String.valueOf(id));
	}

	@Override
	public String represent() {
		return CLASSOF + this.getName();
	}
}
