package projlab.fungorium.controllers;

import projlab.fungorium.models.Insect;
import projlab.fungorium.models.player.Insectologist;

public class InsectologistController {
	private int activeInsectID;

	// @formatter:off
	public int getInsectID() { return activeInsectID; }
	// @formatter:on

	public void updateActive(Insectologist newActiveInsectologist) {
		activeInsectID = newActiveInsectologist.getID();
	}

	public Insect getSelectedInsect() throws Exception {
		Insect i = GameController.getInstance().getSelectedInsect();
		if(i.getInsectologistID() != activeInsectID) {
			throw new Exception("Insectologist IDs of active player and the selected insect don't match");
		}
		return i;
	}
}
