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
            if (mt.getMushroomID() != controller.getMycologistId()) {
                throw new Exception("Can't controll someone elses thread");
            }
            mt.createConnection(t);

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
