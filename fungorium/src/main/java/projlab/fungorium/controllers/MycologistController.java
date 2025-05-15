package projlab.fungorium.controllers;

import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.player.Mycologist;

public class MycologistController {
	private int activeMushroomID;

	// @formatter:off
	public int getMushroomID() { return activeMushroomID; }
	// @formatter:on

	public void updateActive(Mycologist newActiveMycologist) {
		activeMushroomID = newActiveMycologist.getID();
	}

	public MushroomBody getSelectedBody() throws Exception {
		MushroomBody mb = GameController.getInstance().getSelectedBody();
		if(mb.getMushroomID() != activeMushroomID) {
			throw new Exception("Mycologist IDs of active player and the selected thread don't match");
		}
		return mb;
	}
}
