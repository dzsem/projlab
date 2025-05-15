package projlab.fungorium.controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import projlab.fungorium.actions.mycologist.CreateConnectionAction;
import projlab.fungorium.actions.mycologist.DistributeSporeAction;
import projlab.fungorium.actions.mycologist.EatInsectAction;
import projlab.fungorium.actions.mycologist.GrowBodyAction;
import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.player.Mycologist;

public class MycologistController {
	private int activeMushroomID;

	private GameController gameController;

	private CreateConnectionAction createConnectionAction;
	private DistributeSporeAction distributeSporeAction;
	private EatInsectAction eatInsectAction;
	private GrowBodyAction growBodyAction;

	public MycologistController(GameController gameController) {
		this.gameController = gameController;

		createConnectionAction = new CreateConnectionAction(gameController);
		distributeSporeAction = new DistributeSporeAction(gameController, this);
		eatInsectAction = new EatInsectAction(gameController);
		growBodyAction = new GrowBodyAction(gameController, this);
	}

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

	public List<AbstractAction> getActions() {
		return new ArrayList<>(List.of(createConnectionAction, distributeSporeAction, eatInsectAction, growBodyAction));
	}
}
