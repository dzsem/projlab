package projlab.fungorium.controllers;

import projlab.fungorium.models.player.Mycologist;

public class MycologistController {
	private int activeMushroomID;

	// @formatter:off
	public int getMushroomID() { return activeMushroomID; }
	// @formatter:on

	public void updateActive(Mycologist newActiveMycologist) {
		activeMushroomID = newActiveMycologist.getID();
	}
}
