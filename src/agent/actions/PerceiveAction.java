package agent.actions;

import agent.NeighbourhoodAgentBrain;
import agent.SmartMeterAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Brain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Mind;

/**
 * An internal {@link Action} performed by a {@link Mind} that request the most
 * recent perceptions from the {@link Brain} it is linked with. <br>
 * see: {@link NeighbourhoodAgentBrain}, {@link SmartMeterAgentBrain} and their
 * linked {@link Mind}s.
 * 
 * @author Benedict Wilkins
 *
 */
public class PerceiveAction implements Action {
}
