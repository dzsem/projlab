package projlab.fungorium.actions.game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.Game;
import projlab.fungorium.models.player.PlayerType;

public class PassAction extends AbstractAction {
    private GameController controller;

    public PassAction(GameController controller) {
        super();

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller.getActiveType() == PlayerType.INSECTOLOGIST) {
            controller.setInsectologistIdx(controller.getInsectologistIdx() + 1);
            controller.setActiveType(PlayerType.MYCOLOGIST);
        } else {
            controller.setMycologistIdx(controller.getMycologistIdx() + 1);
            controller.setActiveType(PlayerType.INSECTOLOGIST);
        }

        // TODO: nem egyenlő számú player típusokra nem működik
        if (controller.checkIfLastActive()) {
            Game.getInstance().nextRound();

            controller.setInsectologistIdx(0);
            controller.setMycologistIdx(0);
        }
    }

}
