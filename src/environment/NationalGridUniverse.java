package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

public class NationalGridUniverse extends AbstractEnvironment {

	private List<HouseEnvironment> houseSubEnvironments;

	public NationalGridUniverse(Space state,
			Set<Class<? extends AbstractAction>> admissibleActions,
			Set<Body> bodies, Physics physics,
			List<HouseEnvironment> houseSubEnvironments) {
		super(state, admissibleActions, bodies, physics, false,
				new NationalGridUniverseAppearance("UNIVERSE"));
		this.houseSubEnvironments = (houseSubEnvironments != null) ? houseSubEnvironments
				: new ArrayList<HouseEnvironment>();
		for(HouseEnvironment e : this.houseSubEnvironments) {
			((HouseEnvironmentSpace)e.getState()).addObserver(this);
		}
	}

	@Override
	public void update(CustomObservable o, Object arg) {
		System.out.println("MESSAGE RECEIVED");
		Event e = (Event)arg;
		Result r = e.attempt(getPhysics(), getState());
		System.out.println("DONE");
	}

	@Override
	public Boolean isSimple() {
		return false;
	}

	@Override
	public NationalGridUniverseSpace getState() {
		return (NationalGridUniverseSpace) super.getState();
	}

	public List<HouseEnvironment> getHouseSubEnvironments() {
		return houseSubEnvironments;
	}

	public void setHouseSubEnvironments(
			List<HouseEnvironment> houseSubEnvironments) {
		this.houseSubEnvironments = houseSubEnvironments;
	}
}
