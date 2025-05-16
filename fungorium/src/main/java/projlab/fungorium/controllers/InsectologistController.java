package projlab.fungorium.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import projlab.fungorium.actions.insectologist.CutThreadAction;
import projlab.fungorium.actions.insectologist.EatSporeAction;
import projlab.fungorium.actions.insectologist.MoveInsectAction;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.player.Insectologist;

public class InsectologistController {
	private int activeInsectID;

	private GameController gameController;

	private CutThreadAction cutThreadAction;
	private EatSporeAction eatSporeAction;
	private MoveInsectAction moveInsectAction;

	public InsectologistController(GameController gameController) {
		this.gameController = gameController;

		cutThreadAction = new CutThreadAction(gameController, this);
		eatSporeAction = new EatSporeAction(gameController, this);
		moveInsectAction = new MoveInsectAction(gameController, this);
	}

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

	public List<AbstractAction> getActions() {
		return new ArrayList<>(List.of(cutThreadAction, eatSporeAction, moveInsectAction));
	}
}
