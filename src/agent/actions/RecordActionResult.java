package agent.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.ActionResult;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.DefaultActionResult;

public class RecordActionResult extends DefaultActionResult {

	private Double record = null;
	
	public RecordActionResult(ActionResult result, Exception failureReason) {
		super(result, failureReason, null);
	}
	
	public RecordActionResult(ActionResult result, Double record) {
		super(result, null);
	}

	public Double getRecord() {
		return record;
	}
}
