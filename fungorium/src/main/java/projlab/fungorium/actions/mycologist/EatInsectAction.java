package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.MushroomThread;

public class EatInsectAction extends AbstractAction {

    private GameController controller;

    public EatInsectAction(GameController controller) {
        super();

        putValue(NAME, "Eat Insect");

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!controller.getCurrentPlayer().hasMoreActions()) {
            controller.showMessage("Out of actions.");
            return;
        }

        try {
            MushroomThread mt = controller.getMycologistController().getSelectedThread();
            mt.eat();

            controller.getCurrentPlayer().exhaustAction();

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
