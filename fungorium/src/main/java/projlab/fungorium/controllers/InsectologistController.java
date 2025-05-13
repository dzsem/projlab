package projlab.fungorium.controllers;

import projlab.fungorium.models.player.Insectologist;

public class InsectologistController {
	private int activeInsectID;

	// @formatter:off
	public int getInsectID() { return activeInsectID; }
	// @formatter:on

	public void updateActive(Insectologist newActiveInsectologist) {
		activeInsectID = newActiveInsectologist.getID();
	}
}
