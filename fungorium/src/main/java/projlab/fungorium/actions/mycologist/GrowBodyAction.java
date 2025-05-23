package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;
import java.util.jar.Attributes.Name;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.MycologistController;
import projlab.fungorium.models.Tecton;

public class GrowBodyAction extends AbstractAction {

    private GameController controller;
    private MycologistController mycologist;

    public GrowBodyAction(GameController controller, MycologistController mycologist) {
        super();

        putValue(NAME, "Grow Body");

        this.controller = controller;
        this.mycologist = mycologist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!controller.getCurrentPlayer().hasMoreActions()) {
            controller.showMessage("Out of actions.");
            return;
        }

        Tecton t = controller.getSelectedTecton();
        int id = mycologist.getMushroomID();
        try {
            t.growBody(id);

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
