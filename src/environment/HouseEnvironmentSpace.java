package environment;

import housemodels.HouseModel;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

public class HouseEnvironmentSpace extends CustomObservable implements Space {
	
	private HouseModel model;
	
	public HouseEnvironmentSpace(HouseModel model) {
		this.model = model;
	}
	
	public Double getReading() {
		return model.getReading();
	}
}
