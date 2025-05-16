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
        MushroomThread mt = controller.getSelectedThread();
        Tecton t = controller.getSelectedTecton();
        try {
            mt.createConnection(t);
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
