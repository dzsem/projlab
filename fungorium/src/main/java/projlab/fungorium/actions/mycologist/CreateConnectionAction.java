package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;

public class CreateConnectionAction extends AbstractAction {

    private GameController controller;

    public CreateConnectionAction(GameController controller) {
        super();

        putValue(NAME, "Create Connection");

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!controller.getCurrentPlayer().hasMoreActions()) {
            controller.showMessage("Out of actions.");
            setEnabled(false);
            return;
        }

        try {
            Tecton t = controller.getSelectedTecton();
            MushroomThread mt = controller.getMycologistController().getSelectedThread();
            mt.createConnection(t);

            controller.getCurrentPlayer().exhaustAction();

            if (!controller.getCurrentPlayer().hasMoreActions()) {
                setEnabled(false);
            }

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
