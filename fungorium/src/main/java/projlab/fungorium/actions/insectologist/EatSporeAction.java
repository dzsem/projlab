package projlab.fungorium.actions.insectologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.InsectologistController;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.effects.Effect;

public class EatSporeAction extends AbstractAction {
    private GameController controller;
    private InsectologistController insectologist;

    public EatSporeAction(GameController controller, InsectologistController insectologist) {
        super();

        putValue(NAME, "Eat Spore");

        this.controller = controller;
        this.insectologist = insectologist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!controller.getCurrentPlayer().hasMoreActions()) {
            controller.showMessage("Out of actions.");
            setEnabled(false);
            return;
        }

        try {
            Insect i = insectologist.getSelectedInsect();

            Effect effect = i.eatMushroomSpore();

            controller.getCurrentPlayer().exhaustAction();

            if (!controller.getCurrentPlayer().hasMoreActions()) {
                setEnabled(false);
            }

            controller.showMessage(effect.getDescription());

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }

}
