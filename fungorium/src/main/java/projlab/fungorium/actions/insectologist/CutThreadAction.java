package projlab.fungorium.actions.insectologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.InsectologistController;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.MushroomThread;

public class CutThreadAction extends AbstractAction {

    private GameController controller;
    private InsectologistController insectologist;

    public CutThreadAction(GameController controller, InsectologistController insectologist) {
        super();

        putValue(NAME, "Cut Thread");

        this.controller = controller;
        this.insectologist = insectologist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Insect i = insectologist.getSelectedInsect();
            MushroomThread mt = controller.getSelectedThread();
            i.cutMushroomThread(mt);

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }

}
